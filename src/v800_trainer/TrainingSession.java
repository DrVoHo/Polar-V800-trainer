package v800_trainer;

/*
 * TrainingSession.java
 *
 * SourceFile is part of Chainwheel

 * decrypt V800 files and stores to gpx and TourData
 */



/**
 *
 * @author and migration to java by volker hochholzer
 * 
 * Chainwheel and all dependend source files is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Chainwheel is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 */



//import com.sun.org.apache.xerces.internal.xs.StringList;


/*
    Copyright 2014-2015 Paul Colby

    This file is part of Bipolar.

    Bipolar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Biplar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bipolar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;


public class TrainingSession {

    private final String AUTOLAPS = "autolaps";
    private final String CREATE = "create";
    private final String LAPS = "laps";
    private final String ROUTE = "route";
    private final String RRSAMPLES = "rrsamples";
    private final String SAMPLES = "samples";
    private final String STATISTICS = "statistics";
    private final String ZONES = "zones";

    private HashMap parsedExercises;
    private HashMap parsedPhysicalInformation;
    private HashMap parsedSession;
    private String baseName;  //Name der zu untersuchenden Datei
    private String Lapnames;
    
    private String gpx_file;
    
    private long Dauer;
    private long Strecke;
    private long Datenpunkte;
    
    private GregorianCalendar StartTime;

    TrainingSession(String BaseName) {

        baseName = BaseName;
        parsedExercises = new HashMap();
    }



    private boolean isGzipped(String Filename) {

        FileInputStream data = null;
        try {
            data = new FileInputStream(Filename);
        } catch (Exception e) {
        }

        byte[] dummy = new byte[8];

        try {
            data.read(dummy, 0, 2);
            data.close();
        } catch (Exception e) {
        }

        return dummy[0] == (byte)GZIPInputStream.GZIP_MAGIC
                 && dummy[1] == (byte)(GZIPInputStream.GZIP_MAGIC>>>8);
        

    }


    private boolean isValid() {
        return !parsedExercises.isEmpty();
    }

    public boolean parse() {

        parsedExercises.clear();

        parsedPhysicalInformation = parsePhysicalInformation(baseName + "-physical-information");

        parsedSession = parseCreateSession(baseName + "-create");

        HashMap<String, HashMap<String, String>> fileNames = new HashMap();
        HashMap<String, String> inner = new HashMap();

        File fileInfo = new File(baseName.substring(0, baseName.lastIndexOf("/")));

        String[] dummy = fileInfo.list(new v800export.DirFilter("v2")); 

        for (int i = 0; i < dummy.length; i++) {

            String[] nameParts = dummy[i].split("-");//entryInfo.fileName().split(QLatin1Char('-'));
            if (nameParts.length >= 3 && nameParts[nameParts.length - 3].equals("exercises")) {
                inner.put(nameParts[nameParts.length - 1], fileInfo.getPath() + "/" + dummy[i]);//.filePath());
                fileNames.put(nameParts[nameParts.length - 2], inner);

            }

        }

        for (HashMap.Entry<String, HashMap<String, String>> entry : fileNames.entrySet()) {
            parse(entry.getKey(), entry.getValue());
            int i = 0;
        }

        return isValid();
    }

       boolean parse(String exerciseId, HashMap fileNames) {
        HashMap exercise = new HashMap();
        ArrayList sources = new ArrayList();

        if (fileNames.containsKey(AUTOLAPS)) {
            HashMap map = parseLaps(fileNames.get(AUTOLAPS).toString());
            if (!map.isEmpty()) {
                exercise.put(AUTOLAPS, map);
                sources.add(fileNames.get(AUTOLAPS));
            }
        }

        if (fileNames.containsKey(CREATE)) {
            HashMap map = parseCreateExercise(fileNames.get(CREATE).toString());
            if (!map.isEmpty()) {
                exercise.put(CREATE, map);
                sources.add(fileNames.get(CREATE));
            }
        }
        if (fileNames.containsKey(LAPS)) {
            HashMap map = parseLaps(fileNames.get(LAPS).toString());
            if (!map.isEmpty()) {
                exercise.put(LAPS, map);
                sources.add(fileNames.get(LAPS));
            }
        }

        
        if (fileNames.containsKey(ROUTE)) {
            HashMap map = parseRoute(fileNames.get(ROUTE).toString());
            if (!map.isEmpty()) {
                exercise.put(ROUTE, map);
                sources.add(fileNames.get(ROUTE));
            }
        }

        if (fileNames.containsKey(RRSAMPLES)) {
            HashMap map = parseRRSamples(fileNames.get(RRSAMPLES).toString());
            if (!map.isEmpty()) {
                exercise.put(RRSAMPLES, map);
                sources.add(fileNames.get(RRSAMPLES));
            }
        }

        if (fileNames.containsKey(SAMPLES)) {
            HashMap map = parseSamples(fileNames.get(SAMPLES).toString());
            if (!map.isEmpty()) {
                exercise.put(SAMPLES, map);
                sources.add(fileNames.get(SAMPLES));
            }
        }

        if (fileNames.containsKey(STATISTICS)) {
            HashMap map = parseStatistics(fileNames.get(STATISTICS).toString());
            if (!map.isEmpty()) {
                exercise.put(STATISTICS, map);
                sources.add(fileNames.get(STATISTICS));
            }
        }

        if (fileNames.containsKey(ZONES)) {
            HashMap map = parseZones(fileNames.get(ZONES).toString());
            if (!map.isEmpty()) {
                exercise.put(ZONES, map);
                sources.add(fileNames.get(ZONES));
            }
        }

        if (!exercise.isEmpty()) {
            exercise.put("sources", sources);
            parsedExercises.put(exerciseId, exercise);
            return true;
        }
        return false;
    }


    private HashMap parseCreateExercise(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();

        for (int i = 0; i < Types.CreateExercise.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.CreateExercise[i][1],
                    Types.CreateExercise[i][2]);
            fieldInfoM.put((String) Types.CreateExercise[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseCreateExercise(String fileName) {
        
    //    System.out.println("    parseCreateExcercise  ");
        
        InputStream file = OpenInputStream(fileName);
        if (file == null) return new HashMap();
            else return parseCreateExercise(file);

    }

    private HashMap parseCreateSession(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.CreateSession.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.CreateSession[i][1],
                    Types.CreateSession[i][2]);
            fieldInfoM.put((String) Types.CreateSession[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseCreateSession(String fileName) {
        
    //    System.out.println("    parseCreateSession  ");
        
        InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {
            return parseCreateSession(file);
        }

    }

    private HashMap parseLaps(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.Laps.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.Laps[i][1],
                    Types.Laps[i][2]);
            fieldInfoM.put((String) Types.Laps[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseLaps(String fileName) {
        
   //    System.out.println("    parseLaps  ");
        
       InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {

        return parseLaps(file);
        }
    }

    private HashMap parsePhysicalInformation(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.PhysicalInformation.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.PhysicalInformation[i][1],
                    Types.PhysicalInformation[i][2]);
            fieldInfoM.put((String) Types.PhysicalInformation[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parsePhysicalInformation(String fileName) {
        
   //    System.out.println("    parsePhysicalInformation  ");

        InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {

        return parsePhysicalInformation(file);
        }
    }

    private HashMap parseRoute(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.Route.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.Route[i][1],
                    Types.Route[i][2]);
            fieldInfoM.put((String) Types.Route[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseRoute(String fileName) {
        
   //   System.out.println("    parseRoute  ");  

      InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {
        return parseRoute(file);
        }
    }

    private HashMap parseRRSamples(InputStream data) {
        
        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.Route.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo("value",
                    Types.ScalarType.Uint32);
            fieldInfoM.put("1", inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseRRSamples(String fileName) {
        
    //    System.out.println("    parseRRSamples  ");        
       InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {

        return parseRRSamples(file);
        }
    }

    private HashMap parseSamples(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.Samples.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.Samples[i][1],
                    Types.Samples[i][2]);
            fieldInfoM.put((String) Types.Samples[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseSamples(String fileName) {
        
    //    System.out.println("    parseSamples  ");        
        
       InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {

        return parseSamples(file);
        }
    }

    private HashMap parseStatistics(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.Statistics.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.Statistics[i][1],
                    Types.Statistics[i][2]);
            fieldInfoM.put((String) Types.Statistics[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseStatistics(String fileName) {
        
    //   System.out.println("    parseStatistics  ");   
        
       InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {
        }
        return parseStatistics(file);

    }

    private HashMap parseZones(InputStream data) {

        HashMap<String, Message.FieldInfo> fieldInfoM = new HashMap();
        for (int i = 0; i < Types.Zones.length; i++) {
            Message.FieldInfo inner = new Message.FieldInfo(Types.Zones[i][1],
                    Types.Zones[i][2]);
            fieldInfoM.put((String) Types.Zones[i][0], inner);
        }

        Message Messageparser = new Message(fieldInfoM, "/");

        return Messageparser.parse(data, "");

    }

    private HashMap parseZones(String fileName) {
        
    //   System.out.println("    parseZones  ");
        
       InputStream file = OpenInputStream(fileName);
        if (file == null) {
            return new HashMap();
        } else {

        return parseZones(file);
        }
    }

  

    private InputStream OpenInputStream(String fileName) {
           
        InputStream file;
        GZIPInputStream zfile;
   
       ArrayList<Byte> data = new ArrayList();
        byte[] a = new byte[1];
        try {
            if (!isGzipped(fileName)) {
                file = new FileInputStream(fileName);
 
                while (file.read(a)>=1)for(int i =0;i<a.length;i++)data.add(a[i]);
                file.close();   
            } else {
                zfile = new GZIPInputStream(new FileInputStream(fileName));

                while(zfile.read(a)>=1)for(int i =0;i<a.length;i++)data.add(a[i]);
                zfile.close();
                }
            } catch (Exception e) {
            System.out.println("TrainingSession 466  "+e.getMessage()); 
            return null;
        }
        
        if (data.size()==0) return null;
        else {
            a = new byte[data.size()];
            for (int i = 0; i<data.size();i++)
                a[i] = data.get(i);
            
            return new ByteArrayInputStream(a);
        }
    }   
        
        

    /**
     * @brief Fetch the first item from a list contained within a QVariant.
     *
     * This is just a convenience function that prevents us from having to
     * perform the basic QList::isEmpty() check in many, many places.
     *
     * @param variant QVariant (probably) containing a list.
     *
     * @return The first item in the list, or an invalid variant if there is no
     * such list, or the list is empty.
     */
 
    
    // Funktionen für das Schreiben der daten
    
    
    private Object first( ArrayList variant) {
    
        if (variant ==null)return null;
        ArrayList list = variant;
        return (list.isEmpty()) ? null : list.get(0);
    }

    private HashMap firstMap(ArrayList list) {
        if(list==null) return null;
        return  (HashMap)list.get(0); 
    }

    private long TimetoSeconds(HashMap time) {
        if (time==null)return 0;
        long TimeSeconds = 0;
        ArrayList<Long> dummy = new ArrayList();
        dummy.add((long)0);
        ArrayList hours = (ArrayList) time.getOrDefault("hours",dummy);
        ArrayList minutes = (ArrayList) time.getOrDefault("minutes",dummy);
        ArrayList seconds = (ArrayList) time.getOrDefault("seconds",dummy);
        ArrayList milliseconds = (ArrayList) time.getOrDefault("milliseconds",dummy);
       
        TimeSeconds =  (long) hours.get(0)*3600 +
                + (long) minutes.get(0)*60 +
                + (long) seconds.get(0) +
                + (long) ((long)milliseconds.get(0)/1000.0);
        return TimeSeconds;
    }
    
    
    private GregorianCalendar getDateTime( HashMap map)
{
        if(map==null) return new GregorianCalendar();
        HashMap date = firstMap((ArrayList)map.get( "date"));
        if(date==null) return new GregorianCalendar();
        HashMap time = firstMap((ArrayList)map.get( "time"));
        if(time==null) return new GregorianCalendar();
        if(date.isEmpty()||time.isEmpty())return new GregorianCalendar();
        
       GregorianCalendar dateTime = new GregorianCalendar((int)(long)((ArrayList) date.get("year")).get(0),
                            (int)(long)((ArrayList)date.get("month")).get(0)-1,
                            (int)(long)((ArrayList)date.get("day")).get(0),
                            (int)(long)((ArrayList)time.get("hour")).get(0),
                            (int)(long)((ArrayList)time.get("minute")).get(0),
                            (int)(long)((ArrayList)time.get("seconds")).get(0));

        return dateTime;
    }


    private String getFileName( String file)
{

    return file.substring(file.lastIndexOf("/"), file.length());
    }


/// @see http://www.topografix.com/GPX/1/1/gpx.xsd
   
    private String formatTime (GregorianCalendar gc){
         String aktZeit =  ""+gc.get(Calendar.YEAR) +"-" 
                      + (int)(gc.get(Calendar.MONTH)+1) +"-"
                      + gc.get(Calendar.DATE)+"T" 
                      + gc.get(Calendar.HOUR_OF_DAY)+":"
                      + gc.get(Calendar.MINUTE)+":" 
                      + gc.get(Calendar.SECOND);
        
        return aktZeit;
        
    }
    
    private String toGPX() {
        GregorianCalendar gc = new GregorianCalendar();  //aktuelle Zeit ermitteln
        String aktZeit = formatTime(gc);

        boolean gpxOK = false;
        
        StringBuffer doc = new StringBuffer();

        doc.append("<?xml version='1.0' encoding='utf-8'?>\n"
                + //Kopfzeilen einfach kopiert aus default
                "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\"\n"
                + "xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 "
                + "http://www.topografix.com/GPX/1/1/gpx.xsd\" "
                + "creator=\"V800 Downloader  - https://github.com/pcolby/bipolar\" \n"
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n"
                + "version=\"1.1\">\n");

        doc.append("<metadata>\n");
        doc.append("\t<name>" + getFileName(baseName) + "</name>\n");
        doc.append("\t<desc> GPX encoding of" + getFileName(baseName) + "</desc>\n");
        doc.append("\t<author>\n"
                + "\t\t<link href=\"https://github.com/pcolby/bipolar\">\n"
                + "\t\t\t<text>Created by Ciclotronic, based on Bipolar</text>\n"
                + "\t\t</link>\n"
                + "\t</author>\n");

        doc.append("\t<time>" + aktZeit + "Z</time>\n");
        doc.append("</metadata>\n");

        for (Iterator it = parsedExercises.entrySet().iterator(); it.hasNext();) {
            Map.Entry exercise = (Map.Entry) it.next();
            doc.append("<trk>\n");
            doc.append("\t<src>\n");

            HashMap map = (HashMap) exercise.getValue();

            ArrayList<String> sources = (ArrayList) map.get("sources");

            for (int i = 0; i < sources.size(); i++) {
                doc.append(sources.get(i));
            }
            doc.append("\n\t</src>\n");
            doc.append("\t<trkseg>\n");

            HashMap route = (HashMap) map.get(ROUTE);
            if (route != null) {
                gpxOK = true;
                StartTime = new GregorianCalendar();

                StartTime = getDateTime(firstMap((ArrayList) route.get("timestamp")));
                             
                ArrayList altitude = (ArrayList) route.get("altitude");

                ArrayList duration = (ArrayList) route.get("duration");

                ArrayList latitude = (ArrayList) route.get("latitude");

                ArrayList longitude = (ArrayList) route.get("longitude");

                ArrayList satellites = (ArrayList) route.get("satellites");

                if ((duration.size() != altitude.size())
                        || (duration.size() != latitude.size())
                        || (duration.size() != longitude.size())
                        || (duration.size() != satellites.size())) {
                    System.out.println("TrainingSession 1092  toGPX  duration size passt nicht zu latitude;longitude;altitude oder satellites");
                }
                GregorianCalendar temptime = StartTime;
                for (int i = 0; i < duration.size(); i++) {
                    doc.append("\t\t<trkpt lon=\"" + longitude.get(i) + "\"     lat=\"" + latitude.get(i) + "\" >\n");
                    doc.append("\t\t\t<ele>" + altitude.get(i) + "</ele>\n");

                    temptime = (GregorianCalendar) StartTime.clone();
                    temptime.add(Calendar.MILLISECOND, (int) (long) duration.get(i));

                    doc.append("\t\t\t<time>" + formatTime(temptime) + "</time>\n");
                    doc.append("\t\t\t<sat>" + satellites.get(i) + "</sat>\n");
                    doc.append("\t\t</trkpt>\n");

                };
              
            };
            doc.append("\t</trkseg>\n");
            doc.append("</trk>\n");
        };
        doc.append("</gpx>");

        if (gpxOK)return doc.toString();else return"";
    }


    
    

    public boolean writeData(String fileName) {

 
        String data = getDataFile();
        
        if (data == null) {
            System.out.println("TrainingSession 686, String data = null");
            return false;
        }
       
        
        Properties cfg = getDataProperty();
        
        if (cfg == null) {
            System.out.println("TrainingSession 681, Property cfg = null");
            return false;
        }
        
        
        
        
        String cfg_file = fileName + cfg.getProperty("Jahr") + cfg.getProperty("Monat")
                + cfg.getProperty("Tag")
                + cfg.getProperty("Stunde")
                + cfg.getProperty("Minute")
             //   + cfg.getProperty("Sekunde") 
                + "_Tour.cfg";

        File file = new File(cfg_file);

        FileWriter output;
        try {
            output = new FileWriter(file);  //Datei wird überschrieben

        } catch (Exception e) {
            System.out.println("TrainingSession 703, Fehler bei OuputStream " + e.getMessage());
            return false;
        }

        try {// device.write(data);
            cfg.store(output, "Tour Eigenschaften: "
                    + cfg.getProperty("Jahr") + cfg.getProperty("Monat")
                    + cfg.getProperty("Tag")
                    + cfg.getProperty("Stunde")
                    + cfg.getProperty("Minute")
                    + cfg.getProperty("Sekunde"));

            output.flush();
            output.close();
        
        } catch (Exception e) {
            System.out.println("TrainingSession 721, Fehler beim Schreiben der Property Datei");
            return false;
        }

        String data_file = fileName + cfg.getProperty("Jahr") + cfg.getProperty("Monat")
                + cfg.getProperty("Tag")
                + cfg.getProperty("Stunde")
                + cfg.getProperty("Minute")
             //   + cfg.getProperty("Sekunde") 
                + "_Tour.txt";

        file = new File(data_file);

        try {
            output = new FileWriter(file);  //Datei wird überschrieben

        } catch (Exception e) {
            System.out.println("TrainingSession 737, Fehler bei OuputStream " + e.getMessage());
            return false;
        }

        try {

            output.write(data);

            output.flush();
            output.close();

        } catch (Exception e) {
            System.out.println("TrainingSession 49, Fehler beim Schreiben der Daten Datei");
            return false;
        }

        return true;
    }
    
    
    public boolean writeGPX(String fileName) {

        DecimalFormat form = new DecimalFormat("00");
        
        
        String gpx = toGPX();
        if (gpx == null) {
            System.out.println("TrainingSession 706, String gpx = null");

            return false;
        }
        if (gpx.equalsIgnoreCase("")) {
            gpx_file = "";
            return true;
            
        }

        gpx_file = fileName + StartTime.get(Calendar.YEAR)
                + form.format(StartTime.get(Calendar.MONTH)+1)
                + form.format(StartTime.get(Calendar.DAY_OF_MONTH))
                + form.format(StartTime.get(Calendar.HOUR_OF_DAY))
                + form.format(StartTime.get(Calendar.MINUTE))
                + form.format(StartTime.get(Calendar.SECOND)) + ".gpx";

        File file = new File(gpx_file);

        FileWriter output;
        try {
            output = new FileWriter(file);  //Datei wird überschrieben

        } catch (Exception e) {
            System.out.println("TrainingSession 724, Fehler bei OuputStream " + e.getMessage());
            return false;
        }
        try {

            output.write(gpx);

            output.flush();
            output.close();

        } catch (Exception e) {
            System.out.println("TrainingSession 735, Fehler beim Schreiben der Datei");
            return false;
        }

        return true;
    }
        


private Properties getDataProperty(){
    
    Properties DataProperty = new Properties();
    HashMap create = new HashMap();
    HashMap samples = new HashMap();
    
    DecimalFormat format = new DecimalFormat("00"); //Format für Tag und Monat
 
    for (Iterator it = parsedExercises.entrySet().iterator(); it.hasNext();) {
            Map.Entry exercise = (Map.Entry) it.next();
            HashMap map = (HashMap) exercise.getValue();
            create = (HashMap) map.get(CREATE);
            samples = (HashMap) map.get(SAMPLES);
    }
    
    DataProperty.setProperty("Computer", "Polar_V/M_Serie");  
    HashMap temp2 = null;
   
    HashMap temp = firstMap((ArrayList) parsedSession.get("start"));
    if(temp!=null) temp2 = firstMap((ArrayList) temp.get("date"));
    if(temp2!=null) DataProperty.setProperty("Jahr", ((ArrayList) temp2.get("year")).get(0).toString());
    if(temp2!=null) DataProperty.setProperty("Monat", format.format(((ArrayList) temp2.get("month")).get(0)));
    if(temp2!=null) DataProperty.setProperty("Tag", format.format(((ArrayList) temp2.get("day")).get(0)));        
    
    temp2 = firstMap((ArrayList) temp.get("time"));
    if(temp2!=null) DataProperty.setProperty("Stunde",format.format(((ArrayList) temp2.get("hour")).get(0)));
    if(temp2!=null) DataProperty.setProperty("Minute",format.format(((ArrayList) temp2.get("minute")).get(0)));
    if(temp2!=null) DataProperty.setProperty("Sekunde",format.format(((ArrayList) temp2.get("seconds")).get(0)));
     
 
        DataProperty.setProperty("Dauer", ""+Dauer);

     long intervall = TimetoSeconds(firstMap((ArrayList) samples.get("record-interval"))); 
   
 
         DataProperty.setProperty("Anzahl Datenpunkte",""+Datenpunkte);
                 
    ArrayList dummylist = (ArrayList) parsedSession.get("distance");
    float Strecke = 0;
    if(dummylist !=null) Strecke = (float)dummylist.get(0);
    DecimalFormat formatb = new java.text.DecimalFormat("0.00");
    DataProperty.setProperty("Strecke", formatb.format(Strecke/1000.0)); 
    DataProperty.setProperty("Kalorien",  ((ArrayList) parsedSession.get("calories")).get(0).toString()); 
    
    temp2 = firstMap((ArrayList) parsedSession.get("training-load"));
    if(temp2!=null)DataProperty.setProperty("Belastung",  ((ArrayList) temp2.get("load-value")).get(0).toString()); 
    if(temp2!=null)DataProperty.setProperty("Erholungszeit", ""+TimetoSeconds(firstMap((ArrayList)temp2.get("recovery-time")))); 
    if(temp2!=null)DataProperty.setProperty("Kohlenhydrate", ((ArrayList) temp2.get("carbs")).get(0).toString());
    if(temp2!=null)DataProperty.setProperty("Proteine", ((ArrayList) temp2.get("protein")).get(0).toString());
    if(temp2!=null)DataProperty.setProperty("Fett", ((ArrayList) temp2.get("fat")).get(0).toString());
     
    temp = firstMap((ArrayList) create.get("running-index"));
    if(temp!=null) DataProperty.setProperty("Running-Index",((ArrayList) temp.get("value")).get(0).toString());
    
    temp2 = firstMap((ArrayList) parsedSession.get("session-name"));
     if(temp2!=null)DataProperty.setProperty("Typ", ((ArrayList) temp2.get("text")).get(0).toString());

     temp = firstMap((ArrayList) parsedPhysicalInformation.get("birthday"));
     
    if(temp!=null) temp2 = firstMap((ArrayList) temp.get("value"));
    if(temp2!=null) DataProperty.setProperty("Geburtsdatum", ((ArrayList) temp2.get("day")).get(0).toString()+"."+
                                                           ((ArrayList) temp2.get("month")).get(0).toString()+"."+
                                                           ((ArrayList) temp2.get("year")).get(0).toString());        
     
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("weight"));
    if(temp!=null) DataProperty.setProperty("Gewicht",((ArrayList) temp.get("value")).get(0).toString());
    
    
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("height"));
    if(temp!=null) DataProperty.setProperty("Größe",((ArrayList) temp.get("value")).get(0).toString());
     
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("maximum-heartrate"));
    if(temp!=null) DataProperty.setProperty("obere HF",((ArrayList) temp.get("value")).get(0).toString());
     
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("resting-heartrate"));
    if(temp!=null) DataProperty.setProperty("untere HF",((ArrayList) temp.get("value")).get(0).toString());
     
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("aerobic-threshold"));
    if(temp!=null) DataProperty.setProperty("Aerobe Schwelle",((ArrayList) temp.get("value")).get(0).toString());
     
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("anareobic-threshold"));
    if(temp!=null) DataProperty.setProperty("Anerobe Schwelle",((ArrayList) temp.get("value")).get(0).toString());
    
    temp = firstMap((ArrayList) parsedPhysicalInformation.get("vo2max"));
    if(temp!=null) DataProperty.setProperty("vo2max",((ArrayList) temp.get("value")).get(0).toString());
 
    if(!gpx_file.equalsIgnoreCase("")) DataProperty.setProperty("GoogleEarth",gpx_file);
    
    return DataProperty;
}






private String getDataFile() {
        GregorianCalendar gc = new GregorianCalendar();  //aktuelle Zeit ermitteln
    //    String aktZeit = formatTime(gc);

    boolean alt = false;
    boolean cad = false;
    boolean dist = false;
    boolean spd = false;
    boolean heart = false;
    boolean temper = false;
    boolean schritt = false;

    DecimalFormat format = new DecimalFormat("0000000");
    DecimalFormat formatneg = new DecimalFormat("000000");

        StringBuffer doc = new StringBuffer();

        for (Iterator it = parsedExercises.entrySet().iterator(); it.hasNext();) {
            Map.Entry exercise = (Map.Entry) it.next();
            HashMap map = (HashMap) exercise.getValue();
            ArrayList<String> sources = (ArrayList) map.get("sources");
            GregorianCalendar startTime = new GregorianCalendar();
            HashMap samples = (HashMap) map.get(SAMPLES);
            if(samples == null)continue;
            
            
            ArrayList cadence = (ArrayList) samples.get("cadence");
            if (cadence != null) {
                cad = true;
            } else {
                cad = false;
            }
            ArrayList distance = (ArrayList) samples.get("distance");
            if (distance != null) {
                dist = true;
            } else {
                dist = false;
            }
            ArrayList speed = (ArrayList) samples.get("speed");
            if (speed != null) {
                spd = true;
            } else {
                spd = false;
            }
            ArrayList heartrate = (ArrayList) samples.get("heartrate");
            if (heartrate != null) {
                heart = true;
            } else {
                heart = false;
            }
            ArrayList temperature = (ArrayList) samples.get("temperature");
            if (temperature != null) {
                temper = true;
            } else {
                temper = false;
            }
            ArrayList stride_length = (ArrayList) samples.get("stride-length");
            if (stride_length != null) {
                schritt = true;
            } else {
                schritt = false;
            }
            ArrayList altitude = (ArrayList) samples.get("altitude");
            if (altitude != null) {
                alt = true;
            } else {
                alt = false;
            }
            ArrayList correction = (ArrayList) samples.get("altitude-calibration");
            float adder = 0;
            if (correction != null) {
                HashMap correctvalue = (HashMap) correction.get(correction.size() - 1);
                ArrayList correct = (ArrayList) correctvalue.get("value");
                if (correct != null) {
                    adder = (float) correct.get(correct.size() - 1);
                }

            }
 
            HashMap create = (HashMap) map.get(CREATE);
            if(create != null) startTime = getDateTime(firstMap((ArrayList) create.get("start")));  //gregorian calendar
            else startTime = new GregorianCalendar();
            long intervall = TimetoSeconds(firstMap((ArrayList) samples.get("record-interval")));

            if (((cad && dist) && (cadence.size() != distance.size()))
                    || ((cad && spd) && (cadence.size() != speed.size()))
                    || ((cad && heart) && (cadence.size() != heartrate.size()))
                    || ((cad && schritt) && (cadence.size() != stride_length.size()))
                    || ((cad && temper) && (cadence.size() != temperature.size()))) {
                System.out.println("TrainingSession 752  toData  size passt nicht");
                return "";
            }
            GregorianCalendar temptime = startTime;
            int Zeitpunkt = 0;
            int temp;
            Datenpunkte = heartrate.size();
            for (int i = 0; i < Datenpunkte; i++) {   //spezielles casting, da die Werte unterschiedlich in den ArrayList abgelegt sind aber immer int benötigt werden
                Zeitpunkt = (int) (intervall * i);
                doc.append(format.format(Zeitpunkt)).append("\t");
                doc.append(format.format((int) ((float) distance.get(i)))).append("\t");
                if (alt) {
                    temp = (int) (((float) (altitude.get(i)) + adder) * 10.0 + 0.5);
                } else {
                    temp = 0;
                }
                if (temp < 0) {
                    doc.append(formatneg.format(temp)).append("\t");
                } else {
                    doc.append(format.format(temp)).append("\t");
                }
                if (heart) {
                    temp = (int) (long) heartrate.get(i);
                } else {
                    temp = 0;
                }
                if (temp < 0) {
                    doc.append(formatneg.format(temp)).append("\t");
                } else {
                    doc.append(format.format(temp)).append("\t");
                }
                if (cad) {
                    doc.append(format.format((int) (long) cadence.get(i))).append("\t");
                } else {
                    doc.append(format.format(0)).append("\t");
                }
                if (schritt) {
                    doc.append(format.format((int) (long) stride_length.get(i))).append("\t");
                } else {
                    doc.append(format.format(0)).append("\t");
                }
                if (spd) {
                    doc.append(format.format((int) ((float) speed.get(i) * 100.0))).append("\t");
                } else {
                    doc.append(format.format(0)).append("\t");
                }
                if (temper) {
                    temp = (int) ((float) temperature.get(i) * 10.0);
                } else {
                    temp = 0;
                }
                if (temp < 0) {
                    doc.append(formatneg.format(temp)).append("\n");
                } else {
                    doc.append(format.format(temp)).append("\n");
                }
            }
            Dauer = Zeitpunkt;
        }

        return doc.toString();
    }



}
