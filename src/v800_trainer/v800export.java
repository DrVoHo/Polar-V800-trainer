package v800_trainer;

/*
 * v800expoert.java
 *
 * SourceFile is part of Chainwheel

 * Oberste Funktionen zum Auslesen V800 Daten
 */



/**
 *
 * @author and migration to java volker hochholzer
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



/*
    Copyright 2014 Christian Weber

    This file is part of V800 Downloader.

    V800 Downloader is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    V800 Downloader is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with V800 Downloader.  If not, see <http://www.gnu.org/licenses/>.

*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.ProgressMonitor;


public class v800export {

    public void V800export() {
    }

    public void export_sessions(JCicloTronic THandle, ArrayList<String> sessions) {

        Thread main = Thread.currentThread();
        TronicHandle = THandle;


                for (int sessions_iter = 0; sessions_iter < sessions.size(); sessions_iter++) {
                    if(TronicHandle.pm!=null)TronicHandle.pm.setNote(sessions.get(sessions_iter));
                    if(TronicHandle.pm!=null)TronicHandle.pm.setProgress((int) 100.0 * sessions_iter / sessions.size());
                    File filter_dir = new File(sessions.get(sessions_iter));

                    File[] dummy = filter_dir.listFiles();//list(new DirFilter(""));

                    ArrayList<String> multi_sessions = new ArrayList<String>();
                    if (dummy == null) {
                        return;
                    }
                    for (int j = 0; j < dummy.length; j++) {
                        if (dummy[j].isDirectory()) {
                            multi_sessions.add(dummy[j].getPath());
                        }
                    }
                    if (multi_sessions.size() == 0) {
                        multi_sessions.add(sessions.get(sessions_iter));
                    }

                    int multi_sessions_iter;

                    for (multi_sessions_iter = 0; multi_sessions_iter < multi_sessions.size(); multi_sessions_iter++) {
                        if (make_bipolar_names(multi_sessions.get(multi_sessions_iter)) == false) {

                        }

                        String dummy2 = multi_sessions.get(multi_sessions_iter);
                        dummy2 = dummy2.substring(dummy2.lastIndexOf("\\") + 1);
                        String session_info = multi_sessions.get(multi_sessions_iter) + "/v2-users-0000000-training-sessions-" + dummy2; //)).arg(default_dir).arg(multi_sessions[multi_sessions_iter]).arg(multi_sessions[multi_sessions_iter]));
                        TrainingSession parser = new TrainingSession(session_info);

                        //              parser.setTcxOption(0x0001, true);  //ForceTcxUTC = 0x0001
                        if (parser.parse() == false) {
                            System.out.println("V800export 109 parser Error");
                            return;
                        }

                        String gpx = TronicHandle.Properties.getProperty("GPS.dir", TronicHandle.SystemProperties.getProperty("user.dir"))
                                + TronicHandle.SystemProperties.getProperty("file.separator").toString();

                        if (parser.writeGPX(gpx) == false) {
                            //              System.out.println("v800export 91  Fehler beim Erstellen gpx Datei:  " + gpx);

                        }

                        String data = TronicHandle.Properties.getProperty("data.dir", TronicHandle.SystemProperties.getProperty("user.dir"))
                                + TronicHandle.SystemProperties.getProperty("file.separator").toString();

                        if (parser.writeData(data) == false) {
                            System.out.println("v800export 125  Fehler beim Erstellen data Datei:  " + data);
                            return;
                        }

                        //unbenötigte Files löschen!
                        File fileInfo = new File(session_info.substring(0, session_info.lastIndexOf("/")));

                        String[] dummy3 = fileInfo.list(new v800export.DirFilter("v2")); //path.list(new DirFilter("_Tour.cfg"));

                        for (int i = 0; i < dummy3.length; i++) {
                            File datei = new File(fileInfo.getPath() + "/" + dummy3[i]);
                            if (datei.exists()) {
                                datei.delete();

                            }

                        }

                    } // Ende multisessions iter

                    
  

            } //Ende sessions iter


    }
    
    
    private static boolean make_bipolar_names(String session) {
      
    //syntetic names - not changed to reduce migration effort; files are deleted at the end   
        String session_path = session;
        session = session_path.substring(session_path.lastIndexOf("\\") + 1);

        File file = new File(session_path + "/TSESS.BPB");
        String new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-create";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        } else {
            return false;
        }

        file = new File(session_path + "/PHYSDATA.BPB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-physical-information";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        } else {
            return false;
        }

        file = new File(session_path + "/BASE.BPB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-create";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        } else {
            return false;
        }

        file = new File(session_path + "/ALAPS.BPB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-autolaps";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        file = new File(session_path + "/LAPS.BPB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-laps";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        file = new File(session_path + "/ROUTE.GZB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-route";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        file = new File(session_path + "/SAMPLES.GZB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-samples";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        file = new File(session_path + "/STATS.BPB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-statistics";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        file = new File(session_path + "/ZONES.BPB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-zones";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        file = new File(session_path + "/RR.GZB");
        new_file = session_path + "/v2-users-0000000-training-sessions-" + session + "-exercises-" + session + "-rrsamples";//.arg(session_path).arg(session);
        if (file.exists() == true) {
            try {
                copyFile(file, new File(new_file));
            } catch (Exception e) {
            }
        }

        return true;
    }

    public static class DirFilter implements FilenameFilter {

        String afn;

        DirFilter(String afn) {
            this.afn = afn;
        }

        public boolean accept(File dir, String name) {
        
            String f = new File(name).getName();
            return f.indexOf(afn) != -1;
        }
    }

   

    private static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    private JCicloTronic TronicHandle;

    private static String Raw_data;
    private String FileSeparator;

    public boolean abort;

}
