package v800_trainer;

 /*
 * JHistogram.java
 
 *
 * SourceFile is part of Chainwheel

 * Graphik for Histogram Panels
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

import java.awt.*;
import javax.swing.*;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.data.statistics.HistogramType;

import javax.swing.JOptionPane;

import java.util.ArrayList;

public class JHistogram extends java.lang.Object {

    /**
     * Creates new JHistogram
     */
    public JHistogram(JCicloTronic JTronicHandle) {
        padding = new RectangleInsets(0,30*(int)JTronicHandle.FontSize/12,
                5*(int)JTronicHandle.FontSize/12,0);
    }

    public JPanel StartHistoHf(JCicloTronic JTronicHandle) {

        boolean Summenhisto;
        int von = 0;
        int bis = 0;
        int num = 0;
        int selected;
        int single;
        int Gruppen = 0;
        int i = 100;
        int j = 10;
        int m = 0;
        int n = 0;
        int Linecount = 0;
        int Anzahl = 1;

        JFreeChart chart;

        double DummyData[] = new double[1];

        selected = JTronicHandle.Auswahl_Histogramm.getSelectedIndex();
        Summenhisto = JTronicHandle.Summenhistogramm_Check.isSelected();

        chart = ChartFactory.createHistogram("", "Herzfrequenz [p/min]", "Häufigkeit", new HistogramDataset(), PlotOrientation.HORIZONTAL, true, true, true);

        chart.setBackgroundPaint(Color.white);

        JTronicHandle.applyChartTheme(chart);

        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getRangeAxis().setFixedDimension(15.0);

        ArrayList Bufferarray = new ArrayList();
        double Buffer;

        if (selected == 0) {
            single = 1;
        } else {
            single = 0;
            Summenhisto = false;
        }  //einzelne Tour oder alle Touren

        von = Integer.parseInt(JTronicHandle.Properties.getProperty("HistovonHf", "100"));
        bis = Integer.parseInt(JTronicHandle.Properties.getProperty("HistobisHf", "200"));
        Gruppen = Integer.parseInt(JTronicHandle.Properties.getProperty("HistostepHf", "10"));
        Anzahl = (JTronicHandle.Auswahl_Histogramm.getItemCount() - 2) * single + 1;  //komische Mathe;
        //wenn nur eine Tour ausgewählt ist single = 0  und damit Anzahl =1
        // sonst single gleich 1 und die Anzahl ist ItemCont - 1
        for (j = 0; j < Anzahl; j++) {
            if (single == 1) {
                selected = j + 1; // single = 1 => Summe ist ausgewählt und selected muss ein höher gesetz werden
            }
            if (!Summenhisto) {
                num = JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
            } else {
                num += JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
            }

            n = 0;

            if (!Summenhisto) {
                create_Start_Stop(JTronicHandle, selected);  //die limits einer gezoomten Graphik ermitteln
                for (i = start; i < stop; i++) {         //hier wird nun über alle Daten gezählt
                    Buffer = (double) JTronicHandle.Statistikhandle.TourData[selected].Hf_gesZeit[i]; //aktueller Wert zwischengespeichert
                    if (Buffer >= von && Buffer <= bis) {
                        Bufferarray.add(Buffer); //wenn im ges. Intervall dann in das Bufferarray
                    }
                }
            } else {     //Summenhist ist ausgewählt
                for (i = 0; i < j + 1; i++) {  //schlechte Schleifensteuerung i=1;i<j hätte es auch getan

                    create_Start_Stop(JTronicHandle, i + 1);
                    for (m = start; m < stop; m++) { // Schleife über alle Datenpunkte
                        Buffer = (double) JTronicHandle.Statistikhandle.TourData[i + 1].Hf_gesZeit[m];
                        if (Buffer >= von && Buffer <= bis) {
                            Bufferarray.add(Buffer);
                        }
                    }

                }
            }

            DummyData = new double[Bufferarray.size()];

            for (i = 0; i < Bufferarray.size(); i++) {
                DummyData[i] = new Double(Bufferarray.get(i).toString());
            }

            if (!Summenhisto) {
                HistogramDataset histoHF = new HistogramDataset();
                histoHF.addSeries(""
                        + JTronicHandle.Statistikhandle.TourData[selected].Tag + "."
                        + JTronicHandle.Statistikhandle.TourData[selected].Monat + "."
                        + JTronicHandle.Statistikhandle.TourData[selected].Jahr,
                        DummyData, Gruppen, (double) von, (double) bis);
                histoHF.setType(HistogramType.RELATIVE_FREQUENCY);
                plot.setDataset(Linecount, histoHF);
                plot.mapDatasetToRangeAxis(Linecount, 0);
                XYBarRenderer renderer = new XYBarRenderer();
                renderer.setDrawBarOutline(true);
                //                renderer.setShadowVisible(false);

                renderer.setSeriesPaint(0, getColour(Linecount, (int) 255 / Anzahl));

                renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                plot.setRenderer(Linecount, renderer);

            }
            Linecount++;
        }

        if (Summenhisto) {
            HistogramDataset histoHF = new HistogramDataset();
            histoHF.addSeries("Summenhistogram", DummyData, Gruppen, (double) von, (double) bis);
            histoHF.setType(HistogramType.RELATIVE_FREQUENCY);

            plot.setDataset(0, histoHF);
            plot.mapDatasetToRangeAxis(0, 0);
            XYItemRenderer renderer = new XYBarRenderer();
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

            plot.setRenderer(0, renderer);
        }

        chart.setPadding(padding);
        ChartPanel Panel = new ChartPanel(chart);

        return Panel;
    }

    public JPanel StartHistoSp(JCicloTronic JTronicHandle) {

        boolean Summenhisto;
        int von = 0;
        int bis = 0;
        int num = 0;
        int selected;
        int single;
        int Gruppen = 0;
        int i = 100;
        int j = 10;
        int m = 0;
        int n = 0;
        int Linecount = 0;
        int Anzahl = 1;

        JFreeChart chart;

        double DummyData[] = new double[1];

        selected = JTronicHandle.Auswahl_Histogramm.getSelectedIndex();
        Summenhisto = JTronicHandle.Summenhistogramm_Check.isSelected();

        chart = ChartFactory.createHistogram("", "Geschwindigkeit [km/h]", "Häufigkeit", new HistogramDataset(), PlotOrientation.HORIZONTAL, true, true, true);

        chart.setBackgroundPaint(Color.white);

        JTronicHandle.applyChartTheme(chart);

        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getRangeAxis().setFixedDimension(15.0);

        ArrayList Bufferarray = new ArrayList();
        double Buffer;

        if (selected == 0) {
            single = 1;
        } else {
            single = 0;
            Summenhisto = false;
        }

        try {
            von = Integer.parseInt(JTronicHandle.Properties.getProperty("HistovonSp", "100"));
            bis = Integer.parseInt(JTronicHandle.Properties.getProperty("HistobisSp", "200"));
            Gruppen = Integer.parseInt(JTronicHandle.Properties.getProperty("HistostepSp", "10"));
            Anzahl = (JTronicHandle.Auswahl_Histogramm.getItemCount() - 2) * single + 1;
            for (j = 0; j < Anzahl; j++) {
                if (single == 1) {
                    selected = j + 1;
                }
                if (!Summenhisto) {
                    num = JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                } else {
                    num += JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                }

                n = 0;
                if (!Summenhisto) {
                    create_Start_Stop(JTronicHandle, selected);  //die limits einer gezoomten Graphik ermitteln

                    for (i = start; i < stop; i++) {
                        Buffer = (double) JTronicHandle.Statistikhandle.TourData[selected].Geschw_gesZeit[i];
                        if (Buffer >= von && Buffer <= bis) {
                            Bufferarray.add(Buffer);
                        }
                    }
                } else {
                    for (i = 0; i < j + 1; i++) {
                        create_Start_Stop(JTronicHandle, i + 1);
                        for (m = start; m < stop; m++) {
                            Buffer = (double) JTronicHandle.Statistikhandle.TourData[i + 1].Geschw_gesZeit[m];
                            if (Buffer >= von && Buffer <= bis) {
                                Bufferarray.add(Buffer);
                            }

                        }
                    }
                }

                DummyData = new double[Bufferarray.size()];

                for (i = 0; i < Bufferarray.size(); i++) {
                    DummyData[i] = new Double(Bufferarray.get(i).toString());
                }

                if (!Summenhisto) {
                    HistogramDataset histoHM = new HistogramDataset();
                    histoHM.addSeries(""
                            + JTronicHandle.Statistikhandle.TourData[selected].Tag + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Monat + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Jahr,
                            DummyData, Gruppen, (double) von, (double) bis);
                    histoHM.setType(HistogramType.RELATIVE_FREQUENCY);
                    plot.setDataset(Linecount, histoHM);
                    plot.mapDatasetToRangeAxis(Linecount, 0);
                    XYBarRenderer renderer = new XYBarRenderer();
                    renderer.setDrawBarOutline(true);

                    renderer.setSeriesPaint(0, getColour(Linecount, (int) 255 / Anzahl));

                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    plot.setRenderer(Linecount, renderer);

                }
                Linecount++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "StartHistoSp\nFehler: Sp " + e + " " + i + " " + j, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        if (Summenhisto) {
            HistogramDataset histoHM = new HistogramDataset();
            histoHM.addSeries("Summenhistogram", DummyData, Gruppen, (double) von, (double) bis);
            histoHM.setType(HistogramType.RELATIVE_FREQUENCY);

            plot.setDataset(0, histoHM);
            plot.mapDatasetToRangeAxis(0, 0);
            XYItemRenderer renderer = new XYBarRenderer();
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

            plot.setRenderer(0, renderer);
        }
        chart.setPadding(padding);
        ChartPanel Panel = new ChartPanel(chart);

        return Panel;
    }

    public JPanel StartHistoHm(JCicloTronic JTronicHandle) {

        boolean Summenhisto;
        int von = 0;
        int bis = 0;
        int num = 0;
        int selected;
        int single;
        int Gruppen = 0;
        int i = 100;
        int j = 10;
        int m = 0;
        int n = 0;
        int Linecount = 0;
        int Anzahl = 1;

        JFreeChart chart;

        double DummyData[] = new double[1];

        selected = JTronicHandle.Auswahl_Histogramm.getSelectedIndex();
        Summenhisto = JTronicHandle.Summenhistogramm_Check.isSelected();

        chart = ChartFactory.createHistogram("", "Steigung [m/min]", "Häufigkeit", new HistogramDataset(), PlotOrientation.HORIZONTAL, true, true, true);

        chart.setBackgroundPaint(Color.white);

        JTronicHandle.applyChartTheme(chart);

        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getRangeAxis().setFixedDimension(15.0);

        ArrayList Bufferarray = new ArrayList();
        double Buffer;

        if (selected == 0) {
            single = 1;
        } else {
            single = 0;
            Summenhisto = false;
        };

        try {
            von = Integer.parseInt(JTronicHandle.Properties.getProperty("HistovonHm", "100"));
            bis = Integer.parseInt(JTronicHandle.Properties.getProperty("HistobisHm", "200"));
            Gruppen = Integer.parseInt(JTronicHandle.Properties.getProperty("HistostepHm", "10"));
            Anzahl = (JTronicHandle.Auswahl_Histogramm.getItemCount() - 2) * single + 1;
            for (j = 0; j < Anzahl; j++) {
                if (single == 1) {
                    selected = j + 1;
                }
                if (!Summenhisto) {
                    num = JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                } else {
                    num += JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                }

                n = 0;
                if (!Summenhisto) {
                    create_Start_Stop(JTronicHandle, selected);  //die limits einer gezoomten Graphik ermitteln

                    for (i = start; i < stop; i++) {
                        Buffer = (double) JTronicHandle.Statistikhandle.TourData[selected].Steigm_gesZeit[i];
                        if (Buffer >= von && Buffer <= bis) {
                            Bufferarray.add(Buffer);
                        }
                    }
                } else {
                    for (i = 0; i < j + 1; i++) {
                        create_Start_Stop(JTronicHandle, i + 1);
                        for (m = start; m < stop; m++) {
                            Buffer = (double) JTronicHandle.Statistikhandle.TourData[i + 1].Steigm_gesZeit[m];
                            if (Buffer >= von && Buffer <= bis) {
                                Bufferarray.add(Buffer);
                            }

                        }

                    }
                }

                DummyData = new double[Bufferarray.size()];

                for (i = 0; i < Bufferarray.size(); i++) {
                    DummyData[i] = new Double(Bufferarray.get(i).toString());
                }

                if (!Summenhisto) {
                    HistogramDataset histoHM = new HistogramDataset();
                    histoHM.addSeries(""
                            + JTronicHandle.Statistikhandle.TourData[selected].Tag + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Monat + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Jahr,
                            DummyData, Gruppen, (double) von, (double) bis);
                    histoHM.setType(HistogramType.RELATIVE_FREQUENCY);
                    plot.setDataset(Linecount, histoHM);
                    plot.mapDatasetToRangeAxis(Linecount, 0);
                    XYBarRenderer renderer = new XYBarRenderer();
                    renderer.setDrawBarOutline(true);

                    renderer.setSeriesPaint(0, getColour(Linecount, (int) 255 / Anzahl));

                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    plot.setRenderer(Linecount, renderer);

                }
                Linecount++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "StartHistoSp\nFehler: Hm " + e + " " + i + " " + j, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        if (Summenhisto) {
            HistogramDataset histoHM = new HistogramDataset();
            histoHM.addSeries("Summenhistogram", DummyData, Gruppen, (double) von, (double) bis);
            histoHM.setType(HistogramType.RELATIVE_FREQUENCY);

            plot.setDataset(0, histoHM);
            plot.mapDatasetToRangeAxis(0, 0);
            XYItemRenderer renderer = new XYBarRenderer();
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

            plot.setRenderer(0, renderer);
        }
        chart.setPadding(padding);
        ChartPanel Panel = new ChartPanel(chart);

        return Panel;
    }

    public JPanel StartHistoCd(JCicloTronic JTronicHandle) {

        boolean Summenhisto;
        int von = 0;
        int bis = 0;
        int num = 0;
        int selected;
        int single;
        int Gruppen = 0;
        int i = 100;
        int j = 10;
        int m = 0;
        int n = 0;
        int Linecount = 0;
        int Anzahl = 1;

        JFreeChart chart;

        double DummyData[] = new double[1];

        selected = JTronicHandle.Auswahl_Histogramm.getSelectedIndex();
        Summenhisto = JTronicHandle.Summenhistogramm_Check.isSelected();

        chart = ChartFactory.createHistogram("", "Cadence [n/min]", "Häufigkeit", new HistogramDataset(), PlotOrientation.HORIZONTAL, true, true, true);

        chart.setBackgroundPaint(Color.white);

        JTronicHandle.applyChartTheme(chart);

        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getRangeAxis().setFixedDimension(15.0);

        ArrayList Bufferarray = new ArrayList();
        double Buffer;

        if (selected == 0) {
            single = 1;
        } else {
            single = 0;
            Summenhisto = false;
        }

        try {
            von = Integer.parseInt(JTronicHandle.Properties.getProperty("HistovonCd", "100"));
            bis = Integer.parseInt(JTronicHandle.Properties.getProperty("HistobisCd", "200"));
            Gruppen = Integer.parseInt(JTronicHandle.Properties.getProperty("HistostepCd", "10"));
            Anzahl = (JTronicHandle.Auswahl_Histogramm.getItemCount() - 2) * single + 1;
            for (j = 0; j < Anzahl; j++) {
                if (single == 1) {
                    selected = j + 1;
                }
                if (!Summenhisto) {
                    num = JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                } else {
                    num += JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                }

                n = 0;
                if (!Summenhisto) {
                    create_Start_Stop(JTronicHandle, selected);  //die limits einer gezoomten Graphik ermitteln

                    for (i = start; i < stop; i++) {
                        Buffer = (double) JTronicHandle.Statistikhandle.TourData[selected].Cadence_gesZeit[i];
                        if (Buffer >= von && Buffer <= bis) {
                            Bufferarray.add(Buffer);
                        }
                    }
                } else {
                    for (i = 0; i < j + 1; i++) {
                        create_Start_Stop(JTronicHandle, i + 1);

                        for (m = start; m < stop; m++) {
                            Buffer = (double) JTronicHandle.Statistikhandle.TourData[i + 1].Cadence_gesZeit[m];
                            if (Buffer >= von && Buffer <= bis) {
                                Bufferarray.add(Buffer);
                            }

                        }
                        n += m;
                    }
                }

                DummyData = new double[Bufferarray.size()];

                for (i = 0; i < Bufferarray.size(); i++) {
                    DummyData[i] = new Double(Bufferarray.get(i).toString());
                }

                if (!Summenhisto) {
                    HistogramDataset histoHM = new HistogramDataset();
                    histoHM.addSeries(""
                            + JTronicHandle.Statistikhandle.TourData[selected].Tag + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Monat + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Jahr,
                            DummyData, Gruppen, (double) von, (double) bis);
                    histoHM.setType(HistogramType.RELATIVE_FREQUENCY);
                    plot.setDataset(Linecount, histoHM);
                    plot.mapDatasetToRangeAxis(Linecount, 0);
                    XYBarRenderer renderer = new XYBarRenderer();
                    renderer.setDrawBarOutline(true);

                    renderer.setSeriesPaint(0, getColour(Linecount, (int) 255 / Anzahl));

                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    plot.setRenderer(Linecount, renderer);

                }
                Linecount++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "StartHistoCd\nFehler: Cd " + e + " " + i + " " + j, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        if (Summenhisto) {
            HistogramDataset histoHM = new HistogramDataset();
            histoHM.addSeries("Summenhistogram", DummyData, Gruppen, (double) von, (double) bis);
            histoHM.setType(HistogramType.RELATIVE_FREQUENCY);

            plot.setDataset(0, histoHM);
            plot.mapDatasetToRangeAxis(0, 0);
            XYItemRenderer renderer = new XYBarRenderer();
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

            plot.setRenderer(0, renderer);
        };
        chart.setPadding(padding);
        ChartPanel Panel = new ChartPanel(chart);

        return Panel;

    }

    public JPanel StartHistoSchrittlänge(JCicloTronic JTronicHandle) {

        boolean Summenhisto;
        int von = 0;
        int bis = 0;
        int num = 0;
        int selected;
        int single;
        int Gruppen = 0;
        int i = 100;
        int j = 10;
        int m = 0;
        int n = 0;
        int Linecount = 0;
        int Anzahl = 1;

        JFreeChart chart;

        double DummyData[] = new double[1];

        selected = JTronicHandle.Auswahl_Histogramm.getSelectedIndex();
        Summenhisto = JTronicHandle.Summenhistogramm_Check.isSelected();

        chart = ChartFactory.createHistogram("", "Schrittlänge [cm]", "Häufigkeit", new HistogramDataset(), PlotOrientation.HORIZONTAL, true, true, true);

        chart.setBackgroundPaint(Color.white);

        JTronicHandle.applyChartTheme(chart);

        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getRangeAxis().setFixedDimension(15.0);

        ArrayList Bufferarray = new ArrayList();
        double Buffer;

        if (selected == 0) {
            single = 1;
        } else {
            single = 0;
            Summenhisto = false;
        }

        try {
            von = Integer.parseInt(JTronicHandle.Properties.getProperty("HistovonSchrittlänge", "100"));
            bis = Integer.parseInt(JTronicHandle.Properties.getProperty("HistobisSchrittlänge", "200"));
            Gruppen = Integer.parseInt(JTronicHandle.Properties.getProperty("HistostepSchrittlänge", "10"));
            Anzahl = (JTronicHandle.Auswahl_Histogramm.getItemCount() - 2) * single + 1;
            for (j = 0; j < Anzahl; j++) {
                if (single == 1) {
                    selected = j + 1;
                }
                if (!Summenhisto) {
                    num = JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                } else {
                    num += JTronicHandle.Statistikhandle.TourData[selected].Datenpunkte;
                }

                n = 0;
                if (!Summenhisto) {
                    create_Start_Stop(JTronicHandle, selected);  //die limits einer gezoomten Graphik ermitteln

                    for (i = start; i < stop; i++) {
                        Buffer = (double) JTronicHandle.Statistikhandle.TourData[selected].Schritt_länge[i];
                        if (Buffer >= von && Buffer <= bis) {
                            Bufferarray.add(Buffer);
                        }
                    }
                } else {
                    for (i = 0; i < j + 1; i++) {
                        create_Start_Stop(JTronicHandle, i + 1);
                        for (m = start; m < stop; m++) {
                            Buffer = (double) JTronicHandle.Statistikhandle.TourData[i + 1].Schritt_länge[m];
                            if (Buffer >= von && Buffer <= bis) {
                                Bufferarray.add(Buffer);
                            }

                        }

                    }
                }

                DummyData = new double[Bufferarray.size()];

                for (i = 0; i < Bufferarray.size(); i++) {
                    DummyData[i] = new Double(Bufferarray.get(i).toString());
                }

                if (!Summenhisto) {
                    HistogramDataset histoHM = new HistogramDataset();
                    histoHM.addSeries(""
                            + JTronicHandle.Statistikhandle.TourData[selected].Tag + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Monat + "."
                            + JTronicHandle.Statistikhandle.TourData[selected].Jahr,
                            DummyData, Gruppen, (double) von, (double) bis);
                    histoHM.setType(HistogramType.RELATIVE_FREQUENCY);
                    plot.setDataset(Linecount, histoHM);
                    plot.mapDatasetToRangeAxis(Linecount, 0);
                    XYBarRenderer renderer = new XYBarRenderer();
                    renderer.setDrawBarOutline(true);

                    renderer.setSeriesPaint(0, getColour(Linecount, (int) 255 / Anzahl));

                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    plot.setRenderer(Linecount, renderer);

                }
                Linecount++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "StartHistoSchrittlänge\nFehler: Hm " + e + " " + i + " " + j, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        if (Summenhisto) {
            HistogramDataset histoHM = new HistogramDataset();
            histoHM.addSeries("Summenhistogram", DummyData, Gruppen, (double) von, (double) bis);
            histoHM.setType(HistogramType.RELATIVE_FREQUENCY);

            plot.setDataset(0, histoHM);
            plot.mapDatasetToRangeAxis(0, 0);
            XYItemRenderer renderer = new XYBarRenderer();
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

            plot.setRenderer(0, renderer);
        }
        chart.setPadding(padding);
        ChartPanel Panel = new ChartPanel(chart);

        return Panel;
    }
    
 
    private static Paint getColour(int Farbe, int alpha) {

        Paint Colour = Color.BLACK;

        Farbe = Farbe % 7;
        alpha = (int) (125 + 130 * (alpha / 255));
        switch (Farbe) {
            case 6:
                Colour = new Color(0, 0, 0, alpha);
                break;
            case 0:
                Colour = new Color(0, 0, 255, alpha);
                break;
            case 2:
                Colour = new Color(0, 255, 0, alpha);
                break;
            case 1:
                Colour = new Color(255, 0, 0, alpha);
                break;
            case 3:
                Colour = new Color(0, 255, 255, alpha);
                break;
            case 4:
                Colour = new Color(255, 0, 255, alpha);
                break;
            case 5:
                Colour = new Color(255, 255, 0, alpha);
                break;

        }
        return Colour;
    }

    private void create_Start_Stop(JCicloTronic TronicHandle, int j) {
        start = 0;
        stop = 0;

        int i = 0;
        int Datenp = TronicHandle.Statistikhandle.TourData[j].Datenpunkte;

        if (TronicHandle.Graphik_Radio_Strecke.isSelected() == true) {
            while (TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] < TronicHandle.graph_min && i + 1 < Datenp) {
                i++;
            }
            start = i;
            while (TronicHandle.Statistikhandle.TourData[j].Strecke_gesZeit[i] < TronicHandle.graph_max && i + 1 < Datenp) {
                i++;
            }

            stop = i;
        } else {

            while (TronicHandle.Statistikhandle.TourData[j].gesZeit[i] < TronicHandle.graph_min && i + 1 < Datenp) {
                i++;
            }

            start = i;
            while (TronicHandle.Statistikhandle.TourData[j].gesZeit[i] < TronicHandle.graph_max && i + 1 < Datenp) {
                i++;
            }

            stop = i;
        }

    }

    private JCicloTronic JTronicHandle;
    private JStatistik Statistikhandle;
    private JTourData TourData;
    private int start;
    private int stop;
    private RectangleInsets padding;

}
