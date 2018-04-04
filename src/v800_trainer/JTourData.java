package v800_trainer;

 /*
 * JTourData.java
 
 *
 * SourceFile is part of Chainwheel

 * Reads TourData Files (Data and Property) for Program Options
 



/**
 *
 * @author  volker hochholzer
 * 
 * Chainwheel and all dependend source files is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Chainwheel is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 

 * Created on 30. November 2000, 20:59
 */


import java.io.BufferedInputStream;
import java.io.File;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class JTourData extends Object {

    

    public JTourData(String Datei, JCicloTronic TronicH) {
        TronicHandle = TronicH;
        Dateiname = Datei;
        if (Dateiname == null) {
            Tag = 1;
            Monat = 1;
            Jahr = 1900;
            return;
        }
        
        Teiler = 100;  //Teiler für Anzahl an Datenpunkte für Strecken oder Zeitunterschied
 
        int i, j, m;
        int FileModified = 0;
        int ohneHf, ohneCadence;

        int ohneSteigmetpos, ohneSteigmetneg;

        int Höhenmeterschwelle;
        int HFMittelwert = 1;
        int GeschwMittelwert = 1;
        int HmMittelwert = 1;
        int SteigpMittelwert = 1;
        int SteigmMittelwert = 1;
        int CadenceMittelwert = 1;
        int SchrittlängeMittelwert = 1;
        int S;
        int[] Temp = {0, 0, 0, 0, 0, 0};
        float Hmtempp = 0, Hmtempm = 0;
        float DeltaHm = 0;
        boolean ignore = true;
        boolean polar = false;
        boolean HAC = false;
        boolean V800 = false;
        int Datensatz = 0;
        int Datenlänge = 0;
     

        Höhenmeterschwelle = Integer.parseInt(TronicHandle.Properties.getProperty("Höhenmeterakkumulierung", "1"));

        if (!TronicHandle.Editmode) {
            HFMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("HFMittelwert", "1"));
            GeschwMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("GeschwMittelwert", "1"));
            HmMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("HmMittelwert", "1"));
            SteigpMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("SteigpMittelwert", "1"));
            SteigmMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("SteigmMittelwert", "1"));
            CadenceMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("CadenceMittelwert", "1"));
            SchrittlängeMittelwert = Integer.parseInt(TronicHandle.Properties.getProperty("SchrittlängeMittelwert", "1"));
        };

        DataProperty = new java.util.Properties();

        Konfigfile = Dateiname + ".cfg";

        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(Konfigfile));
      //      FileInputStream in = new FileInputStream(Konfigfile);
            DataProperty.load(in);
            in.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IO-Fehler bei " + Dateiname + e, "Achtung!", JOptionPane.ERROR_MESSAGE);
        };

        Notiz = DataProperty.getProperty("Notiz", "keine");
        Datum = DataProperty.getProperty("Tag", "11") + "." + DataProperty.getProperty("Monat", "11") + "." + DataProperty.getProperty("Jahr", "1111");
        Tag = Integer.parseInt(DataProperty.getProperty("Tag", "11"));
        Monat = Integer.parseInt(DataProperty.getProperty("Monat", "11"));
        Jahr = Integer.parseInt(DataProperty.getProperty("Jahr", "1111"));
        StartStunden = Integer.parseInt(DataProperty.getProperty("Stunde", "11"));
        StartMinuten = Integer.parseInt(DataProperty.getProperty("Minute", "11"));
        Datenpunkte = Integer.parseInt(DataProperty.getProperty("Anzahl Datenpunkte", "0"));
        Streckenlänge = Float.parseFloat(DataProperty.getProperty("Strecke", "0").replace(',', '.'));
        String computer = DataProperty.getProperty("Computer", "HAC");

        Kalorien = Integer.parseInt(DataProperty.getProperty("Kalorien", "0"));
        Fett = Integer.parseInt(DataProperty.getProperty("Fett", "0"));
        Protein = Integer.parseInt(DataProperty.getProperty("Proteine", "0"));
        Belastung = Integer.parseInt(DataProperty.getProperty("Belastung", "0"));
        Erholungszeit = Integer.parseInt(DataProperty.getProperty("Erholungszeit","0"));
        Lauf_Index = Integer.parseInt(DataProperty.getProperty("Running-Index","0"));
        gpx_File = DataProperty.getProperty("GoogleEarth","");
        
        if (computer.equalsIgnoreCase("HAC")) {
            HAC = true;
            Datenlänge = 6;
            Datensatz = 42;  //6 Daten a 6 Zeichen + Tab
        }
        if (computer.equalsIgnoreCase("Polar")) {
            polar = true;
            Datenlänge = 6;
            Datensatz = 42; //6 Daten a 6 Zeichen + Tab
        }
        if (computer.equalsIgnoreCase("Polar_V/M_Serie")) {
            V800 = true;
            Datenlänge = 7;
            Datensatz = 64;  //8 Daten a 7 Zeichen + Tab
        }

        if (Datenpunkte == 0) {
           // JOptionPane.showMessageDialog(null, "Keine Daten im Datenfile " + Dateiname, "Achtung!", JOptionPane.ERROR_MESSAGE);
            return;

        }



       byte[] Data = new byte[Datenpunkte * Datensatz];
        try {
     
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(Dateiname + ".txt"));
            readData = in.read(Data);
            in.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IO-Fehler bei " + Dateiname + e, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        if (Data[Datensatz - 1] == 0x0D && Data[Datensatz] == 0x0A) {
            FileModified = 1;

            // Daten sind modifiziert worden (Word) und müssen neu geladen werden
            Data = new byte[Datenpunkte * (Datensatz + FileModified)];
            try {
                //FileInputStream in = new FileInputStream(Dateiname + ".txt");
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(Dateiname + ".txt"));
                readData = in.read(Data);
                in.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "IO-Fehler bei " + Dateiname + e, "Achtung!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (Datenpunkte * (Datensatz + FileModified) != readData) {
            JOptionPane.showMessageDialog(null, "JTourData\nFehler in Datenfile " + Dateiname + "\n" + Datenpunkte * (Datensatz + FileModified) + " " + readData + "\n" + "modifiziertes File", "Achtung", JOptionPane.ERROR_MESSAGE);
            return;
        }
         String Dummy = new String(Data);
        InitArrays();
        j = 0;
        ohneHf = 0;
        ohneCadence = 0;
        Strecke_Anstieg = 0;
        Strecke_Gefälle = 0;
        ohneSteigmetpos = 0;
        ohneSteigmetneg = 0;
        gesZeit[0] = Integer.parseInt(Dummy.substring(0, Datenlänge));
        gefZeit[0] = gesZeit[0];
        Geschw_gesZeit[0] = 0;
        Strecke_gesZeit[0] = (float) Integer.parseInt(Dummy.substring(Datenlänge + 1, 2* Datenlänge + 1)) / (float) 100.0;
        Hoehe_gesZeit[0] = Integer.parseInt(Dummy.substring(2 * (Datenlänge + 1), 2 * (Datenlänge + 1) + Datenlänge));
        if (V800) {
            Hoehe_gesZeit[0] = (float) (Hoehe_gesZeit[0] / 10.0);
        }
        Hf_gesZeit[0] = Integer.parseInt(Dummy.substring(3 * (Datenlänge + 1), 3 * (Datenlänge + 1) + Datenlänge));
        Steigp_gesZeit[0] = 0;
        Steigm_gesZeit[0] = 0;

        Cadence_gesZeit[0] = Integer.parseInt(Dummy.substring(4 * (Datenlänge + 1), 4 * (Datenlänge + 1) + Datenlänge));

        if (HAC) {
            Temperatur_gesZeit[0] = Integer.parseInt(Dummy.substring(5 * (Datenlänge + 1), 5 * (Datenlänge + 1) + Datenlänge));
        }
        if (polar) {
            Geschw_gesZeit[0] = (float) (Integer.parseInt(Dummy.substring(5 * (Datenlänge + 1), 5 * (Datenlänge + 1) + Datenlänge)) / 10.0);
            Temperatur_gesZeit[0] = 0;
        }
        if (V800) {
            Schritt_länge[0] = (int) Integer.parseInt(Dummy.substring(5 * (Datenlänge + 1), 5 * (Datenlänge + 1) + Datenlänge));
            Geschw_gesZeit[0] = (float) (Integer.parseInt(Dummy.substring(6 * (Datenlänge + 1), 6 * (Datenlänge + 1) + Datenlänge)) / 100.0);
            Temperatur_gesZeit[0] = (float) (Integer.parseInt(Dummy.substring(7 * (Datenlänge + 1), 7 * (Datenlänge + 1) + Datenlänge)) / 10.0);
            Strecke_gesZeit[0] = (float) (Strecke_gesZeit[0] / 10.0);
        }

        max_Hoehe = Hoehe_gesZeit[0];
        min_Hoehe = Hoehe_gesZeit[0];
        max_Temperatur = Temperatur_gesZeit[0];
        min_Temperatur = Temperatur_gesZeit[0];

//RAW werden für TourEditor und Statistik verwenden.

        RAWHF[0] = Hf_gesZeit[0];
        RAWGeschw[0] = Geschw_gesZeit[0];
        RAWHm[0] = Hoehe_gesZeit[0];
        RAWCadence[0] = Cadence_gesZeit[0];
        RAWSchrittlänge[0] = Schritt_länge[0];

        float TempHFbuffer = 0;
        float TempGeschwbuffer = 0;
        float TempHmbuffer = 0;
        float TempSteigp[] = new float[Datenpunkte];
        float TempSteigpbuffer = 0;
        float TempSteigm[] = new float[Datenpunkte];
        float TempSteigmbuffer = 0;
        int TempCadencebuffer = 0;
        int TempSchrittlängebuffer = 0;
 

        for (i = 1; i < Datenpunkte; i++) {
            try {
                gesZeit[i] = Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified), i * (Datensatz + FileModified) + Datenlänge));
                Strecke_gesZeit[i] = (float) Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + Datenlänge + 1, i * (Datensatz + FileModified) + 2 * Datenlänge + 1)) / (float) 100.0;
                Hoehe_gesZeit[i] = Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + 2 * (Datenlänge + 1), i * (Datensatz + FileModified) + 2 * (Datenlänge + 1) + Datenlänge));
                Hf_gesZeit[i] = Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + 3 * (Datenlänge + 1), i * (Datensatz + FileModified) + 3 * (Datenlänge + 1) + Datenlänge));
                Cadence_gesZeit[i] = Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + 4 * (Datenlänge + 1), i * (Datensatz + FileModified) + 4 * (Datenlänge + 1) + Datenlänge));
                Temperatur_gesZeit[i] = Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + 5 * (Datenlänge + 1), i * (Datensatz + FileModified) + 5 * (Datenlänge + 1) + Datenlänge));
                if (i == 1) {
                    max_Temperatur = Temperatur_gesZeit[1];
                    min_Temperatur = max_Temperatur;
                }

                if (HAC) {
                    Geschw_gesZeit[i] = (Strecke_gesZeit[i] - Strecke_gesZeit[i - 1]) / (gesZeit[i] - gesZeit[i - 1]) * (float) 3600.0;
                }
                if (polar) {
                    Geschw_gesZeit[i] = (float) (Temperatur_gesZeit[i] / 10.0);
                    Temperatur_gesZeit[i] = 0;
                }
                if (V800) {
                    Hoehe_gesZeit[i] = (float) (Hoehe_gesZeit[i] / 10.0);
                    Strecke_gesZeit[i] = (float) (Strecke_gesZeit[i] / 10.0);
                    Schritt_länge[i] = (int) Temperatur_gesZeit[i];
                    Geschw_gesZeit[i] = (float) (Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + 6 * (Datenlänge + 1), i * (Datensatz + FileModified) + 6 * (Datenlänge + 1) + Datenlänge)) / 100.0);
                    Temperatur_gesZeit[i] = (float) (Integer.parseInt(Dummy.substring(i * (Datensatz + FileModified) + 7 * (Datenlänge + 1), i * (Datensatz + FileModified) + 7 * (Datenlänge + 1) + Datenlänge)) / 10.0);
                }

                RAWGeschw[i] = Geschw_gesZeit[i];
                RAWHF[i] = Hf_gesZeit[i];
                RAWHm[i] = Hoehe_gesZeit[i];
                RAWCadence[i] = Cadence_gesZeit[i];
                RAWSchrittlänge[i] = Schritt_länge[i];

                DeltaHm = RAWHm[i] - RAWHm[i-1];
                Steigm_gesZeit[i] = 0;
                Steigp_gesZeit[i] = 0;
                if (DeltaHm == 121 || DeltaHm == -128) {
                    ignore = true;
                }
                if (ignore == true && Datenpunkte >= 3) {     //so wird der erste Datenwert immer ignoriert, da ignore mit true initialisiert wird
                    if (DeltaHm != 121 && DeltaHm != -128) {
                        ignore = false;
                    }
                } else {

                    Steigm_gesZeit[i] = java.lang.Math.round(DeltaHm * 60 / (gesZeit[i] - gesZeit[i - 1]));
                    TempSteigm[i] = Steigm_gesZeit[i];

                    m = 1;
                    while (Strecke_gesZeit[i] - Strecke_gesZeit[i - m] == 0 && m < i) {
                        m++; // suchen ab wann ein Streckenintervall erzeugt wurde
                    }
                    if (Strecke_gesZeit[i] - Strecke_gesZeit[i - m] != 0) {
                        Steigp_gesZeit[i] = java.lang.Math.round((Hoehe_gesZeit[i] - Hoehe_gesZeit[i - m]) / (Strecke_gesZeit[i] - Strecke_gesZeit[i - m]) / 10);
                    } else {
                        Steigp_gesZeit[i] = Steigp_gesZeit[i - 1];
                    }
                    TempSteigp[i] = Steigp_gesZeit[i];

                    if (DeltaHm > 0) {
                        Hmtempp += DeltaHm;
                        if (Hmtempm >= Höhenmeterschwelle) {
                            ges_Hoehem += Hmtempm;
                        }
                        Hmtempm = 0;
                    }
                    if (DeltaHm < 0) {
                        Hmtempm += -DeltaHm;
                        if (Hmtempp >= Höhenmeterschwelle) {
                            ges_Hoehep += Hmtempp;
                        }
                        Hmtempp = 0;
                    }
                }

                if (Hf_gesZeit[i] == 0) {
                    ohneHf++;
                }
                av_Hf = av_Hf + Hf_gesZeit[i];

                if (Cadence_gesZeit[i] == 0) {
                    ohneCadence++;
                }
                av_Cadence = av_Cadence + Cadence_gesZeit[i];
                av_Schritt_länge = av_Schritt_länge + Schritt_länge[i];

                if (Steigp_gesZeit[i] > 0) {
                    av_Steigprozentpositiv += DeltaHm;
                    Strecke_Anstieg += Strecke_gesZeit[i] - Strecke_gesZeit[i - 1];
                } else if (Steigp_gesZeit[i] < 0) {
                    av_Steigprozentnegativ += DeltaHm;
                    Strecke_Gefälle += Strecke_gesZeit[i] - Strecke_gesZeit[i - 1];
                }


                av_Temperatur = av_Temperatur + Temperatur_gesZeit[i];
                
                if(Cadence_gesZeit[i]!=0 && Schritt_länge[i] ==0){
                    Schritt_länge[i] = (int)((100.0*Geschw_gesZeit[i] / 3.6) * (gesZeit[i]-gesZeit[i-1])*60.0)/ (2*Cadence_gesZeit[i]);
                    RAWSchrittlänge[i] = Schritt_länge[i];
                } 
                

//Kurven Glätten
                m = 0;
                TempHFbuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < HFMittelwert) {
                    TempHFbuffer += RAWHF[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Hf_gesZeit[i] = TempHFbuffer / m;

         
                m = 0;
                TempGeschwbuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < GeschwMittelwert) {
                    TempGeschwbuffer += RAWGeschw[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Geschw_gesZeit[i] = TempGeschwbuffer / m;

                if (Geschw_gesZeit[i] != 0) {
                    j = j + 1;
                    gefZeit[j] = gefZeit[j - 1] + gesZeit[i] - gesZeit[i - 1];

                    av_Geschw_gesZeit[i] = Strecke_gesZeit[i] / gefZeit[j] * (float) 3600;  //Änderung

                } else {
                    av_Geschw_gesZeit[i] = av_Geschw_gesZeit[i - 1];
                }

                m = 0;
                TempHmbuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < HmMittelwert) {
                    TempHmbuffer += RAWHm[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Hoehe_gesZeit[i] = TempHmbuffer / (float) m;

                m = 0;
                TempSchrittlängebuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < SchrittlängeMittelwert) {
                    TempSchrittlängebuffer += RAWSchrittlänge[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Schritt_länge[i] = TempSchrittlängebuffer / m;

                m = 0;
                TempCadencebuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < CadenceMittelwert) {
                    TempCadencebuffer += RAWCadence[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Cadence_gesZeit[i] = TempCadencebuffer / m;
                
                
                
                
                m = 0;
                TempSteigpbuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < SteigpMittelwert) {
                    TempSteigpbuffer += TempSteigp[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Steigp_gesZeit[i] = TempSteigpbuffer / m;

                m = 0;
                TempSteigmbuffer = 0;
                while (gesZeit[i] - gesZeit[i - m] < SteigmMittelwert) {
                    TempSteigmbuffer += TempSteigm[i - m];
                    m++;
                    if (m == i) {
                        break;
                    }
                }
                Steigm_gesZeit[i] = TempSteigmbuffer / m;


                if (Steigm_gesZeit[i] > 0) {
                    av_Steigmeterpositiv = av_Steigmeterpositiv + Steigm_gesZeit[i];
                    ohneSteigmetneg++;
                } else if (Steigm_gesZeit[i] < 0) {
                    av_Steigmeternegativ = av_Steigmeternegativ + -1 * Steigm_gesZeit[i];
                    ohneSteigmetpos++;
                } else {
                    ohneSteigmetpos++;
                    ohneSteigmetneg++;
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "JTourData\nExeption " + e + '\n' + Dateiname, "Achtung!", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (ignore == false || Datenpunkte == 2) {
            if (Hmtempm >= Höhenmeterschwelle) {
                ges_Hoehem += Hmtempm;
            }
            if (Hmtempp >= Höhenmeterschwelle) {
                ges_Hoehep += Hmtempp;
            }
        }

        if (j != 0) {
            av_Geschw = Strecke_gesZeit[i - 1] / gefZeit[j - 1] * 3600;
            gefahreneZeit = gefZeit[j];
        }
        av_Hf = av_Hf / (Datenpunkte - ohneHf);
        av_Temperatur = av_Temperatur / Datenpunkte;
        av_Cadence = av_Cadence / (Datenpunkte - ohneCadence);
        av_Schritt_länge = av_Schritt_länge / (Datenpunkte - ohneCadence);
        av_Steigprozentpositiv = av_Steigprozentpositiv / Strecke_Anstieg / 10;
        av_Steigprozentnegativ = -av_Steigprozentnegativ / Strecke_Gefälle / 10;
        av_Steigmeterpositiv = av_Steigmeterpositiv / (Datenpunkte - ohneSteigmetpos);
        av_Steigmeternegativ = av_Steigmeternegativ / (Datenpunkte - ohneSteigmetneg);
        gesammtZeit = gesZeit[i - 1];
        Streckenlänge = Strecke_gesZeit[i - 1];
        Zoomstrecke = Streckenlänge;
        Kalorien_h = Kalorien/(float)(gesammtZeit/3600.0);

        for (i = 0; i < ZahlStreckenPunkte; i++) {
            //            Streckenskala[i] = (int)(i*(float)10.0);

            Streckenskala[i] = i / (float) Teiler;
            j = 0;
            //Umrechnung von feste Zeitintervallen auf feste Streckenintervallen
            while (Strecke_gesZeit[j] < Streckenskala[i] && Strecke_gesZeit[j + 1] < Streckenskala[i] && j < Datenpunkte - 2) {
                j++;
            }
            ZeitüberStrecke[i] = java.lang.Math.round(gesZeit[j] + (gesZeit[j + 1] - gesZeit[j])
                    / (Strecke_gesZeit[j + 1] - Strecke_gesZeit[j]) * (Streckenskala[i] - Strecke_gesZeit[j]));
        }

        ReadGeoData();
        
    }
    
    public void Überalles(JStatistik dummy, int Selection[], int Anzahl) {

        StatistikHandle = dummy;
        int i = 1;
        Streckenlänge = 0;
        Datenpunkte = 1;
        InitArrays();
        format = new java.text.DecimalFormat("###0.00");
        max_Hoehe = StatistikHandle.TourData[i].max_Hoehe;
        max_HoeheStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
        min_HoeheStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
        min_Hoehe = max_Hoehe;
        max_Temperatur = StatistikHandle.TourData[i].max_Temperatur;
        max_TemperaturStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
        min_TemperaturStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
        min_Temperatur = max_Temperatur;
        av_Steigprozentpositiv = 0;
        av_Steigprozentnegativ = 0;
        Strecke_Anstieg = 0;
        Strecke_Gefälle = 0;
        Zoomstrecke = 0;
        Kalorien = 0;
        Kalorien_h = 0;
        max_Kalorien = 0;
        Fett = 0;
        av_Fett = 0;
        max_Fett = 0;
        Protein = 0;
        av_Protein = 0;
        max_Protein = 0;
        Belastung = 0;
        max_Belastung = 0;
        Erholungszeit = 0;
        max_Erholungszeit = 0;
        Lauf_Index = 0;
        max_Lauf_Index = 0;
        for (i = 1; i < Anzahl + 1; i++) {
            av_Geschw = av_Geschw + StatistikHandle.TourData[i].av_Geschw * StatistikHandle.TourData[i].gefahreneZeit;
            gefahreneZeit = gefahreneZeit + StatistikHandle.TourData[i].gefahreneZeit;
            gesammtZeit = gesammtZeit + StatistikHandle.TourData[i].gesammtZeit;
            Streckenlänge = Streckenlänge + StatistikHandle.TourData[i].Streckenlänge;
            if (StatistikHandle.TourData[i].av_Geschw > av_Geschw_max) {
                av_Geschw_max = StatistikHandle.TourData[i].av_Geschw;
                av_Geschw_maxStr = "max: " + format.format(av_Geschw_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            if (StatistikHandle.TourData[i].max_Geschw > max_Geschw) {
                max_Geschw = StatistikHandle.TourData[i].max_Geschw;
                max_GeschwStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            if (StatistikHandle.TourData[i].max_Hoehe > max_Hoehe) {
                max_Hoehe = StatistikHandle.TourData[i].max_Hoehe;
                max_HoeheStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            if (StatistikHandle.TourData[i].min_Hoehe < min_Hoehe) {
                min_Hoehe = StatistikHandle.TourData[i].min_Hoehe;
                min_HoeheStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            ges_Hoehep = ges_Hoehep + StatistikHandle.TourData[i].ges_Hoehep;
            ges_Hoehem = ges_Hoehem + StatistikHandle.TourData[i].ges_Hoehem;
            if (StatistikHandle.TourData[i].max_Hf > max_Hf) {
                max_Hf = StatistikHandle.TourData[i].max_Hf;
                max_HfStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            if (StatistikHandle.TourData[i].av_Hf > av_Hf_max) {
                av_Hf_max = StatistikHandle.TourData[i].av_Hf;
                av_Hf_maxStr = "max: " + format.format(av_Hf_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            av_Hf = av_Hf + StatistikHandle.TourData[i].av_Hf * StatistikHandle.TourData[i].gesammtZeit;
            if (av_Steigprozentpositiv_max < StatistikHandle.TourData[i].av_Steigprozentpositiv) {
                av_Steigprozentpositiv_max = StatistikHandle.TourData[i].av_Steigprozentpositiv;
                av_Steigprozentpositiv_maxStr = "max: " + format.format(av_Steigprozentpositiv_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Steigprozentpositiv < StatistikHandle.TourData[i].max_Steigprozentpositiv) {
                max_Steigprozentpositiv = StatistikHandle.TourData[i].max_Steigprozentpositiv;
                max_SteigprozentpositivStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            if (av_Steigprozentnegativ_max < StatistikHandle.TourData[i].av_Steigprozentnegativ) {
                av_Steigprozentnegativ_max = StatistikHandle.TourData[i].av_Steigprozentnegativ;
                av_Steigprozentnegativ_maxStr = "max: " + format.format(av_Steigprozentnegativ_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Steigprozentnegativ < StatistikHandle.TourData[i].max_Steigprozentnegativ) {
                max_Steigprozentnegativ = StatistikHandle.TourData[i].max_Steigprozentnegativ;
                max_SteigprozentnegativStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            av_Steigprozentpositiv += StatistikHandle.TourData[i].av_Steigprozentpositiv * StatistikHandle.TourData[i].Strecke_Anstieg;
            Strecke_Anstieg += StatistikHandle.TourData[i].Strecke_Anstieg;
            av_Steigprozentnegativ += StatistikHandle.TourData[i].av_Steigprozentnegativ * StatistikHandle.TourData[i].Strecke_Gefälle;
            Strecke_Gefälle += StatistikHandle.TourData[i].Strecke_Gefälle;

            Kalorien += StatistikHandle.TourData[i].Kalorien;
            Kalorien_h += StatistikHandle.TourData[i].Kalorien_h * StatistikHandle.TourData[i].gesammtZeit;
            Fett += StatistikHandle.TourData[i].Fett * StatistikHandle.TourData[i].gesammtZeit;
            Protein += StatistikHandle.TourData[i].Protein * StatistikHandle.TourData[i].gesammtZeit;
            Belastung += StatistikHandle.TourData[i].Belastung;// * StatistikHandle.TourData[i].gesammtZeit;
            Erholungszeit += StatistikHandle.TourData[i].Erholungszeit;// * StatistikHandle.TourData[i].gesammtZeit;
            Lauf_Index += StatistikHandle.TourData[i].Lauf_Index * StatistikHandle.TourData[i].gesammtZeit;

            if (av_Steigmeterpositiv_max < StatistikHandle.TourData[i].av_Steigmeterpositiv) {
                av_Steigmeterpositiv_max = StatistikHandle.TourData[i].av_Steigmeterpositiv;
                av_Steigmeterpositiv_maxStr = "max: " + format.format(av_Steigmeterpositiv_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Steigmeterpositiv < StatistikHandle.TourData[i].max_Steigmeterpositiv) {
                max_Steigmeterpositiv = StatistikHandle.TourData[i].max_Steigmeterpositiv;
                max_SteigmeterpositivStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (av_Steigmeternegativ_max < StatistikHandle.TourData[i].av_Steigmeternegativ) {
                av_Steigmeternegativ_max = StatistikHandle.TourData[i].av_Steigmeternegativ;
                av_Steigmeternegativ_maxStr = "max: " + format.format(av_Steigmeternegativ_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Steigmeternegativ < StatistikHandle.TourData[i].max_Steigmeternegativ) {
                max_Steigmeternegativ = StatistikHandle.TourData[i].max_Steigmeternegativ;
                max_SteigmeternegativStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            av_Steigmeterpositiv = av_Steigmeterpositiv + StatistikHandle.TourData[i].av_Steigmeterpositiv * StatistikHandle.TourData[i].gesammtZeit;
            av_Steigmeternegativ = av_Steigmeternegativ + StatistikHandle.TourData[i].av_Steigmeternegativ * StatistikHandle.TourData[i].gesammtZeit;
            if (max_Temperatur < StatistikHandle.TourData[i].max_Temperatur) {
                max_Temperatur = StatistikHandle.TourData[i].max_Temperatur;
                max_TemperaturStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            if (min_Temperatur > StatistikHandle.TourData[i].min_Temperatur) {
                min_Temperatur = StatistikHandle.TourData[i].min_Temperatur;
                min_TemperaturStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            av_Temperatur = av_Temperatur + StatistikHandle.TourData[i].av_Temperatur * StatistikHandle.TourData[i].gesammtZeit;

            if (av_Temperatur_max < StatistikHandle.TourData[i].av_Temperatur) {
                av_Temperatur_max = StatistikHandle.TourData[i].av_Temperatur;
                av_Temperatur_maxStr = "max: " + format.format(av_Temperatur_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Cadence < StatistikHandle.TourData[i].max_Cadence) {
                max_Cadence = StatistikHandle.TourData[i].max_Cadence;
                max_CadenceStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            av_Cadence = av_Cadence + StatistikHandle.TourData[i].av_Cadence * StatistikHandle.TourData[i].gesammtZeit;

            if (av_Cadence_max < StatistikHandle.TourData[i].av_Cadence) {
                av_Cadence_max = StatistikHandle.TourData[i].av_Cadence;
                av_Cadence_maxStr = "max: " + format.format(av_Cadence_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Schritt_länge < StatistikHandle.TourData[i].max_Schritt_länge) {
                max_Schritt_länge = StatistikHandle.TourData[i].max_Schritt_länge;
                max_Schritt_längeStr = StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }
            av_Schritt_länge = av_Schritt_länge + StatistikHandle.TourData[i].av_Schritt_länge * StatistikHandle.TourData[i].gesammtZeit;

            if (av_Schritt_länge_max < StatistikHandle.TourData[i].av_Schritt_länge) {
                av_Schritt_länge_max = StatistikHandle.TourData[i].av_Schritt_länge;
                av_Schritt_länge_maxStr = "max: " + format.format(av_Schritt_länge_max) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Kalorien < StatistikHandle.TourData[i].Kalorien) {
                max_Kalorien = StatistikHandle.TourData[i].Kalorien;
                max_KalorienStr = "max: " + format.format(max_Kalorien) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Kalorien_h < StatistikHandle.TourData[i].Kalorien_h) {
                max_Kalorien_h = StatistikHandle.TourData[i].Kalorien_h;
                max_Kalorien_hStr = "max: " + format.format(max_Kalorien_h) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Fett < StatistikHandle.TourData[i].Fett) {
                max_Fett = StatistikHandle.TourData[i].Fett;
                max_FettStr = "max: " + format.format(max_Fett) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Protein < StatistikHandle.TourData[i].Protein) {
                max_Protein = StatistikHandle.TourData[i].Protein;
                max_ProteinStr = "max: " + format.format(max_Protein) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Belastung < StatistikHandle.TourData[i].Belastung) {
                max_Belastung = StatistikHandle.TourData[i].Belastung;
                max_BelastungStr = "max: " + format.format(max_Belastung) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Erholungszeit < StatistikHandle.TourData[i].Erholungszeit) {
                max_Erholungszeit = (int)StatistikHandle.TourData[i].Erholungszeit;
                int erhl_tag = (int) (max_Erholungszeit / (3600.0 * 24));
                int erhl_std = (int) ((max_Erholungszeit - erhl_tag * 3600 * 24) / 3600.0);
                max_ErholungszeitStr = "max: " + erhl_tag + " T; " + erhl_std + " h    am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            if (max_Lauf_Index < StatistikHandle.TourData[i].Lauf_Index) {
                max_Lauf_Index = StatistikHandle.TourData[i].Lauf_Index;
                max_Lauf_IndexStr = "max: " + format.format(max_Lauf_Index) + " am: " + StatistikHandle.TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0).toString();
            }

            Zoomstrecke += StatistikHandle.TourData[i].Zoomstrecke;

        }
        av_Geschw = Zoomstrecke / gefahreneZeit * 3600;
        av_Hf = av_Hf / gesammtZeit;
        if (Strecke_Anstieg != 0) {
            av_Steigprozentpositiv = av_Steigprozentpositiv / Strecke_Anstieg;
        }
        if (Strecke_Gefälle != 0) {
            av_Steigprozentnegativ = av_Steigprozentnegativ / Strecke_Gefälle;
        }
        av_Steigmeterpositiv = av_Steigmeterpositiv / gesammtZeit;
        av_Steigmeternegativ = av_Steigmeternegativ / gesammtZeit;
        av_Temperatur = av_Temperatur / gesammtZeit;
        av_Cadence = av_Cadence / gesammtZeit;
        av_Schritt_länge = av_Schritt_länge / gesammtZeit;
        Fett = (int) (Fett / gesammtZeit);
        Protein = (int) (Protein / gesammtZeit);
        Kalorien_h = (int) (Kalorien_h / gesammtZeit);
 //       Belastung = (int) (Belastung / gesammtZeit);
 //       Erholungszeit = (int) (Erholungszeit / gesammtZeit);
        Lauf_Index = (int) (Lauf_Index / gesammtZeit);


    }
    
    
    private void InitArrays(){
        try{
            gesammtZeit = 0;
            gefahreneZeit= 0;
            ZahlStreckenPunkte = (int) (Streckenlänge*(float)Teiler);
            if (ZahlStreckenPunkte == 0) ZahlStreckenPunkte =1;
            Strecke_gesZeit = new float[Datenpunkte];
            
            gesZeit = new int[Datenpunkte];
            gefZeit = new int[Datenpunkte];
            ZeitüberStrecke = new int[ZahlStreckenPunkte];
            Streckenskala = new float[ZahlStreckenPunkte];

            Geschw_gesZeit = new float[Datenpunkte];
            av_Geschw_gesZeit = new float[Datenpunkte];
            av_Geschw_gefZeit = new float[Datenpunkte];
            RAWHF = new float[Datenpunkte];
            RAWGeschw = new float[Datenpunkte];
            RAWHm = new float[Datenpunkte];
            RAWCadence = new float[Datenpunkte];
            RAWSchrittlänge = new float[Datenpunkte];
            max_Geschw = 0;
            av_Geschw = 0;
            av_Geschw_max = 0;

            Schritt_länge = new int[Datenpunkte];
             max_Schritt_länge = 0;
   
            Hoehe_gesZeit = new float[Datenpunkte];
            max_Hoehe = 0;
            min_Hoehe = 0;
            ges_Hoehep = 0;
            ges_Hoehem = 0;
            
            Hf_gesZeit = new float[Datenpunkte];
            max_Hf = 0;
            av_Hf = 0;
            av_Hf_max = 0;
            
            Steigp_gesZeit = new float[Datenpunkte];
            max_Steigprozentpositiv = 0;
            max_Steigprozentnegativ = 0;
            av_Steigprozentpositiv = 0;
            av_Steigprozentnegativ = 0;
            av_Steigprozentpositiv_max = 0;
            av_Steigprozentnegativ_max = 0;
            
            Steigm_gesZeit = new float[Datenpunkte];
            max_Steigmeterpositiv = 0;
            max_Steigmeternegativ = 0;
            av_Steigmeterpositiv = 0;
            av_Steigmeternegativ = 0;
            av_Steigmeterpositiv_max = 0;
            av_Steigmeternegativ_max = 0;
            
            Temperatur_gesZeit = new float[Datenpunkte];
            max_Temperatur = 0;
            av_Temperatur = 0;
            av_Temperatur_max = 0;
            
            Cadence_gesZeit = new int[Datenpunkte];
            max_Cadence = 0;
            av_Cadence = 0;
            av_Schritt_länge = 0;
            av_Cadence_max = 0;
            av_Schritt_länge_max = 0;
            max_Belastung = 0;
            max_Erholungszeit = 0;
            max_Lauf_Index = 0;
            
            GeoDataArray = new ArrayList<String>();
            
        }catch(Exception e){JOptionPane.showMessageDialog(null,"InitArrays\nFehler: " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);}
        
    }
    
  
    public void createSubStatistik(JCicloTronic TronicHandle,double min,double max){
            
        int i = 0, j = 0, m = 0;
        int FileModified = 0;
        int ohneHf = 0, ohneCadence = 0;

        int ohneSteigmetpos = 0, ohneSteigmetneg = 0;
 
        int Höhenmeterschwelle;
 
        int[] Temp = {0, 0, 0, 0, 0, 0};
        float Hmtempp = 0, Hmtempm = 0;
        float DeltaHm = 0;
 
        int start = 0, stop = 0;

    

        Höhenmeterschwelle = Integer.parseInt(TronicHandle.Properties.getProperty("Höhenmeterakkumulierung", "1"));

        j = 0;
 //       JTourData[] TourData = new JTourData[TronicHandle.jTableaccess.getSelectedRowCount() + 1];

//        TourData = TronicHandle.Statistikhandle.TourData;  // ein bischen mit der Kugel durch den Rücken - aber funktioniert

        for (j = 1; j < TronicHandle.jTableaccess.getSelectedRowCount() + 1; j++) {
            
            ohneHf = 0; ohneCadence = 0;
        TronicHandle.Statistikhandle.TourData[j].Strecke_Anstieg = 0;
        TronicHandle.Statistikhandle.TourData[j].Strecke_Gefälle = 0;
        ohneSteigmetpos = 0; ohneSteigmetneg = 0;
        Hmtempp = 0; Hmtempm = 0;
        
            
            m = 0;
            TronicHandle.Statistikhandle.TourData[j].max_Geschw = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Geschw = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Geschw_max = 0;


            TronicHandle.Statistikhandle.TourData[j].ges_Hoehep = 0;
            TronicHandle.Statistikhandle.TourData[j].ges_Hoehem = 0;

            TronicHandle.Statistikhandle.TourData[j].max_Hf = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Hf = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Hf_max = 0;

            TronicHandle.Statistikhandle.TourData[j].max_Steigprozentpositiv = 0;
            TronicHandle.Statistikhandle.TourData[j].max_Steigprozentnegativ = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigprozentpositiv = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigprozentnegativ = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigprozentpositiv_max = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigprozentnegativ_max = 0;

            TronicHandle.Statistikhandle.TourData[j].max_Steigmeterpositiv = 0;
            TronicHandle.Statistikhandle.TourData[j].max_Steigmeternegativ = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigmeterpositiv = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigmeternegativ = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigmeterpositiv_max = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Steigmeternegativ_max = 0;

          
            TronicHandle.Statistikhandle.TourData[j].av_Temperatur = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Temperatur_max = 0;

            TronicHandle.Statistikhandle.TourData[j].max_Cadence = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Cadence = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Cadence_max = 0;
            
            TronicHandle.Statistikhandle.TourData[j].max_Schritt_länge = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge = 0;
            TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge_max = 0;
            

            TronicHandle.Statistikhandle.TourData[j].gefahreneZeit = 0;

            
            
            i = 0;

            int Datenp = TronicHandle.Statistikhandle.TourData[j].Datenpunkte;

            start = 0;
            stop = 0;

            if (TronicHandle.Graphik_Radio_Strecke.isSelected() == true) {
                while (TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] < min && i + 1 < Datenp) {
                    i++;
                }
                start = i;
                while (TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] < max && i + 1 < Datenp) {
                    i++;
                }

                stop = i;
            } else {

                while (TronicHandle.Statistikhandle.TourData[j].gesZeit[i] < min && i + 1 < Datenp) {
                    i++;
                }

                start = i;
                while (TronicHandle.Statistikhandle.TourData[j].gesZeit[i] < max && i + 1 < Datenp) {
                    i++;
                }

                stop = i;
            }

            TronicHandle.Statistikhandle.TourData[j].max_Hoehe = TronicHandle.Statistikhandle.TourData[j].Hoehe_gesZeit[start];
            TronicHandle.Statistikhandle.TourData[j].min_Hoehe = TronicHandle.Statistikhandle.TourData[j].Hoehe_gesZeit[start];
            if(start+1<Datenp) TronicHandle.Statistikhandle.TourData[j].max_Temperatur = TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[start+1];//+1 wegen Kompatibilität zu altem HAC Format (Temperatur war in Startdaten nicht gespeichert)
            if(start+1<Datenp) TronicHandle.Statistikhandle.TourData[j].min_Temperatur = TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[start+1];

//       
            

            for (i = start + 1; i < stop; i++) {
 

                if (TronicHandle.Statistikhandle.TourData[j].Geschw_gesZeit[i] != 0) {
                    m = m + 1;
                    TronicHandle.Statistikhandle.TourData[j].gefZeit[m] = TronicHandle.Statistikhandle.TourData[j].gefZeit[m - 1] + TronicHandle.Statistikhandle.TourData[j].gesZeit[i] - TronicHandle.Statistikhandle.TourData[j].gesZeit[i - 1];
                    TronicHandle.Statistikhandle.TourData[j].gefahreneZeit = TronicHandle.Statistikhandle.TourData[j].gefZeit[m]; //* (TourData[j].gesZeit[i]- TourData[j].gesZeit[i-1]);
                    TronicHandle.Statistikhandle.TourData[j].av_Geschw_gesZeit[i] = (TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] - TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[start]) / TronicHandle.Statistikhandle.TourData[j].gefZeit[m] * (float) 3600;

                } else {
                    TronicHandle.Statistikhandle.TourData[j].av_Geschw_gesZeit[i] = TronicHandle.Statistikhandle.TourData[j].av_Geschw_gesZeit[i - 1];
                }

                DeltaHm = TronicHandle.Statistikhandle.TourData[j].RAWHm[i] - TronicHandle.Statistikhandle.TourData[j].RAWHm[i - 1];


                if (DeltaHm > 0) {
                    Hmtempp += DeltaHm;
                    if (Hmtempm >= Höhenmeterschwelle) {
                        TronicHandle.Statistikhandle.TourData[j].ges_Hoehem += Hmtempm;
                    }
                    Hmtempm = 0;
                }
                if (DeltaHm < 0) {
                    Hmtempm += -DeltaHm;
                    if (Hmtempp >= Höhenmeterschwelle) {
                        TronicHandle.Statistikhandle.TourData[j].ges_Hoehep += Hmtempp;
                    }
                    Hmtempp = 0;
                }

                if (TronicHandle.Statistikhandle.TourData[j].Hf_gesZeit[i] == 0) {
                    ohneHf++;
                }
                TronicHandle.Statistikhandle.TourData[j].av_Hf += TronicHandle.Statistikhandle.TourData[j].Hf_gesZeit[i];

                if (TronicHandle.Statistikhandle.TourData[j].Cadence_gesZeit[i] == 0) {
                    ohneCadence++;
                }
                TronicHandle.Statistikhandle.TourData[j].av_Cadence += TronicHandle.Statistikhandle.TourData[j].Cadence_gesZeit[i];
                TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge += TronicHandle.Statistikhandle.TourData[j].Schritt_länge[i];

                if (TronicHandle.Statistikhandle.TourData[j].Steigp_gesZeit[i] > 0) {
                    TronicHandle.Statistikhandle.TourData[j].av_Steigprozentpositiv += DeltaHm;
                    TronicHandle.Statistikhandle.TourData[j].Strecke_Anstieg += TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] - TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i - 1];
                } else if (TronicHandle.Statistikhandle.TourData[j].Steigp_gesZeit[i] < 0) {
                    TronicHandle.Statistikhandle.TourData[j].av_Steigprozentnegativ += DeltaHm;
                    TronicHandle.Statistikhandle.TourData[j].Strecke_Gefälle += TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] - TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i - 1];
                }


                if (TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i] > 0) {
                    TronicHandle.Statistikhandle.TourData[j].av_Steigmeterpositiv = TronicHandle.Statistikhandle.TourData[j].av_Steigmeterpositiv + TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i];
                    ohneSteigmetneg++;
                } else if (TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i] < 0) {
                    TronicHandle.Statistikhandle.TourData[j].av_Steigmeternegativ = TronicHandle.Statistikhandle.TourData[j].av_Steigmeternegativ + -1 * TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i];
                    ohneSteigmetpos++;
                } else {
                    ohneSteigmetpos++;
                    ohneSteigmetneg++;
                }

                TronicHandle.Statistikhandle.TourData[j].av_Temperatur += TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[i];

//min max Werte ermitteln
                if (TronicHandle.Statistikhandle.TourData[j].Geschw_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Geschw) {
                    TronicHandle.Statistikhandle.TourData[j].max_Geschw = TronicHandle.Statistikhandle.TourData[j].Geschw_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].max_Geschw>=25){  // bei Fahrrad die Schrittlänge löschen
                    TronicHandle.Statistikhandle.TourData[j].Schritt_länge = new int[TronicHandle.Statistikhandle.TourData[j].Datenpunkte];
                    TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge=0;
                    TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge_max=0;
                    TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge_maxStr="";
                } 
                if (TronicHandle.Statistikhandle.TourData[j].Hoehe_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Hoehe) {
                    TronicHandle.Statistikhandle.TourData[j].max_Hoehe = TronicHandle.Statistikhandle.TourData[j].Hoehe_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Hoehe_gesZeit[i] < TronicHandle.Statistikhandle.TourData[j].min_Hoehe) {
                    TronicHandle.Statistikhandle.TourData[j].min_Hoehe = TronicHandle.Statistikhandle.TourData[j].Hoehe_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Temperatur) {
                    TronicHandle.Statistikhandle.TourData[j].max_Temperatur = TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[i] < TronicHandle.Statistikhandle.TourData[j].min_Temperatur) {
                    TronicHandle.Statistikhandle.TourData[j].min_Temperatur = TronicHandle.Statistikhandle.TourData[j].Temperatur_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Hf_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Hf) {
                    TronicHandle.Statistikhandle.TourData[j].max_Hf = (int)TronicHandle.Statistikhandle.TourData[j].Hf_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Cadence_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Cadence) {
                    TronicHandle.Statistikhandle.TourData[j].max_Cadence = TronicHandle.Statistikhandle.TourData[j].Cadence_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Schritt_länge[i] > TronicHandle.Statistikhandle.TourData[j].max_Schritt_länge) {
                    TronicHandle.Statistikhandle.TourData[j].max_Schritt_länge = TronicHandle.Statistikhandle.TourData[j].Schritt_länge[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Steigp_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Steigprozentpositiv) {
                    TronicHandle.Statistikhandle.TourData[j].max_Steigprozentpositiv = TronicHandle.Statistikhandle.TourData[j].Steigp_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Steigp_gesZeit[i] < -TronicHandle.Statistikhandle.TourData[j].max_Steigprozentnegativ) {
                    TronicHandle.Statistikhandle.TourData[j].max_Steigprozentnegativ = -TronicHandle.Statistikhandle.TourData[j].Steigp_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i] > TronicHandle.Statistikhandle.TourData[j].max_Steigmeterpositiv) {
                    TronicHandle.Statistikhandle.TourData[j].max_Steigmeterpositiv = TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i];
                }
                if (TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i] < -TronicHandle.Statistikhandle.TourData[j].max_Steigmeternegativ) {
                    TronicHandle.Statistikhandle.TourData[j].max_Steigmeternegativ = -TronicHandle.Statistikhandle.TourData[j].Steigm_gesZeit[i];
                }

  
            }

            if (Hmtempm >= Höhenmeterschwelle) {
                TronicHandle.Statistikhandle.TourData[j].ges_Hoehem += Hmtempm;
            }
            if (Hmtempp >= Höhenmeterschwelle) {
                TronicHandle.Statistikhandle.TourData[j].ges_Hoehep += Hmtempp;
            }
            if (TronicHandle.Statistikhandle.TourData[j].gefZeit[m] - TronicHandle.Statistikhandle.TourData[j].gefZeit[0] != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Geschw = (TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[stop] - TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[start])
                        / (TronicHandle.Statistikhandle.TourData[j].gefZeit[m] - TronicHandle.Statistikhandle.TourData[j].gefZeit[0]) * 3600;
            }

  
            TronicHandle.Statistikhandle.TourData[j].gesammtZeit = TronicHandle.Statistikhandle.TourData[j].gesZeit[stop] - TronicHandle.Statistikhandle.TourData[j].gesZeit[start];
            if (stop - start - ohneHf != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Hf = TronicHandle.Statistikhandle.TourData[j].av_Hf / (stop - start - ohneHf);
            }
            if (stop - start != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Temperatur = TronicHandle.Statistikhandle.TourData[j].av_Temperatur / (stop - start);
            }
            if (stop - start - ohneCadence != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Cadence = TronicHandle.Statistikhandle.TourData[j].av_Cadence / (stop - start - ohneCadence);
                TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge = TronicHandle.Statistikhandle.TourData[j].av_Schritt_länge / (stop - start - ohneCadence);
            }
            if (TronicHandle.Statistikhandle.TourData[j].Strecke_Anstieg != 0) {
                
                TronicHandle.Statistikhandle.TourData[j].av_Steigprozentpositiv = TronicHandle.Statistikhandle.TourData[j].av_Steigprozentpositiv / TronicHandle.Statistikhandle.TourData[j].Strecke_Anstieg/10;
            }
            if (TronicHandle.Statistikhandle.TourData[j].Strecke_Gefälle != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Steigprozentnegativ = - TronicHandle.Statistikhandle.TourData[j].av_Steigprozentnegativ /TronicHandle.Statistikhandle.TourData[j].Strecke_Gefälle/10;
            }
            if (stop - start - ohneSteigmetpos != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Steigmeterpositiv = TronicHandle.Statistikhandle.TourData[j].av_Steigmeterpositiv / (stop - start - ohneSteigmetpos);
            }
            if (stop - start - ohneSteigmetneg != 0) {
                TronicHandle.Statistikhandle.TourData[j].av_Steigmeternegativ = TronicHandle.Statistikhandle.TourData[j].av_Steigmeternegativ / (stop - start - ohneSteigmetneg);
            }
            //     TourData[j].gesammtZeit = TourData[j].gesZeit[i-1];
            TronicHandle.Statistikhandle.TourData[j].Zoomstrecke = TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[stop] - TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[start];
            
            Überalles(TronicHandle.Statistikhandle, TronicHandle.jTableaccess.getSelectedRows(),TronicHandle.jTableaccess.getSelectedRowCount());
            
        }

    }
    
    private void ReadGeoData() {

        //  ArrayList List = new ArrayList();
        StringBuilder GeoBuffer = new StringBuilder();
        String entryName = "";
        String kmlName = "";
        File Datei;
   

        GeoDataArray = new ArrayList<String>();
      
   

            File f = new File(gpx_File);

            String ext = f.getName();
            if (!ext.contains(".")) {
                ext = "";
            } else {
                ext = ext.substring(ext.lastIndexOf("."), ext.length());
            }

            if (ext.endsWith("kmz")) {

                try {

                    byte[] buf = new byte[1024];
                    ZipInputStream zipinputstream = null;
                    ZipEntry zipentry;
                    zipinputstream = new ZipInputStream(
                            new FileInputStream(gpx_File));

                    zipentry = zipinputstream.getNextEntry();
                    while (zipentry != null) {
                        //for each entry to be extracted
                        entryName = zipentry.getName();
                        int k = 0;
                        if (entryName.indexOf(".kml") != -1) {
                            //               System.out.println("entryname "+entryName);
                            int n;

                            FileOutputStream fileoutputstream;
                            File newFile = new File(entryName);

                            String directory = newFile.getParent();

                            if (directory == null) {
                                if (newFile.isDirectory()) {
                                    break;
                                }
                            }

                            fileoutputstream = new FileOutputStream(entryName);

                            while ((n = zipinputstream.read(buf, 0, 1024)) > -1) //                   GeoBuffer.append(buf.toString());
                            {
                                fileoutputstream.write(buf, 0, n);
                            }
                            kmlName = entryName;
                            fileoutputstream.close();
                            zipinputstream.closeEntry();

                        };
                        zipentry = zipinputstream.getNextEntry();
                    }//while

                    zipinputstream.close();
                } catch (Exception e) {
                 //   continue;
                }
                try {
                    FileInputStream stream = new FileInputStream(kmlName);
                    Datei = new File(kmlName);
                    byte[] Data = new byte[(int) Datei.length()];
                    stream.read(Data);
                    stream.close();

                    Datei = new File(kmlName);

                    if (Datei.exists()) {
                        Datei.delete();
                    }

                    GeoBuffer = new StringBuilder();
                    for (int i = 0; i < Data.length; i++) {
                        GeoBuffer.append((char) Data[i]);
                    }

                } catch (Exception e) {
                };

            } // Ende kmz
            else if (ext.endsWith("tcx") || ext.endsWith("gpx")) {

                try {
  
                    BufferedInputStream stream = new BufferedInputStream(new FileInputStream(gpx_File));
                    Datei = new File(gpx_File);
                    byte[] Data = new byte[(int) Datei.length()];
  
    
                    stream.read(Data);
                    stream.close();

                    GeoBuffer = new StringBuilder();
                    for (int i = 0; i < Data.length; i++) {
                        GeoBuffer.append((char) Data[i]);
                    }
                    ext = ext;
                } catch (Exception e) {
                };

            };

    
            double geotemp = 0;
            int temp = 0;
            int temp1 = 0;
            int Linestringcount = 0;
            int subindex = 0;
            int subindex_2 = 0;
            int Eof_coordinates = 0;
            String string_a = "";
            String string_b = "";
            char dummy = 0;
            int dummy_2 = 0;


            String tempstring;
            tempstring = GeoBuffer.toString();
            double temp_streckenlänge = 0;
            int tempindex = 0;

            if (ext.endsWith("kmz")) {   //start kmz Auswertung

                GeoBuffer = new StringBuilder(tempstring.replace('\n', ' '));

                do {
                    subindex = GeoBuffer.indexOf("ns2:");
                    subindex_2 = GeoBuffer.length();
                    subindex = subindex + 1 - 1;
                    if (subindex != -1) {
                        GeoBuffer.delete(subindex, subindex + 4);
                    }
                } while (subindex != -1);

                do {
                    Linestringcount++;
                    subindex = GeoBuffer.indexOf("<LineString>", subindex + 1);
                } while (subindex != -1);

                for (int i = 0; i < Linestringcount - 1; i++) {

                    subindex = GeoBuffer.indexOf("<LineString>");
                    if (subindex == -1) {
                        break;
                    }
                    GeoBuffer.delete(0, subindex + 1);
 
                    subindex = GeoBuffer.indexOf("<coordinates>");

                    Eof_coordinates = GeoBuffer.indexOf("</coordinates>", subindex);

                    subindex--;
                    do {
                        subindex++;
                        dummy = GeoBuffer.charAt(subindex);
                        dummy_2 = (int) dummy;
                        if (subindex >= Eof_coordinates) {
                            break;
                        }
                    } while (((int) GeoBuffer.charAt(subindex) < 48 || (int) GeoBuffer.charAt(subindex) > 57) && (int) GeoBuffer.charAt(subindex) != 45);
                    if (subindex >= Eof_coordinates) {
                        break;
                    }
                    subindex_2 = subindex--;
   
                    do {
                        subindex = GeoBuffer.indexOf(",", subindex_2);
                        if (subindex != -1) {
                            string_a = GeoBuffer.substring(subindex_2, subindex);
                            geotemp = Double.parseDouble(string_a);

                            if (temp1 == 0) {
                                map_y_max = geotemp;
                                map_y_min = geotemp;
                                temp1 = 1;
                            } else {
                                if (geotemp > map_y_max) {
                                    map_y_max = geotemp;
                                }
                                if (geotemp < map_y_min) {
                                    map_y_min = geotemp;
                                }
                            };
                            GeoDataArray.add(string_a);
                           
                            subindex_2 = subindex + 1;
                        };

                        subindex = GeoBuffer.indexOf(",", subindex_2);
                        if (subindex != -1) {
                            string_a = GeoBuffer.substring(subindex_2, subindex);
                            geotemp = Double.parseDouble(string_a);

                            if (temp == 0) {
                                map_x_max = geotemp;
                                map_x_min = geotemp;
                                temp = 1;
                            } else {
                                if (geotemp > map_x_max) {
                                    map_x_max = geotemp;
                                }
                                if (geotemp < map_x_min) {
                                    map_x_min = geotemp;
                                }
                            };
                            GeoDataArray.add(string_a);
                           
                            subindex_2 = subindex + 1;
                        };

                        subindex = GeoBuffer.indexOf(" ", subindex_2);
                        if (subindex != -1)  {
                         
                            subindex_2 = subindex + 1;
                       
                        }
                        tempindex = GeoDataArray.size();
                      
                        if (tempindex > 3) {

                            temp_streckenlänge += TronicHandle.distFrom(Double.parseDouble(GeoDataArray.get(tempindex - 3)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 4)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 1)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 2)));
                        }

                    } while (subindex != -1 && subindex + "</coordinates>".length() < Eof_coordinates);
                    map_Streckenlänge = (float) temp_streckenlänge;
                }
            }

            if (ext.endsWith("tcx")) {
                if (tempstring.indexOf("<Position", subindex) != -1) {

                    do {

                        subindex = tempstring.indexOf("<Position", subindex);
                        subindex_2 = tempstring.indexOf("<LatitudeDegrees", subindex);

                        string_a = tempstring.substring(
                                tempstring.indexOf(">", subindex_2) + 1,
                                tempstring.indexOf("<", subindex_2 + 1));

                        geotemp = Double.parseDouble(string_a);

                        if (temp == 0) {
                            map_x_max = geotemp;
                            map_x_min = geotemp;
                            temp = 1;
                        } else {
                            if (geotemp > map_x_max) {
                                map_x_max = geotemp;
                            }
                            if (geotemp < map_x_min) {
                                map_x_min = geotemp;
                            }
                        };

                        subindex_2 = tempstring.indexOf("<LongitudeDegrees", subindex);

                        string_b = tempstring.substring(
                                tempstring.indexOf(">", subindex_2) + 1,
                                tempstring.indexOf("<", subindex_2 + 1));

                        geotemp = Double.parseDouble(string_b);

                        GeoDataArray.add(string_b);
                        GeoDataArray.add(string_a);

                        if (temp1 == 0) {
                            map_y_max = geotemp;
                            map_y_min = geotemp;
                            temp1 = 1;
                        } else {
                            if (geotemp > map_y_max) {
                                map_y_max = geotemp;
                            }
                            if (geotemp < map_y_min) {
                                map_y_min = geotemp;
                            }
                        }
                        subindex++;
                        tempindex = GeoDataArray.size();
                        if (tempindex > 3) {

                            temp_streckenlänge += TronicHandle.distFrom(Double.parseDouble(GeoDataArray.get(tempindex - 3)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 4)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 1)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 2)));
                        }

                    } while (tempstring.indexOf("<Position", subindex) != -1);
                }
                map_Streckenlänge = (float) temp_streckenlänge;
            }

            if (ext.endsWith("gpx")) {

                if (tempstring.indexOf("<trkpt ", subindex) != -1) {

                    do {

                        subindex = tempstring.indexOf("<trkpt ", subindex);
                        subindex_2 = tempstring.indexOf("lon", subindex);

                        string_a = tempstring.substring(
                                tempstring.indexOf("\"", subindex_2) + 1,
                                tempstring.indexOf("\"", subindex_2 + 5));

                        geotemp = Double.parseDouble(string_a);

                        if (temp == 0) {
                            map_y_max = geotemp;
                            map_y_min = geotemp;
                            temp = 1;
                        } else {
                            if (geotemp > map_y_max) {
                                map_y_max = geotemp;
                            }
                            if (geotemp < map_y_min) {
                                map_y_min = geotemp;
                            }
                        };

                        subindex_2 = tempstring.indexOf("lat", subindex);

                        string_b = tempstring.substring(
                                tempstring.indexOf("\"", subindex_2) + 1,
                                tempstring.indexOf("\"", subindex_2 + 5));

                        geotemp = Double.parseDouble(string_b);

                        GeoDataArray.add(string_a);
                        GeoDataArray.add(string_b);

                        if (temp1 == 0) {
                            map_x_max = geotemp;
                            map_x_min = geotemp;
                            temp1 = 1;
                        } else {
                            if (geotemp > map_x_max) {
                                map_x_max = geotemp;
                            }
                            if (geotemp < map_x_min) {
                                map_x_min = geotemp;
                            }
                        }
                        subindex++;
                        tempindex = GeoDataArray.size();
                        if (tempindex > 3) {

                            temp_streckenlänge += TronicHandle.distFrom(Double.parseDouble(GeoDataArray.get(tempindex - 3)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 4)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 1)),
                                    Double.parseDouble(GeoDataArray.get(tempindex - 2)));
                        }
                    } while (tempstring.indexOf("<trkpt ", subindex) != -1);
                }

            }
            map_Streckenlänge = (float) (Math.round(temp_streckenlänge * 100) / 100.0);

 

    }
    
//    private byte Data[];
//    private String Dummy;
    private int readData;
    private float Teiler = 1;
    
 
    public String Datum;
    public int Monat;
    public int Tag;
    public int Jahr;
    public int StartStunden;
    public int StartMinuten;
    public String Uhrzeit;
    public float Streckenlänge;
    public float map_Streckenlänge;
    public float Zoomstrecke;
    public float gesammtZeit;
    public float gefahreneZeit;
    public String Notiz;
    public int Datenpunkte;
    public int ZahlStreckenPunkte;

    public float RAWGeschw[];
    public float RAWHF[];
    public float RAWHm[];
    public float RAWCadence[];
    public float RAWSchrittlänge[];

    public float Strecke_gesZeit[];

    public float Strecke_Anstieg;
    public float Strecke_Gefälle;
    
    public int gesZeit[];
    public int gefZeit[];
    
    public int ZeitüberStrecke[];
    public float Streckenskala[];
    
    public float Geschw_gesZeit[];
    public float av_Geschw_gesZeit[];
    public float av_Geschw_gefZeit[];
    public float max_Geschw;
    public String max_GeschwStr = null;
    public float av_Geschw;
    public float av_Geschw_max;
    public String av_Geschw_maxStr;
 
    public float Hoehe_gesZeit[];
    public float max_Hoehe;
    public String max_HoeheStr = null;
    public float min_Hoehe;
    public String min_HoeheStr = null;
    public float ges_Hoehep;
    public float ges_Hoehem;
    
    public int Schritt_länge[];
    public int max_Schritt_länge;
    public float av_Schritt_länge;
    public float av_Schritt_länge_max;
    public String max_Schritt_längeStr = null;
    public String av_Schritt_länge_maxStr;
    
    public float Hf_gesZeit[];
    public int max_Hf;
    public String max_HfStr = null;
    public float av_Hf;
    public float av_Hf_max;
    public String av_Hf_maxStr;
    
    public float Steigp_gesZeit[];
    public float max_Steigprozentpositiv;
    public String max_SteigprozentpositivStr = null;
    public float max_Steigprozentnegativ;
    public String max_SteigprozentnegativStr = null;
    public float av_Steigprozentpositiv;
    public float av_Steigprozentnegativ;
    public float av_Steigprozentpositiv_max;
    public float av_Steigprozentnegativ_max;
    public String av_Steigprozentpositiv_maxStr;
    public String av_Steigprozentnegativ_maxStr;
    
    public float Steigm_gesZeit[];
    public float max_Steigmeterpositiv;
    public String max_SteigmeterpositivStr = null;
    public float max_Steigmeternegativ;
    public String max_SteigmeternegativStr = null;
    public float av_Steigmeterpositiv;
    public float av_Steigmeternegativ;
    public float av_Steigmeterpositiv_max;
    public float av_Steigmeternegativ_max;
    public String av_Steigmeterpositiv_maxStr;
    public String av_Steigmeternegativ_maxStr;
    
    public float Temperatur_gesZeit[];
    public float max_Temperatur;
    public String max_TemperaturStr = null;
    public float min_Temperatur;
    public String min_TemperaturStr = null;
    public float av_Temperatur;
    public float av_Temperatur_max;
    public String av_Temperatur_maxStr;
    
    public int Cadence_gesZeit[];
    public int max_Cadence;
    public String max_CadenceStr = null;
    public float av_Cadence;
    public float av_Cadence_max;
    public String av_Cadence_maxStr;
    
    public int AnzahlMarken;
    public String Marken[];
    public String Konfigfile;
    
    public int Kalorien;
    public int max_Kalorien;
    public String max_KalorienStr;
    public float Kalorien_h;
    public float max_Kalorien_h;
    public String max_Kalorien_hStr;
    public int Fett;
    public float av_Fett;
    public int max_Fett;
    public String max_FettStr;
    public int Protein;
    public float av_Protein;
    public int max_Protein;
    public String max_ProteinStr;
    public int Belastung;
    public int max_Belastung;
    public String max_BelastungStr;
    public long Erholungszeit;
    public int max_Erholungszeit;
    public String max_ErholungszeitStr;
    public int Lauf_Index;
    public int max_Lauf_Index;
    public String max_Lauf_IndexStr;
    
    public ArrayList<String> GeoDataArray;
    private String gpx_File;
    
    public double map_y_max;
    public double map_y_min;
    public double map_x_max;
    public double map_x_min;
    
    private JStatistik StatistikHandle;
    private java.text.DecimalFormat format;
    private JCicloTronic TronicHandle;
    
    public java.util.Properties   DataProperty;
    public String Dateiname;
    
    public int zähler;
}