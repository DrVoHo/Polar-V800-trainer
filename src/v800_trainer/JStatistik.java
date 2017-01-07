package v800_trainer;

 /*
 * Statistik.java
 
 *
 * SourceFile is part of Chainwheel

 * Calculates all Statistic values (individual and over all) 
 */



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

 
 */

import javax.swing.JOptionPane;
import java.io.File;
import java.awt.Cursor;



public class JStatistik extends Object {

    /**
     * Creates new Statistik
     */
    public JStatistik() {
    }

    ;
    public void StatistikStart(JCicloTronic dummy) {
        TronicHandle = dummy;
        int i = 0;
        float Gesammtkilometer = 0;
        String Dummy = "";
        format = new java.text.DecimalFormat("###0.00");
        DataProperty = new java.util.Properties();
        String Filename = "";
        File path = new File(TronicHandle.Properties.getProperty("data.dir"));
        Selection = new int[TronicHandle.jTableaccess.getSelectedRowCount()];
        Selection = TronicHandle.jTableaccess.getSelectedRows();
        TronicHandle.Update = false;
        TronicHandle.Auswahl_Statistik.removeAllItems();
        TronicHandle.Auswahl_Graphik.removeAllItems();
        TronicHandle.Auswahl_Histogramm.removeAllItems();
        TronicHandle.Auswahl_Info.removeAllItems();
        TronicHandle.Auswahl_Map.removeAllItems();

        TronicHandle.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        TourData = null;
        if (TronicHandle.jTableaccess.getSelectedRowCount() == 0) {
            TronicHandle.Auswahl_Statistik.addItem("keine Datei selektiert");
            TronicHandle.Auswahl_Graphik.addItem("keine Datei selektiert");
            TronicHandle.Auswahl_Histogramm.addItem("keine Datei selektiert");
            TronicHandle.Auswahl_Info.addItem("keine Datei selektiert");
            TronicHandle.Auswahl_Map.addItem("keine Datei selektiert");

            TronicHandle.Update = true;
            TronicHandle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            return;
        }

        TourData = new JTourData[TronicHandle.jTableaccess.getSelectedRowCount() + 1];

        try {
            for (i = 1; i < TronicHandle.jTableaccess.getSelectedRowCount() + 1; i++) {

                Filename = (String) TronicHandle.sorter.getValueAt(Selection[i - 1], 5);
                TourData[i] = new JTourData(Filename, TronicHandle);

            }
            TourData[0] = new JTourData(null, TronicHandle); //funktionen werden greifbar
            TourData[0].Überalles(this, Selection, i - 1);
            TronicHandle.Auswahl_Statistik.addItem("Strecke  " + format.format(TourData[0].Streckenlänge) + " km");
            TronicHandle.Auswahl_Graphik.addItem("Strecke  " + format.format(TourData[0].Streckenlänge) + " km");
            TronicHandle.Auswahl_Histogramm.addItem("Strecke  " + format.format(TourData[0].Streckenlänge) + " km");
            TronicHandle.Auswahl_Info.addItem("Strecke  " + format.format(TourData[0].Streckenlänge) + " km");
            TronicHandle.Auswahl_Map.addItem("Strecke  " + format.format(TourData[0].Streckenlänge) + " km");

            for (i = 1; i < TronicHandle.jTableaccess.getSelectedRowCount() + 1; i++) {
                Dummy = TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 0)
                        + "   " + TronicHandle.jTableaccess.getValueAt(Selection[i - 1], 1) + " km";
                while (!TronicHandle.CheckComboEntry(TronicHandle.Auswahl_Statistik, Dummy)) {
                    Dummy += " ";
                }
                TronicHandle.Auswahl_Statistik.addItem(Dummy);
                TronicHandle.Auswahl_Graphik.addItem(Dummy);
                TronicHandle.Auswahl_Histogramm.addItem(Dummy);
                TronicHandle.Auswahl_Info.addItem(Dummy);
                TronicHandle.Auswahl_Map.addItem(Dummy);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "StatistikStart\nFehler: " + e + "\n" + e.getStackTrace(), "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        TronicHandle.SelectionChanged = false;
        TronicHandle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        if (TronicHandle.jTableaccess.getSelectedRowCount() == 1) {
            TronicHandle.Auswahl_Statistik.setSelectedIndex(1);
            TronicHandle.Auswahl_Graphik.setSelectedIndex(1);
            TronicHandle.Auswahl_Histogramm.setSelectedIndex(1);
            TronicHandle.Auswahl_Info.setSelectedIndex(1);
            TronicHandle.Auswahl_Map.setSelectedIndex(1);

        } else {
            TronicHandle.Auswahl_Statistik.setSelectedIndex(0);
            TronicHandle.Auswahl_Graphik.setSelectedIndex(0);
            TronicHandle.Auswahl_Histogramm.setSelectedIndex(0);
            TronicHandle.Auswahl_Info.setSelectedIndex(0);
            TronicHandle.Auswahl_Map.setSelectedIndex(0);

        }
        TronicHandle.Update = true;

    }

    private java.text.DecimalFormat format;

    public JCicloTronic TronicHandle;
    public int Selection[];
    private java.util.Properties DataProperty;
    public JTourData TourData[];
    
 
}