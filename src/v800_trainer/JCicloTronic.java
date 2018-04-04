package v800_trainer;

/*
 * JCicloTronic.java
 ** Chainwheel and all dependend source files is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Chainwheel is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.


 * Created on 11. Juni 2000, 13:25
 */

/**
 *
 * @author  volker
 * @version
 */
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// import gnu.io.*;

import java.io.File;
import java.io.FilenameFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.TableColumn;
import java.util.Calendar;

import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

import java.util.ArrayList;

//import org.jdesktop.swingx.JXMapViewer;

import org.jxmapviewer.JXMapViewer;

//import org.jdesktop.swingx.JXMapKit.DefaultProviders;
//import org.jdesktop.swingx.mapviewer.GeoPosition;
import java.awt.geom.Point2D;
//import org.jdesktop.swingx.painter.Painter;




//import org.jxmapviewer.OSMTileFactoryInfo;

//import org.jxmapviewer.viewer.DefaultTileFactory;

import org.jxmapviewer.viewer.GeoPosition;

//import org.jxmapviewer.viewer.TileFactoryInfo;

// import org.jxmapviewer.JXMapKit.DefaultProviders;

import org.jxmapviewer.painter.Painter;

import org.jxmapviewer.painter.CompoundPainter;


import org.jxmapviewer.viewer.DefaultWaypoint;

import org.jxmapviewer.viewer.Waypoint;

import org.jxmapviewer.viewer.WaypointPainter;


      


import org.jfree.chart.ChartPanel;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.JFreeChart;

//import com.sun.jna.Library; 
//import com.sun.jna.Native;
import java.util.*;

//import com.sun.jna.win32.*;
//
//import com.sun.jna.platform.win32.WinDef.HWND;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.text.DecimalFormat;
import javax.swing.filechooser.FileFilter;
//import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
//import org.jdesktop.swingx.mapviewer.TileFactory;
//import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jfree.data.time.Second;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;

public class JCicloTronic extends javax.swing.JFrame {

    /** Creates new form JCicloTronic */
    public JCicloTronic() {



        ScreenSize = new Dimension();
        SelectionChanged = false;
        ScreenSize.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 50,
                java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50);
        Size = new Dimension();
  
        Properties = new java.util.Properties();
        SystemProperties = java.lang.System.getProperties();
        chooser = new javax.swing.JFileChooser();
        RawData = new byte[98316];
  //        System.setProperty("jna.library.path" , "C:/WINDOWS/system32");


   

        try {
            FileInputStream in = new FileInputStream(SystemProperties.getProperty("user.dir") + SystemProperties.getProperty("file.separator") + "JCicloexp.cfg");
            Properties.load(in);
            in.close();
        } catch (Exception e) {
            FontSize = 20;
            setFontSizeGlobal("Tahoma", FontSize);

            JOptionPane.showMessageDialog(null, "Keine Config-Datei in:  " + SystemProperties.getProperty("user.dir"), "Achtung!", JOptionPane.ERROR_MESSAGE);
            Properties.put("working.dir", SystemProperties.getProperty("user.dir"));
            Eigenschaften = new Eigenschaften(new javax.swing.JFrame(), true, this);
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            this.setSize(new Dimension((int) width, (int) height));
            this.setPreferredSize(new Dimension((int) width, (int) height));
            this.setMinimumSize(new Dimension((int) width, (int) height));
            repaint();
        }
        try {


            UIManager.setLookAndFeel(Properties.getProperty("LookFeel"));
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch (Exception exc) {
        }
     


        if (debug) {
            try {
                System.setErr(new java.io.PrintStream(new FileOutputStream(Properties.getProperty("working.dir")
                        + SystemProperties.getProperty("file.separator") + "error.txt")));
                //        System.err =  new FileOutputStream(Properties.getProperty("working.dir") + SystemProperties.getProperty("file.separator") + "error.txt");
                System.setOut(new java.io.PrintStream(new FileOutputStream(Properties.getProperty("working.dir")
                        + SystemProperties.getProperty("file.separator") + "error.txt")));
            } catch (Exception err) {
            }
        }
        
        initComponents();

        setTitle("V800 Trainer    Datadir: " + Properties.getProperty("data.dir"));

        icon = new ImageIcon("hw.jpg");
        setIconImage(icon.getImage());

        if (Integer.parseInt(Properties.getProperty("View Geschw", "1")) == 1) {
            Graphik_check_Geschwindigkeit.setSelected(true);
        } else {
            Graphik_check_Geschwindigkeit.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Höhe", "1")) == 1) {
            Graphik_check_Höhe.setSelected(true);
        } else {
            Graphik_check_Höhe.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Hf", "1")) == 1) {
            Graphik_check_HF.setSelected(true);
        } else {
            Graphik_check_HF.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Temp", "1")) == 1) {
            Graphik_check_Temp.setSelected(true);
        } else {
            Graphik_check_Temp.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Steigp", "1")) == 1) {
            Graphik_check_Steigung_p.setSelected(true);
        } else {
            Graphik_check_Steigung_p.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Steigm", "1")) == 1) {
            Graphik_check_Steigung_m.setSelected(true);
        } else {
            Graphik_check_Steigung_m.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View av_Geschw", "1")) == 1) {
            Graphik_check_av_Geschw.setSelected(true);
        } else {
            Graphik_check_av_Geschw.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Cadence", "1")) == 1) {
            Graphik_check_Cadence.setSelected(true);
        } else {
            Graphik_check_Cadence.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("View Schrittlänge", "1")) == 1) {
            Graphik_check_Schrittlänge.setSelected(true);
        } else {
            Graphik_check_Schrittlänge.setSelected(false);
        }
        
        if (Integer.parseInt(Properties.getProperty("ZeitStreckeAbstände", "1")) == 1) {
            Graphik_check_Abstand.setSelected(true);
        } else {
            Graphik_check_Abstand.setSelected(false);
        }
        if (Integer.parseInt(Properties.getProperty("SummenHisto", "1")) == 1) {
            Summenhistogramm_Check.setSelected(true);
        } else {
            Summenhistogramm_Check.setSelected(false);
        }

         if (Integer.parseInt(Properties.getProperty("xy_Strecke", "1")) == 1) {
            Graphik_Radio_Strecke.setSelected(true);
            Graphik_Radio_Zeit.setSelected(false);
        } else {
            Graphik_Radio_Strecke.setSelected(false);
            Graphik_Radio_Zeit.setSelected(true);
        }
        
        //Buttons für XY-Darstellung   (Über Strecke oder über Zeit)
        X_Axis = new ButtonGroup();
        X_Axis.add(Graphik_Radio_Strecke);
        X_Axis.add(Graphik_Radio_Zeit);

       //Buttons für Jahresübersicht
        Übersicht = new ButtonGroup();
        Übersicht.add(jRadioButton_jahresverlauf);
        Übersicht.add(jRadioButton_monatsübersicht);

        Datenliste_Zeitabschnitt.addItem("nicht aktiv");
        Datenliste_Zeitabschnitt.addItem("vergangene Woche");
        Datenliste_Zeitabschnitt.addItem("vergangener Monat");
        Datenliste_Zeitabschnitt.addItem("vergangenes Jahr");
        Datenliste_Zeitabschnitt.addItem("Alles");

        if (Datentabelle.getRowCount() != 0) {
            Datentabelle.addRowSelectionInterval(0, 0);
            Datenliste_scroll_Panel.getViewport().setViewPosition(new java.awt.Point(0, 0));
        }
//        if (Properties.getProperty("CommPort").equals("nocom")) {
//            jMenuReceive.setEnabled(false);
//        } else {
//            jMenuReceive.setEnabled(true);
//        }

        jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());

  
        setFileChooserFont(chooser.getComponents());
        locmap = true;
        Map_Type.removeAllItems();
        Map_Type.addItem("OpenStreetMap");
        Map_Type.addItem("Virtual Earth Map");
        Map_Type.addItem("Virtual Earth Satelite");
        Map_Type.addItem("Virtual Earth Hybrid");
        locmap = false;
    //    ChangeModel();
    }

    @Override
    public void repaint() {

        FontSize = Integer.parseInt(Properties.getProperty("FontSize", "12"));
        Font = Properties.getProperty("Font", "Tahoma");
        setFontSizeGlobal(Font, FontSize);
        if (graphik != null && xygraphik != null) {
            xygraphik.setSize(Graphik_Sub_Panel.getSize());
        }
        if (Jahresüberblick != null) {
            Jahresüberblick.setSize(jPanel17Übersichtchart.getSize());
        }

        if (HistoGram != null) {
            Size = jPanel19_HistoCd.getSize();

            HFHistogramm.setSize(Size);
            HMHistogramm.setSize(Size);
            SPHistogramm.setSize(Size);
            CdHistogramm.setSize(Size);
        }

        if (mapKit != null) {
            mapKit.setSize(Map_internal_Panel.getSize());
            if (Update_Map_paint) {
                int zoom = mapKit.getMainMap().getZoom();
                GeoPosition Center = mapKit.getMainMap().getCenterPosition();

                Draw_Map();

                mapKit.setCenterPosition(Center);
                int minzoom = mapKit.getMainMap().getTileFactory().getInfo().getMinimumZoomLevel();
                if (minzoom <= zoom) {
                    mapKit.getMainMap().setZoom(zoom);
                } else {
                    mapKit.getMainMap().setZoom(minzoom);
                }

            }
    
        }

        super.repaint();
           
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup_Karte = new javax.swing.ButtonGroup();
        Hauptfenster = new javax.swing.JTabbedPane();
        Datenliste_Panel = new javax.swing.JPanel();
        Datenliste_scroll_Panel = new javax.swing.JScrollPane();
        Datentabelle = new javax.swing.JTable();
        Datenliste_Jahr = new javax.swing.JComboBox();
        Datenliste_Monat = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        Datenliste_Zeitabschnitt = new javax.swing.JComboBox();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        Datenliste_TourTyp = new javax.swing.JComboBox();
        jLabel68 = new javax.swing.JLabel();
        jLabel69_Selektiert = new javax.swing.JLabel();
        Datenliste_search = new javax.swing.JTextField();
        Datenliste_searchButton = new javax.swing.JButton();
        jLabel_search = new javax.swing.JLabel();
        Info_Panel = new javax.swing.JPanel();
        Auswahl_Info = new javax.swing.JComboBox();
        Info_Titel = new javax.swing.JTextField();
        Info_Vorname = new javax.swing.JTextField();
        Info_Name = new javax.swing.JTextField();
        Info_GebTag = new javax.swing.JTextField();
        Info_Gewicht = new javax.swing.JTextField();
        Info_Verein = new javax.swing.JTextField();
        Info_Material = new javax.swing.JTextField();
        Info_Materialgewicht = new javax.swing.JTextField();
        Info_Startort = new javax.swing.JTextField();
        Info_Zielort = new javax.swing.JTextField();
        jLabel24Uhrzeit = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        Info_Button_kopieren = new javax.swing.JButton();
        Info_Button_einfügen = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Info_Notiz = new javax.swing.JTextArea();
        Info_Button_speichern = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65Typ = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        Info_Button_Suche_TrackLog = new javax.swing.JButton();
        Info_Track_Log = new javax.swing.JTextField();
        Statistik_Panel = new javax.swing.JPanel();
        Auswahl_Statistik = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        Statistik_Höhe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Statistik_Minimale_Höhe = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Statistik_Maximale_Höhe = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Statistik_Summe_Hm_Steigung = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Statistik_Summe_Hm_Gefälle = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Statistik_HM_pro_km = new javax.swing.JLabel();
        Statistik_Geschwindigkeit = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Statistik_Max_Geschw = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Statistik_av_Geschw = new javax.swing.JLabel();
        Statistik_Herzfrequenz = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Statistik_max_HF = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Statistik_av_HF = new javax.swing.JLabel();
        Statistik_Steigung_m = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Statistik_max_Steigung_m = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Statistik_av_Steigung_m = new javax.swing.JLabel();
        Statistik_Gefälle_m = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        Statistik_max_Gefälle_m = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Statistik_av_Gefälle_m = new javax.swing.JLabel();
        Statistik_Temperatur = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        Statistik_min_Temp = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Statistik_max_Temp = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        Statistik_av_Temp = new javax.swing.JLabel();
        Statistik_Cadence = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        Statistik_max_Cadence = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Statistik_av_Cadence = new javax.swing.JLabel();
        Statistik_Steigung_p = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        Statistik_max_Steigung_p = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Statistik_av_Steigung_p = new javax.swing.JLabel();
        Statistik_Gefälle_p = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        Statistik_max_Gefälle_p = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        Statistik_av_Gefälle_p = new javax.swing.JLabel();
        Statistik_Zeit = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        Statistik_Zeit_absolut = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        Statistik_Zeit_aktiv = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        Statistik_Teilstrecke = new javax.swing.JLabel();
        Statistik_Schrittlänge = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        Statistik_max_Schrittlänge = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        Statistik_av_Schrittlänge = new javax.swing.JLabel();
        Statistik_Training = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        Statistik_Belastung = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        Statistik_Erholungszeit = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        Statistik_Lauf_Index = new javax.swing.JLabel();
        Statistik_Kalorien = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        Statistik_Kalorien_absolut = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Statistik_Kalorien_h = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        Statistik_Fett = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        Statistik_Protein = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Statistik_Titel = new javax.swing.JLabel();
        Graphik_Panel = new javax.swing.JPanel();
        Auswahl_Graphik = new javax.swing.JComboBox();
        Graphik_Sub_Panel = new javax.swing.JPanel();
        Graphik_check_Geschwindigkeit = new javax.swing.JCheckBox();
        Graphik_check_Höhe = new javax.swing.JCheckBox();
        Graphik_check_HF = new javax.swing.JCheckBox();
        Graphik_check_Temp = new javax.swing.JCheckBox();
        Graphik_check_Steigung_p = new javax.swing.JCheckBox();
        Graphik_check_Steigung_m = new javax.swing.JCheckBox();
        Graphik_check_Cadence = new javax.swing.JCheckBox();
        Graphik_Radio_Strecke = new javax.swing.JRadioButton();
        Graphik_Radio_Zeit = new javax.swing.JRadioButton();
        Graphik_check_Abstand = new javax.swing.JCheckBox();
        Graphik_check_av_Geschw = new javax.swing.JCheckBox();
        Graphik_check_Schrittlänge = new javax.swing.JCheckBox();
        Histogramm_Panel = new javax.swing.JPanel();
        Auswahl_Histogramm = new javax.swing.JComboBox();
        Summenhistogramm_Check = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel18_HistoSP = new javax.swing.JPanel();
        jPanel17_HistoHM = new javax.swing.JPanel();
        jPanel16_HistoHF = new javax.swing.JPanel();
        jPanel19_HistoCd = new javax.swing.JPanel();
        jLabel26_Histotitel = new javax.swing.JLabel();
        Map_Panel = new javax.swing.JPanel();
        Auswahl_Map = new javax.swing.JComboBox();
        LoadGoogleEarth = new javax.swing.JButton();
        Kein_kmz_text = new javax.swing.JLabel();
        Map_internal_Panel = new javax.swing.JPanel();
        jLabel_map_streckenlänge = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        Map_Type = new javax.swing.JComboBox<>();
        Jahresuebersicht_Panel = new javax.swing.JPanel();
        Auswahl_Übersicht = new javax.swing.JComboBox();
        JahrVergleich = new javax.swing.JComboBox();
        jLabel67 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jRadioButton_jahresverlauf = new javax.swing.JRadioButton();
        jRadioButton_monatsübersicht = new javax.swing.JRadioButton();
        jPanel17Übersichtchart = new javax.swing.JPanel();
        jMenuHaupt = new javax.swing.JMenuBar();
        jMenuDatei = new javax.swing.JMenu();
        jMenuOpen = new javax.swing.JMenuItem();
        jMenuOpenall = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuLöschen = new javax.swing.JMenuItem();
        jMenuExit = new javax.swing.JMenuItem();
        jMenu_V800_Laden = new javax.swing.JMenu();
        jMenuTourEditor = new javax.swing.JMenu();
        jMenuEinstellungen = new javax.swing.JMenu();
        jMenuHilfe = new javax.swing.JMenu();

        setTitle("HWCyclingData");
        setPreferredSize(new java.awt.Dimension(800, 600));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0};
        layout.rowHeights = new int[] {0};
        getContentPane().setLayout(layout);

        Hauptfenster.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Hauptfenster.setAlignmentX(0.0F);
        Hauptfenster.setAlignmentY(0.0F);
        Hauptfenster.setAutoscrolls(true);
        Hauptfenster.setPreferredSize(new java.awt.Dimension(10, 10));

        java.awt.GridBagLayout Datenliste_PanelLayout = new java.awt.GridBagLayout();
        Datenliste_PanelLayout.columnWidths = new int[] {0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0};
        Datenliste_PanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        Datenliste_Panel.setLayout(Datenliste_PanelLayout);

        Datenliste_scroll_Panel.setAutoscrolls(true);

        Datentabelle.setAutoCreateColumnsFromModel(false);
        Datentabelle.setFont(Datentabelle.getFont());
        Datentabelle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        Datentabelle.setRowHeight(25);
        //ChangeModel();
        Datentabelle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                DatentabelleMouseDragged(evt);
            }
        });
        Datentabelle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DatentabelleMouseClicked(evt);
            }
        });
        Datenliste_scroll_Panel.setViewportView(Datentabelle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Datenliste_Panel.add(Datenliste_scroll_Panel, gridBagConstraints);

        Datenliste_Jahr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Datenliste_JahrActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(Datenliste_Jahr, gridBagConstraints);

        Datenliste_Monat.setEnabled(false);
        InitComboMonat();
        Datenliste_Monat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Datenliste_MonatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(Datenliste_Monat, gridBagConstraints);

        jLabel11.setText("Jahr wählen");
        jLabel11.setToolTipText("Selektier alle Daten des entsprechenden Jahres");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(jLabel11, gridBagConstraints);

        jLabel51.setText("Monat wählen");
        jLabel51.setToolTipText("Selektiert alle Daten des entsprechenden Monats");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(jLabel51, gridBagConstraints);

        Datenliste_Zeitabschnitt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Datenliste_ZeitabschnittActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(Datenliste_Zeitabschnitt, gridBagConstraints);

        jLabel65.setText("Zeitraum wählen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(jLabel65, gridBagConstraints);

        jLabel66.setText("Tour-Typ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(jLabel66, gridBagConstraints);

        Datenliste_TourTyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Datenliste_TourTypActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(Datenliste_TourTyp, gridBagConstraints);

        jLabel68.setText("Selektierte Daten / von");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(jLabel68, gridBagConstraints);

        jLabel69_Selektiert.setText("'                                                 '");
        jLabel69_Selektiert.setMaximumSize(new java.awt.Dimension(300, 50));
        jLabel69_Selektiert.setMinimumSize(new java.awt.Dimension(300, 50));
        jLabel69_Selektiert.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        Datenliste_Panel.add(jLabel69_Selektiert, gridBagConstraints);

        Datenliste_search.setToolTipText("~ Vorstellen um zu Deselektieren");
        Datenliste_search.setMaximumSize(new java.awt.Dimension(200, 23));
        Datenliste_search.setMinimumSize(new java.awt.Dimension(200, 23));
        Datenliste_search.setPreferredSize(new java.awt.Dimension(200, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(Datenliste_search, gridBagConstraints);

        Datenliste_searchButton.setText("Suchen");
        Datenliste_searchButton.setMaximumSize(new java.awt.Dimension(200, 23));
        Datenliste_searchButton.setMinimumSize(new java.awt.Dimension(200, 23));
        Datenliste_searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Datenliste_searchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(Datenliste_searchButton, gridBagConstraints);

        jLabel_search.setText("Eintrag im Titel suchen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Datenliste_Panel.add(jLabel_search, gridBagConstraints);

        Hauptfenster.addTab("Datenliste", null, Datenliste_Panel, "");

        Info_Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Info_PanelComponentShown(evt);
            }
        });
        java.awt.GridBagLayout Info_PanelLayout = new java.awt.GridBagLayout();
        Info_PanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        Info_PanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        Info_Panel.setLayout(Info_PanelLayout);

        Auswahl_Info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_InfoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Info_Panel.add(Auswahl_Info, gridBagConstraints);

        Info_Titel.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Titel, gridBagConstraints);

        Info_Vorname.setText("jTextField4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Vorname, gridBagConstraints);

        Info_Name.setText("jTextField5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Name, gridBagConstraints);

        Info_GebTag.setText("jTextField6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_GebTag, gridBagConstraints);

        Info_Gewicht.setText("jTextField7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Gewicht, gridBagConstraints);

        Info_Verein.setText("jTextField8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Verein, gridBagConstraints);

        Info_Material.setText("jTextField10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Material, gridBagConstraints);

        Info_Materialgewicht.setText("jTextField9");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Materialgewicht, gridBagConstraints);

        Info_Startort.setText("jTextField2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Startort, gridBagConstraints);

        Info_Zielort.setText("jTextField3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Zielort, gridBagConstraints);

        jLabel24Uhrzeit.setText("jLabel24");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel24Uhrzeit, gridBagConstraints);

        jLabel24.setText("Titel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel24, gridBagConstraints);

        jLabel52.setText("Vorname");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel52, gridBagConstraints);

        jLabel53.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel53, gridBagConstraints);

        jLabel54.setText("Geburtsdatum");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel54, gridBagConstraints);

        jLabel55.setText("Gewicht");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel55, gridBagConstraints);

        jLabel56.setText("Verein / Mitfahrer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel56, gridBagConstraints);

        jLabel57.setText("Material");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel57, gridBagConstraints);

        jLabel58.setText("Materialgewicht");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel58, gridBagConstraints);

        jLabel59.setText("Startort");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel59, gridBagConstraints);

        jLabel60.setText("Zielort");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel60, gridBagConstraints);

        jLabel61.setText("Notiz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel61, gridBagConstraints);

        Info_Button_kopieren.setText("Kopieren");
        Info_Button_kopieren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Info_Button_kopierenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Button_kopieren, gridBagConstraints);

        Info_Button_einfügen.setText("Einfügen");
        Info_Button_einfügen.setEnabled(false);
        Info_Button_einfügen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Info_Button_einfügenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Button_einfügen, gridBagConstraints);

        Info_Notiz.setLineWrap(true);
        jScrollPane2.setViewportView(Info_Notiz);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 36;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Info_Panel.add(jScrollPane2, gridBagConstraints);

        Info_Button_speichern.setText("Speichern");
        Info_Button_speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Info_Button_speichernActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Button_speichern, gridBagConstraints);

        jLabel63.setText("Startzeit:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel63, gridBagConstraints);

        jLabel64.setText("Typ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel64, gridBagConstraints);

        jLabel65Typ.setText("jLabel65");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel65Typ, gridBagConstraints);

        jLabel69.setText("Track Log");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(jLabel69, gridBagConstraints);

        Info_Button_Suche_TrackLog.setText("...");
        Info_Button_Suche_TrackLog.setToolTipText("Track Log ändern");
        Info_Button_Suche_TrackLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Info_Button_Suche_TrackLogActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 32;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Info_Panel.add(Info_Button_Suche_TrackLog, gridBagConstraints);

        Info_Track_Log.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 32;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Info_Panel.add(Info_Track_Log, gridBagConstraints);

        Hauptfenster.addTab("Infos", null, Info_Panel, "");

        Statistik_Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Statistik_PanelComponentShown_StatistikStarten(evt);
            }
        });
        java.awt.GridBagLayout Statistik_PanelLayout1 = new java.awt.GridBagLayout();
        Statistik_PanelLayout1.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        Statistik_PanelLayout1.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        Statistik_Panel.setLayout(Statistik_PanelLayout1);

        Auswahl_Statistik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_StatistikActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Statistik_Panel.add(Auswahl_Statistik, gridBagConstraints);

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel2Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel2.setLayout(jPanel2Layout);

        Statistik_Höhe.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Höhe [m]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Höhe.setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        jLabel1.setText("min.:");
        Statistik_Höhe.add(jLabel1);

        Statistik_Minimale_Höhe.setText("---");
        Statistik_Minimale_Höhe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Statistik_Höhe.add(Statistik_Minimale_Höhe);

        jLabel2.setText("max.:");
        Statistik_Höhe.add(jLabel2);

        Statistik_Maximale_Höhe.setText("---");
        Statistik_Höhe.add(Statistik_Maximale_Höhe);

        jLabel3.setText("Hm +:");
        Statistik_Höhe.add(jLabel3);

        Statistik_Summe_Hm_Steigung.setText("---");
        Statistik_Höhe.add(Statistik_Summe_Hm_Steigung);

        jLabel4.setText("Hm -:");
        Statistik_Höhe.add(jLabel4);

        Statistik_Summe_Hm_Gefälle.setText("---");
        Statistik_Höhe.add(Statistik_Summe_Hm_Gefälle);

        jLabel17.setText("Hm/km:");
        Statistik_Höhe.add(jLabel17);

        Statistik_HM_pro_km.setText("---");
        Statistik_Höhe.add(Statistik_HM_pro_km);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Höhe, gridBagConstraints);

        Statistik_Geschwindigkeit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Geschwindigkeit [km/h]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Geschwindigkeit.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        jLabel5.setText("max.:");
        jLabel5.setToolTipText("");
        Statistik_Geschwindigkeit.add(jLabel5);

        Statistik_Max_Geschw.setText("---");
        Statistik_Geschwindigkeit.add(Statistik_Max_Geschw);

        jLabel6.setText("Durchschnitt:");
        Statistik_Geschwindigkeit.add(jLabel6);

        Statistik_av_Geschw.setText("---");
        Statistik_Geschwindigkeit.add(Statistik_av_Geschw);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Geschwindigkeit, gridBagConstraints);

        Statistik_Herzfrequenz.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herzfrequenz [p/min]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Herzfrequenz.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        jLabel7.setText("max.:");
        Statistik_Herzfrequenz.add(jLabel7);

        Statistik_max_HF.setText("---");
        Statistik_Herzfrequenz.add(Statistik_max_HF);

        jLabel8.setText("Durchschnitt:");
        Statistik_Herzfrequenz.add(jLabel8);

        Statistik_av_HF.setText("---");
        Statistik_Herzfrequenz.add(Statistik_av_HF);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Herzfrequenz, gridBagConstraints);

        Statistik_Steigung_m.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Steigung [m/min]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Steigung_m.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel9.setText("max.:");
        Statistik_Steigung_m.add(jLabel9);

        Statistik_max_Steigung_m.setText("---");
        Statistik_Steigung_m.add(Statistik_max_Steigung_m);

        jLabel10.setText("Durchschnitt:");
        Statistik_Steigung_m.add(jLabel10);

        Statistik_av_Steigung_m.setText("---");
        Statistik_Steigung_m.add(Statistik_av_Steigung_m);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Steigung_m, gridBagConstraints);

        Statistik_Gefälle_m.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gefälle [m/min]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Gefälle_m.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel12.setText("max.:");
        Statistik_Gefälle_m.add(jLabel12);

        Statistik_max_Gefälle_m.setText("---");
        Statistik_Gefälle_m.add(Statistik_max_Gefälle_m);

        jLabel13.setText("Durchschnitt:");
        Statistik_Gefälle_m.add(jLabel13);

        Statistik_av_Gefälle_m.setText("---");
        Statistik_Gefälle_m.add(Statistik_av_Gefälle_m);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Gefälle_m, gridBagConstraints);

        Statistik_Temperatur.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Temperatur [°C]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Temperatur.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        jLabel14.setText("min.:");
        Statistik_Temperatur.add(jLabel14);

        Statistik_min_Temp.setText("---");
        Statistik_Temperatur.add(Statistik_min_Temp);

        jLabel15.setText("max.:");
        Statistik_Temperatur.add(jLabel15);

        Statistik_max_Temp.setText("---");
        Statistik_Temperatur.add(Statistik_max_Temp);

        jLabel16.setText("Durchschnitt:");
        Statistik_Temperatur.add(jLabel16);

        Statistik_av_Temp.setText("---");
        Statistik_Temperatur.add(Statistik_av_Temp);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Temperatur, gridBagConstraints);

        Statistik_Cadence.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadence [n/min]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Cadence.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel18.setText("max.:");
        Statistik_Cadence.add(jLabel18);

        Statistik_max_Cadence.setText("---");
        Statistik_Cadence.add(Statistik_max_Cadence);

        jLabel19.setText("Durchschnitt:");
        Statistik_Cadence.add(jLabel19);

        Statistik_av_Cadence.setText("---");
        Statistik_Cadence.add(Statistik_av_Cadence);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Cadence, gridBagConstraints);

        Statistik_Steigung_p.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Steigung [%]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Steigung_p.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel20.setText("max.:");
        Statistik_Steigung_p.add(jLabel20);

        Statistik_max_Steigung_p.setText("---");
        Statistik_Steigung_p.add(Statistik_max_Steigung_p);

        jLabel21.setText("Durchschnitt:");
        Statistik_Steigung_p.add(jLabel21);

        Statistik_av_Steigung_p.setText("---");
        Statistik_Steigung_p.add(Statistik_av_Steigung_p);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Steigung_p, gridBagConstraints);

        Statistik_Gefälle_p.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gefälle [%]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Gefälle_p.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel22.setText("max.:");
        Statistik_Gefälle_p.add(jLabel22);

        Statistik_max_Gefälle_p.setText("---");
        Statistik_Gefälle_p.add(Statistik_max_Gefälle_p);

        jLabel23.setText("Durchschnitt:");
        Statistik_Gefälle_p.add(jLabel23);

        Statistik_av_Gefälle_p.setText("---");
        Statistik_Gefälle_p.add(Statistik_av_Gefälle_p);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Gefälle_p, gridBagConstraints);

        Statistik_Zeit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zeit [hh:mm:ss]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Zeit.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        jLabel47.setText("absolut:");
        Statistik_Zeit.add(jLabel47);

        Statistik_Zeit_absolut.setText("---");
        Statistik_Zeit.add(Statistik_Zeit_absolut);

        jLabel48.setText("gefahren:");
        Statistik_Zeit.add(jLabel48);

        Statistik_Zeit_aktiv.setText("---");
        Statistik_Zeit.add(Statistik_Zeit_aktiv);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Zeit, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zoom", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel5.setPreferredSize(new java.awt.Dimension(270, 65));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jLabel25.setText("Teilstrecke:  ");
        jLabel25.setMaximumSize(new java.awt.Dimension(200, 26));
        jLabel25.setMinimumSize(new java.awt.Dimension(200, 26));
        jLabel25.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel5.add(jLabel25);

        Statistik_Teilstrecke.setText("jLabel26");
        jPanel5.add(Statistik_Teilstrecke);
        Statistik_Teilstrecke.getAccessibleContext().setAccessibleName("jLabel26_Teilstrecke");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(jPanel5, gridBagConstraints);

        Statistik_Schrittlänge.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Schrittlänge [cm]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Schrittlänge.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel26.setText("max.:");
        Statistik_Schrittlänge.add(jLabel26);

        Statistik_max_Schrittlänge.setText("---");
        Statistik_Schrittlänge.add(Statistik_max_Schrittlänge);

        jLabel28.setText("Durchschnitt:");
        Statistik_Schrittlänge.add(jLabel28);

        Statistik_av_Schrittlänge.setText("---");
        Statistik_Schrittlänge.add(Statistik_av_Schrittlänge);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Schrittlänge, gridBagConstraints);

        Statistik_Training.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Training", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Training.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        jLabel29.setText("Belastung:");
        Statistik_Training.add(jLabel29);

        Statistik_Belastung.setText("---");
        Statistik_Training.add(Statistik_Belastung);

        jLabel30.setText("Erholungszeit:");
        Statistik_Training.add(jLabel30);

        Statistik_Erholungszeit.setText("---");
        Statistik_Training.add(Statistik_Erholungszeit);

        jLabel35.setText("Lauf-Index:");
        Statistik_Training.add(jLabel35);

        Statistik_Lauf_Index.setText("---");
        Statistik_Training.add(Statistik_Lauf_Index);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Training, gridBagConstraints);

        Statistik_Kalorien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kalorien", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        Statistik_Kalorien.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        jLabel31.setText("kCal");
        Statistik_Kalorien.add(jLabel31);

        Statistik_Kalorien_absolut.setText("---");
        Statistik_Kalorien.add(Statistik_Kalorien_absolut);

        jLabel34.setText("kCal/h");
        Statistik_Kalorien.add(jLabel34);

        Statistik_Kalorien_h.setText("---");
        Statistik_Kalorien.add(Statistik_Kalorien_h);

        jLabel32.setText("Fett [%]");
        Statistik_Kalorien.add(jLabel32);

        Statistik_Fett.setText("---");
        Statistik_Kalorien.add(Statistik_Fett);

        jLabel33.setText("Protein [%]");
        Statistik_Kalorien.add(jLabel33);

        Statistik_Protein.setText("---");
        Statistik_Kalorien.add(Statistik_Protein);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Statistik_Kalorien, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Statistik_Panel.add(jPanel2, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 100.0;
        Statistik_Panel.add(jPanel3, gridBagConstraints);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 100.0;
        Statistik_Panel.add(jPanel4, gridBagConstraints);

        Statistik_Titel.setText("jLabel26");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Statistik_Panel.add(Statistik_Titel, gridBagConstraints);

        Hauptfenster.addTab("Statistik", null, Statistik_Panel, "");

        Graphik_Panel.setMinimumSize(new java.awt.Dimension(22, 22));
        Graphik_Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Graphik_PanelComponentShown(evt);
            }
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                Graphik_PanelComponentHidden(evt);
            }
        });
        java.awt.GridBagLayout Graphik_PanelLayout = new java.awt.GridBagLayout();
        Graphik_PanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        Graphik_PanelLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        Graphik_Panel.setLayout(Graphik_PanelLayout);

        Auswahl_Graphik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_GraphikActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Graphik_Panel.add(Auswahl_Graphik, gridBagConstraints);

        Graphik_Sub_Panel.setMinimumSize(new java.awt.Dimension(0, 0));
        Graphik_Sub_Panel.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout Graphik_Sub_PanelLayout = new javax.swing.GroupLayout(Graphik_Sub_Panel);
        Graphik_Sub_Panel.setLayout(Graphik_Sub_PanelLayout);
        Graphik_Sub_PanelLayout.setHorizontalGroup(
            Graphik_Sub_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Graphik_Sub_PanelLayout.setVerticalGroup(
            Graphik_Sub_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Graphik_Panel.add(Graphik_Sub_Panel, gridBagConstraints);

        Graphik_check_Geschwindigkeit.setText("Geschwindigkeit");
        Graphik_check_Geschwindigkeit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_GeschwindigkeitActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Geschwindigkeit, gridBagConstraints);

        Graphik_check_Höhe.setText("Höhe");
        Graphik_check_Höhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_HöheActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Höhe, gridBagConstraints);

        Graphik_check_HF.setText("Herzfrequenz");
        Graphik_check_HF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_HFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_HF, gridBagConstraints);

        Graphik_check_Temp.setText("Temperatur");
        Graphik_check_Temp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_TempActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Temp, gridBagConstraints);

        Graphik_check_Steigung_p.setText("Steigung [%]");
        Graphik_check_Steigung_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_Steigung_pActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Steigung_p, gridBagConstraints);

        Graphik_check_Steigung_m.setText("Steigung [m/min]");
        Graphik_check_Steigung_m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_Steigung_mActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Steigung_m, gridBagConstraints);

        Graphik_check_Cadence.setText("Cadence");
        Graphik_check_Cadence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_CadenceActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Cadence, gridBagConstraints);

        Graphik_Radio_Strecke.setSelected(true);
        Graphik_Radio_Strecke.setText("über Strecke");
        Graphik_Radio_Strecke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_Radio_StreckeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        Graphik_Panel.add(Graphik_Radio_Strecke, gridBagConstraints);

        Graphik_Radio_Zeit.setText("über Zeit");
        Graphik_Radio_Zeit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_Radio_ZeitActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        Graphik_Panel.add(Graphik_Radio_Zeit, gridBagConstraints);

        Graphik_check_Abstand.setText("Zeit- / Streckenabstand");
        Graphik_check_Abstand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_AbstandActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Abstand, gridBagConstraints);

        Graphik_check_av_Geschw.setText("av-Geschw.");
        Graphik_check_av_Geschw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_av_GeschwActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_av_Geschw, gridBagConstraints);

        Graphik_check_Schrittlänge.setText("Schrittlänge");
        Graphik_check_Schrittlänge.setToolTipText("");
        Graphik_check_Schrittlänge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graphik_check_SchrittlängeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        Graphik_Panel.add(Graphik_check_Schrittlänge, gridBagConstraints);

        Hauptfenster.addTab("Graphik", null, Graphik_Panel, "");

        Histogramm_Panel.setMinimumSize(new java.awt.Dimension(22, 22));
        Histogramm_Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Histogramm_PanelComponentShown(evt);
            }
        });
        java.awt.GridBagLayout Histogramm_PanelLayout = new java.awt.GridBagLayout();
        Histogramm_PanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0};
        Histogramm_PanelLayout.rowHeights = new int[] {0, 0, 0, 0, 0};
        Histogramm_Panel.setLayout(Histogramm_PanelLayout);

        Auswahl_Histogramm.setAlignmentX(0.0F);
        Auswahl_Histogramm.setAlignmentY(0.0F);
        Auswahl_Histogramm.setMinimumSize(new java.awt.Dimension(200, 20));
        Auswahl_Histogramm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_HistogrammActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Histogramm_Panel.add(Auswahl_Histogramm, gridBagConstraints);

        Summenhistogramm_Check.setText("Summenhistogramme");
        Summenhistogramm_Check.setAlignmentY(0.0F);
        Summenhistogramm_Check.setMaximumSize(new java.awt.Dimension(32767, 32767));
        Summenhistogramm_Check.setMinimumSize(new java.awt.Dimension(300, 23));
        Summenhistogramm_Check.setOpaque(false);
        Summenhistogramm_Check.setPreferredSize(new java.awt.Dimension(300, 23));
        Summenhistogramm_Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Summenhistogramm_CheckActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Histogramm_Panel.add(Summenhistogramm_Check, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel18_HistoSP.setAlignmentX(0.0F);
        jPanel18_HistoSP.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel18_HistoSPLayout = new javax.swing.GroupLayout(jPanel18_HistoSP);
        jPanel18_HistoSP.setLayout(jPanel18_HistoSPLayout);
        jPanel18_HistoSPLayout.setHorizontalGroup(
            jPanel18_HistoSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18_HistoSPLayout.setVerticalGroup(
            jPanel18_HistoSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanel1.add(jPanel18_HistoSP, gridBagConstraints);

        jPanel17_HistoHM.setAlignmentX(0.0F);
        jPanel17_HistoHM.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel17_HistoHMLayout = new javax.swing.GroupLayout(jPanel17_HistoHM);
        jPanel17_HistoHM.setLayout(jPanel17_HistoHMLayout);
        jPanel17_HistoHMLayout.setHorizontalGroup(
            jPanel17_HistoHMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17_HistoHMLayout.setVerticalGroup(
            jPanel17_HistoHMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanel1.add(jPanel17_HistoHM, gridBagConstraints);

        jPanel16_HistoHF.setAlignmentX(0.0F);
        jPanel16_HistoHF.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel16_HistoHFLayout = new javax.swing.GroupLayout(jPanel16_HistoHF);
        jPanel16_HistoHF.setLayout(jPanel16_HistoHFLayout);
        jPanel16_HistoHFLayout.setHorizontalGroup(
            jPanel16_HistoHFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel16_HistoHFLayout.setVerticalGroup(
            jPanel16_HistoHFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanel1.add(jPanel16_HistoHF, gridBagConstraints);

        jPanel19_HistoCd.setAlignmentX(0.0F);
        jPanel19_HistoCd.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel19_HistoCdLayout = new javax.swing.GroupLayout(jPanel19_HistoCd);
        jPanel19_HistoCd.setLayout(jPanel19_HistoCdLayout);
        jPanel19_HistoCdLayout.setHorizontalGroup(
            jPanel19_HistoCdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19_HistoCdLayout.setVerticalGroup(
            jPanel19_HistoCdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        jPanel1.add(jPanel19_HistoCd, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Histogramm_Panel.add(jPanel1, gridBagConstraints);

        jLabel26_Histotitel.setText("jLabel26");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        Histogramm_Panel.add(jLabel26_Histotitel, gridBagConstraints);

        Hauptfenster.addTab("Histogramme", null, Histogramm_Panel, "");

        Map_Panel.setPreferredSize(new java.awt.Dimension(594, 400));
        Map_Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Map_PanelComponentShown(evt);
            }
        });
        java.awt.GridBagLayout Map_PanelLayout = new java.awt.GridBagLayout();
        Map_PanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        Map_PanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        Map_Panel.setLayout(Map_PanelLayout);

        Auswahl_Map.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_MapActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Map_Panel.add(Auswahl_Map, gridBagConstraints);

        LoadGoogleEarth.setText("Google Earth");
        LoadGoogleEarth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadGoogleEarthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Map_Panel.add(LoadGoogleEarth, gridBagConstraints);

        Kein_kmz_text.setText("Kein Log File");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Map_Panel.add(Kein_kmz_text, gridBagConstraints);

        Map_internal_Panel.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Map_Panel.add(Map_internal_Panel, gridBagConstraints);

        jLabel_map_streckenlänge.setText("jLabel26");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        Map_Panel.add(jLabel_map_streckenlänge, gridBagConstraints);

        jLabel27.setText("GPS Länge:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        Map_Panel.add(jLabel27, gridBagConstraints);

        Map_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Map_Type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Map_TypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Map_Panel.add(Map_Type, gridBagConstraints);

        Hauptfenster.addTab("Landkarte", Map_Panel);

        Jahresuebersicht_Panel.setPreferredSize(new java.awt.Dimension(688, 400));
        Jahresuebersicht_Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Jahresuebersicht_PanelComponentShown(evt);
            }
        });
        java.awt.GridBagLayout Jahresuebersicht_PanelLayout = new java.awt.GridBagLayout();
        Jahresuebersicht_PanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        Jahresuebersicht_PanelLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Jahresuebersicht_Panel.setLayout(Jahresuebersicht_PanelLayout);

        Auswahl_Übersicht.setMinimumSize(new java.awt.Dimension(200, 20));
        Auswahl_Übersicht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_ÜbersichtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Jahresuebersicht_Panel.add(Auswahl_Übersicht, gridBagConstraints);

        JahrVergleich.setMinimumSize(new java.awt.Dimension(200, 20));
        JahrVergleich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JahrVergleichActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Jahresuebersicht_Panel.add(JahrVergleich, gridBagConstraints);

        jLabel67.setText("Vergleich mit Jahr:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Jahresuebersicht_Panel.add(jLabel67, gridBagConstraints);

        jLabel70.setText("   Jahr:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Jahresuebersicht_Panel.add(jLabel70, gridBagConstraints);

        jRadioButton_jahresverlauf.setSelected(true);
        jRadioButton_jahresverlauf.setText("Jahresverlauf");
        jRadioButton_jahresverlauf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_jahresverlaufActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Jahresuebersicht_Panel.add(jRadioButton_jahresverlauf, gridBagConstraints);

        jRadioButton_monatsübersicht.setText("Monatsübersicht");
        jRadioButton_monatsübersicht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_monatsübersichtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        Jahresuebersicht_Panel.add(jRadioButton_monatsübersicht, gridBagConstraints);

        jPanel17Übersichtchart.setLayout(new javax.swing.BoxLayout(jPanel17Übersichtchart, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Jahresuebersicht_Panel.add(jPanel17Übersichtchart, gridBagConstraints);

        Hauptfenster.addTab("Jahresübersicht", null, Jahresuebersicht_Panel, "");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(Hauptfenster, gridBagConstraints);

        jMenuDatei.setLabel("Datei     ");

        jMenuOpen.setText("Rohdaten Importieren");
        jMenuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpenActionPerformed(evt);
            }
        });
        jMenuDatei.add(jMenuOpen);

        jMenuOpenall.setText("Alle Rohdaten Importieren");
        jMenuOpenall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpenallActionPerformed(evt);
            }
        });
        jMenuDatei.add(jMenuOpenall);
        jMenuDatei.add(jSeparator1);

        jMenuLöschen.setText("Löschen");
        jMenuLöschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLöschenActionPerformed(evt);
            }
        });
        jMenuDatei.add(jMenuLöschen);

        jMenuExit.setText("Beenden");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        jMenuDatei.add(jMenuExit);

        jMenuHaupt.add(jMenuDatei);

        jMenu_V800_Laden.setText("Daten Empfangen     ");
        jMenu_V800_Laden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu_V800_LadenMouseClicked(evt);
            }
        });
        jMenuHaupt.add(jMenu_V800_Laden);

        jMenuTourEditor.setLabel("Tour Editor     ");
        jMenuTourEditor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuTourEditorMouseClicked(evt);
            }
        });
        jMenuHaupt.add(jMenuTourEditor);
        jMenuTourEditor.getAccessibleContext().setAccessibleName("Tour Editor");

        jMenuEinstellungen.setLabel("Einstellungen     ");
        jMenuEinstellungen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuEinstellungenMouseClicked(evt);
            }
        });
        jMenuHaupt.add(jMenuEinstellungen);

        jMenuHilfe.setText("Hilfe");
        jMenuHilfe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuHilfeMouseClicked(evt);
            }
        });
        jMenuHaupt.add(jMenuHilfe);

        setJMenuBar(jMenuHaupt);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton_monatsübersichtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_monatsübersichtActionPerformed
        // TODO add your handling code here:
        if (Update == false) {
            return;
        }
        if (Uebersicht == null) {
            return;
        }
        UpdateJahresuebersicht();
    }//GEN-LAST:event_jRadioButton_monatsübersichtActionPerformed

    private void jRadioButton_jahresverlaufActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_jahresverlaufActionPerformed
        // TODO add your handling code here:
        if (Update == false) {
            return;
        }
        if (Uebersicht == null) {
            return;
        }
        UpdateJahresuebersicht();
    }//GEN-LAST:event_jRadioButton_jahresverlaufActionPerformed

    private void jMenuHilfeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuHilfeMouseClicked
        // TODO add your handling code here:
        Hilfe help = new Hilfe(this);


    }//GEN-LAST:event_jMenuHilfeMouseClicked

    private void Datenliste_searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Datenliste_searchButtonActionPerformed
        // TODO add your handling code here:
        if (Update == false) {
            return;
        }
        int i;
        String Text;
        SelectionChanged = true;
        String dummy;
        Text = Datenliste_search.getText();
        Boolean invers = false;
        if (Text.charAt(0) == '~') {
            invers = true;
            Text = Text.substring(1);
        }
        for (i = 0; i < Datentabelle.getRowCount(); i++) {
            dummy = (String) (Datentabelle.getValueAt(i, 4));

            if (dummy.indexOf(Text) != -1) {
                if (invers) {
                    Datentabelle.removeRowSelectionInterval(i, i);
                } else {
                    Datentabelle.addRowSelectionInterval(i, i);
                }
            }
        }

        jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());
    }//GEN-LAST:event_Datenliste_searchButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // Add your handling code here:
        repaint();
    }//GEN-LAST:event_formComponentResized

    private void JahrVergleichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JahrVergleichActionPerformed
        // Add your handling code here:
        if (Update == false) {
            return;
        }
        if (Uebersicht == null) {
            return;
        }
        UpdateJahresuebersicht();

    }//GEN-LAST:event_JahrVergleichActionPerformed

    private void Jahresuebersicht_PanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Jahresuebersicht_PanelComponentShown
        // Add your handling code here:

        if (Uebersicht == null) {
            Uebersicht = new JUebersicht(this);
        }
        //        Uebersicht.Update_Uebersicht(this);
        UpdateJahresuebersicht();



    }//GEN-LAST:event_Jahresuebersicht_PanelComponentShown

    private void Auswahl_ÜbersichtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_ÜbersichtActionPerformed
        // Add your handling code here:
        if (Update == false) {
            return;
        }
        if (Uebersicht == null) {
            return;
        }
        UpdateJahresuebersicht();


    }//GEN-LAST:event_Auswahl_ÜbersichtActionPerformed

    private void Summenhistogramm_CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Summenhistogramm_CheckActionPerformed
        // Add your handling code here:

        if (!Summenhistogramm_Check.isEnabled()) {
            return;
        }
        if (Summenhistogramm_Check.isSelected()) {
            Properties.setProperty("SummenHisto", "1");
        } else {
            Properties.setProperty("SummenHisto", "0");
        }


        UpdateHistogram();



    }//GEN-LAST:event_Summenhistogramm_CheckActionPerformed

    private void Datenliste_TourTypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Datenliste_TourTypActionPerformed
        // Add your handling code here:
      
        if (Update == false) {
            return;
        }
        SelectionChanged = true;
        if (Datenliste_Zeitabschnitt.getSelectedIndex() != 0) {
            Datenliste_ZeitabschnittActionPerformed(evt);
        } else if (Datenliste_Monat.isEnabled()) {
            Datenliste_MonatActionPerformed(evt);
        } else if (Datenliste_Jahr.getSelectedIndex() != 0) {
            Datenliste_JahrActionPerformed(evt);
        } else {
            for (int i = 0; i < Datentabelle.getRowCount(); i++) {

                if (Datentabelle.isRowSelected(i)
                        & (Datenliste_TourTyp.getSelectedItem().toString().equals(sorter.getValueAt(i, 6).toString())
                        || Datenliste_TourTyp.getSelectedIndex() == 0)) {
                    Datentabelle.addRowSelectionInterval(i, i);
                } else {
                    Datentabelle.removeRowSelectionInterval(i, i);
                }
            }
            jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());

        }
    }//GEN-LAST:event_Datenliste_TourTypActionPerformed

    private void Datenliste_ZeitabschnittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Datenliste_ZeitabschnittActionPerformed
        // Add your handling code here:


        if (Update == false) {
            return;
        }
        int Tag, Monat, Jahr;
        int i, Zeitdifferenz = 0;
        String dummy;
        java.util.GregorianCalendar Kalendera;
        java.util.GregorianCalendar Kalenderb;

        SelectionChanged = true;
        Update = false;
        Datenliste_Monat.setEnabled(false);
        if (Datenliste_Monat.getItemCount()!=0) Datenliste_Monat.setSelectedIndex(0);
        if(Datenliste_Jahr.getItemCount()!=0) Datenliste_Jahr.setSelectedIndex(0);
        Update = true;
        Datentabelle.clearSelection();

        if (Datenliste_Zeitabschnitt.getSelectedIndex() == 0) {
            //           jTable1.addRowSelectionInterval(0,0);
            return;
        }
        if (Datenliste_Zeitabschnitt.getSelectedIndex() == 1) {
            Zeitdifferenz = 8;
        }
        if (Datenliste_Zeitabschnitt.getSelectedIndex() == 2) {
            Zeitdifferenz = 31;
        }
        if (Datenliste_Zeitabschnitt.getSelectedIndex() == 3) {
            Zeitdifferenz = 366;
        }
        if (Datenliste_Zeitabschnitt.getSelectedIndex() == 4) {
            Zeitdifferenz = -1;
        }

        Kalendera = (java.util.GregorianCalendar) java.util.GregorianCalendar.getInstance();
        Kalenderb = (java.util.GregorianCalendar) java.util.GregorianCalendar.getInstance();
        if (Zeitdifferenz != -1) {
            Kalendera.add(Calendar.DATE, -Zeitdifferenz);
        }
        for (i = 0; i < Datentabelle.getRowCount(); i++) {
            dummy = (String) (Datentabelle.getValueAt(i, 0));
            Jahr = Integer.parseInt(dummy.substring(8));
            Monat = Integer.parseInt(dummy.substring(5, 7));
            Tag = Integer.parseInt(dummy.substring(2, 4));
            Kalenderb.set(Jahr, Monat - 1, Tag - 1);
            if ((Kalendera.before(Kalenderb) || Zeitdifferenz == -1)
                    & (Datenliste_TourTyp.getSelectedItem().toString().equals(sorter.getValueAt(i, 6).toString())
                    || Datenliste_TourTyp.getSelectedIndex() == 0)) {
                Datentabelle.addRowSelectionInterval(i, i);
            }
            jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());

        }

    }//GEN-LAST:event_Datenliste_ZeitabschnittActionPerformed

  private void Graphik_check_AbstandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_AbstandActionPerformed
      // Add your handling code here:
      if (Graphik_check_Abstand.isSelected()) {
          Properties.setProperty("ZeitStreckeAbstände", "1");
      } else {
          Properties.setProperty("ZeitStreckeAbstände", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();

  }//GEN-LAST:event_Graphik_check_AbstandActionPerformed

  private void Info_Button_speichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Info_Button_speichernActionPerformed
      // Add your handling code here:
      String dummy;
      StringTokenizer st;

      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Titel", Info_Titel.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Notiz", Info_Notiz.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Startort", Info_Startort.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Zielort", Info_Zielort.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Vorname", Info_Vorname.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Name", Info_Name.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Geburtsdatum", Info_GebTag.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Gewicht", Info_Gewicht.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Verein", Info_Verein.getText());
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Materialgewicht", Info_Materialgewicht.getText());//Materialgewicht
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("GoogleEarth", Info_Track_Log.getText());//Google Earth Datei
      Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Material", Info_Material.getText());//Materieal
 //     int AnzahlMarken = Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].AnzahlMarken;
//      if (AnzahlMarken != 0) {
//          for (int i = 0; i < AnzahlMarken; i++) {
//              if (ToSec(Info_MarkenZeit.getItemAt(i).toString()) == -1) {
//                  JOptionPane.showMessageDialog(null, "Fehler in der Zeitangabe der Markierung " + (i + 1), "Achtung!", JOptionPane.ERROR_MESSAGE);
//                  Info_MarkenZeit.setSelectedIndex(i);
//                  return;
//              }
//          }
//          for (int i = 1; i < AnzahlMarken; i++) {
//
//              if (ToSec(Info_MarkenZeit.getItemAt(i).toString())
//                      < ToSec(Info_MarkenZeit.getItemAt(i - 1).toString())) {
//                  int Meldung = JOptionPane.showConfirmDialog(null, "Fehler in der Zeitangabe der Markierung " + (i + 1) + "\nDie Zeitpunkte der Marken müssen aufsteigend sein!\n\nSollen die Marken sortiert werden?", "Achtung!", JOptionPane.OK_CANCEL_OPTION);
//                  if (Meldung == JOptionPane.OK_OPTION) {
//                      SortMarken(Info_MarkenZeit);
//                  } else {
//                      //                    JOptionPane.showMessageDialog(null,"Fehler in der Zeitangabe der Markierung " + (i+1) +"\n Die Zeitpunkte der Marken müssen aufsteigend sein!","Achtung!", JOptionPane.ERROR_MESSAGE);
//                      Info_MarkenZeit.setSelectedIndex(i);
//                      return;
//
//                  }
//              }
//          }
//
//          for (int i = 1; i < AnzahlMarken; i++) {
//              dummy = Info_MarkenZeit.getItemAt(i).toString();
//              if (dummy.indexOf(' ') == -1) {
//                  Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty("Marke " + (i + 1), "" + ToSec(dummy)); //kein Marentext vorhanden
//              } else {
//                  Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.setProperty(
//                          "Marke " + (i + 1), "" + ToSec(dummy) + dummy.substring(dummy.indexOf(' '), dummy.length()));
//              }
//          }
//          if (ToSec(Info_MarkenZeit.getItemAt(Info_MarkenZeit.getItemCount() - 1).toString())
//                  > Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].gesammtZeit) {
//              JOptionPane.showMessageDialog(null, "Fehler in der Zeitangabe der Markierung!\n Zeit der Markierung später als letzter Datenpunkt! ", "Achtung!", JOptionPane.ERROR_MESSAGE);
//              return;
//          }
//      }

      try {
          Ausgabedatei = new java.io.FileOutputStream(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].Konfigfile);

          Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                  + Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                  + Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Tag")
                  + Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Stunde")
                  + Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Minute"));
          Ausgabedatei.close();

      } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei", "Achtung!", JOptionPane.ERROR_MESSAGE);
      }
      //     ChangeModel();
      sorter.setValueAt(Info_Titel.getText(), Statistikhandle.Selection[Auswahl_Info.getSelectedIndex() - 1], 3);
      SelectionChanged = true;
  }//GEN-LAST:event_Info_Button_speichernActionPerformed

  private void Info_Button_einfügenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Info_Button_einfügenActionPerformed
      // Add your handling code here:
      Info_Titel.setText(TempProperty.getProperty("Titel"));
      Info_Notiz.setText(TempProperty.getProperty("Notiz"));
      Info_Startort.setText(TempProperty.getProperty("Startort"));
      Info_Zielort.setText(TempProperty.getProperty("Zielort"));
      Info_Vorname.setText(TempProperty.getProperty("Vorname"));
      Info_Name.setText(TempProperty.getProperty("Name"));
      Info_GebTag.setText(TempProperty.getProperty("GebTag"));
      Info_Gewicht.setText(TempProperty.getProperty("Gewicht"));
      Info_Verein.setText(TempProperty.getProperty("Verein"));
      Info_Materialgewicht.setText(TempProperty.getProperty("Materialgewicht"));//Materialgewicht
      Info_Material.setText(TempProperty.getProperty("Material"));//Materieal
      Info_Track_Log.setText(TempProperty.getProperty("GoogleEarth"));

  }//GEN-LAST:event_Info_Button_einfügenActionPerformed

  private void Info_Button_kopierenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Info_Button_kopierenActionPerformed
      // Add your handling code here:
      TempProperty = new java.util.Properties();
      TempProperty.setProperty("Titel", Info_Titel.getText());
      TempProperty.setProperty("Notiz", Info_Notiz.getText());
      TempProperty.setProperty("Startort", Info_Startort.getText());
      TempProperty.setProperty("Zielort", Info_Zielort.getText());
      TempProperty.setProperty("Vorname", Info_Vorname.getText());
      TempProperty.setProperty("Name", Info_Name.getText());
      TempProperty.setProperty("GebTag", Info_GebTag.getText());
      TempProperty.setProperty("Gewicht", Info_Gewicht.getText());
      TempProperty.setProperty("Verein", Info_Verein.getText());
      TempProperty.setProperty("Materialgewicht", Info_Materialgewicht.getText());//Materialgewicht
      TempProperty.setProperty("Material", Info_Material.getText());//Materieal
      TempProperty.setProperty("GoogleEarth", Info_Track_Log.getText());

      Info_Button_einfügen.setEnabled(true);

  }//GEN-LAST:event_Info_Button_kopierenActionPerformed

  private void Info_PanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Info_PanelComponentShown
      // Add your handling code here:
      if (SelectionChanged) {
          Statistik_PanelComponentShown_StatistikStarten(evt);
  //        UpdateInfos();
          Update_Graphik_paint = true;
          Update_Map_paint = true;
          alteAuswahl = -9;
      }
          UpdateInfos();

  }//GEN-LAST:event_Info_PanelComponentShown

  private void Auswahl_InfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_InfoActionPerformed
      // Add your handling code here:
      if (Update) {
       
          Update = false;
          Auswahl_Statistik.setSelectedIndex(Auswahl_Info.getSelectedIndex());
          Auswahl_Graphik.setSelectedIndex(Auswahl_Info.getSelectedIndex());
          Auswahl_Histogramm.setSelectedIndex(Auswahl_Info.getSelectedIndex());
          Auswahl_Map.setSelectedIndex(Auswahl_Info.getSelectedIndex());

          Update = true;
          Update_Graphik_paint = true;
          Update_Map_paint = true;
          Update_Info = true;
          UpdateInfos();
      }

  }//GEN-LAST:event_Auswahl_InfoActionPerformed

  private void Histogramm_PanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Histogramm_PanelComponentShown
      // Add your handling code here:
      setCursor(new Cursor(Cursor.WAIT_CURSOR)); 
      if (SelectionChanged) {
          Statistik_PanelComponentShown_StatistikStarten(evt);
          Update_Graphik_paint = true;
          Update_Map_paint = true;
          alteAuswahl = -2;
      }

      if (Auswahl_Histogramm.getSelectedIndex() == 0) {
          Summenhistogramm_Check.setEnabled(true);
      } else {
          Summenhistogramm_Check.setEnabled(false);
      }

      UpdateHistogram();
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

  }//GEN-LAST:event_Histogramm_PanelComponentShown

  private void Auswahl_HistogrammActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_HistogrammActionPerformed
      // Add your handling code here:
      if (Update) {
          //          setStatistik(Statistikhandle,jComboBox1.getSelectedIndex());

          Update = false;
          Auswahl_Statistik.setSelectedIndex(Auswahl_Histogramm.getSelectedIndex());
          Auswahl_Graphik.setSelectedIndex(Auswahl_Histogramm.getSelectedIndex());
          Auswahl_Info.setSelectedIndex(Auswahl_Histogramm.getSelectedIndex());
          Auswahl_Map.setSelectedIndex(Auswahl_Histogramm.getSelectedIndex());

          Update_Graphik_paint = true;
          Update_Map_paint = true;
          Update_Info = true;
          Update = true;
          UpdateHistogram();
      }
      if (Auswahl_Histogramm.getSelectedIndex() == 0) {
          Summenhistogramm_Check.setEnabled(true);
      } else {
          Summenhistogramm_Check.setEnabled(false);
      }


  }//GEN-LAST:event_Auswahl_HistogrammActionPerformed

  private void Graphik_Radio_ZeitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_Radio_ZeitActionPerformed
      // Add your handling code here:
     graph_min = 0;
     graph_max = 999999999;//  zoom zurücksetzen
     nozoom = true;
     Properties.setProperty("xy_Strecke", "0");
     Update_XYGraphik();
  }//GEN-LAST:event_Graphik_Radio_ZeitActionPerformed

  private void Graphik_Radio_StreckeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_Radio_StreckeActionPerformed
      // Add your handling code here:
        graph_min = 0;
        graph_max = 999999999;//  zoom zurücksetzen
        nozoom = true;
        Properties.setProperty("xy_Strecke", "1");
     Update_XYGraphik();
  }//GEN-LAST:event_Graphik_Radio_StreckeActionPerformed

  private void Graphik_check_CadenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_CadenceActionPerformed
      // Add your handling code here:
      if (Graphik_check_Cadence.isSelected()) {
          Properties.setProperty("View Cadence", "1");
      } else {
          Properties.setProperty("View Cadence", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_CadenceActionPerformed

  private void Graphik_check_av_GeschwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_av_GeschwActionPerformed
      // Add your handling code here:
      if (Graphik_check_av_Geschw.isSelected()) {
          Properties.setProperty("View av_Geschw", "1");
      } else {
          Properties.setProperty("View av_Geschw", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_av_GeschwActionPerformed

  private void Graphik_check_Steigung_mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_Steigung_mActionPerformed
      // Add your handling code here:
      if (Graphik_check_Steigung_m.isSelected()) {
          Properties.setProperty("View Steigm", "1");
      } else {
          Properties.setProperty("View Steigm", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_Steigung_mActionPerformed

  private void Graphik_check_Steigung_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_Steigung_pActionPerformed
      // Add your handling code here:
      if (Graphik_check_Steigung_p.isSelected()) {
          Properties.setProperty("View Steigp", "1");
      } else {
          Properties.setProperty("View Steigp", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_Steigung_pActionPerformed

  private void Graphik_check_TempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_TempActionPerformed
      // Add your handling code here:
      if (Graphik_check_Temp.isSelected()) {
          Properties.setProperty("View Temp", "1");
      } else {
          Properties.setProperty("View Temp", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_TempActionPerformed

  private void Graphik_check_HFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_HFActionPerformed
      // Add your handling code here:
      if (Graphik_check_HF.isSelected()) {
          Properties.setProperty("View Hf", "1");
      } else {
          Properties.setProperty("View Hf", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_HFActionPerformed

  private void Graphik_check_HöheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_HöheActionPerformed
      // Add your handling code here:
      if (Graphik_check_Höhe.isSelected()) {
          Properties.setProperty("View Höhe", "1");
      } else {
          Properties.setProperty("View Höhe", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();
  }//GEN-LAST:event_Graphik_check_HöheActionPerformed

  private void Graphik_check_GeschwindigkeitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_GeschwindigkeitActionPerformed
      // Add your handling code here:
      if (Graphik_check_Geschwindigkeit.isSelected()) {
          Properties.setProperty("View Geschw", "1");
      } else {
          Properties.setProperty("View Geschw", "0");
      }
      Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
      Update_XYGraphik();

  }//GEN-LAST:event_Graphik_check_GeschwindigkeitActionPerformed

  private void Auswahl_GraphikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_GraphikActionPerformed
      // Add your handling code here:

      if (Update) {
          Update = false;
          Auswahl_Statistik.setSelectedIndex(Auswahl_Graphik.getSelectedIndex());
          Auswahl_Histogramm.setSelectedIndex(Auswahl_Graphik.getSelectedIndex());
          Auswahl_Info.setSelectedIndex(Auswahl_Graphik.getSelectedIndex());
          Auswahl_Map.setSelectedIndex(Auswahl_Graphik.getSelectedIndex());

          Update_Graphik_paint = true;
          Update_Map_paint = true;
          Update_Info = true;
          Update = true;
          if (alteAuswahl >= 0) {
              Save_Min_Max(alteAuswahl);
          }
int i = 1;
          alteAuswahl = Auswahl_Graphik.getSelectedIndex();
          Update_XYGraphik();
      }

  }//GEN-LAST:event_Auswahl_GraphikActionPerformed

  private void Graphik_PanelComponentShown (java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Graphik_PanelComponentShown
      // Add your handling code here:

      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      if (SelectionChanged) {
          Statistik_PanelComponentShown_StatistikStarten(evt);
          Update_XYGraphik();

      }
      if (Update_Graphik_paint == true) {
          Update_XYGraphik();
      }

      Update_Map_paint = true;
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 

  }//GEN-LAST:event_Graphik_PanelComponentShown

  private void Datenliste_MonatActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Datenliste_MonatActionPerformed
      // Add your handling code here:
      //     if(jComboBox3Monat.getSelectedItem().toString().equals("alle")) jComboBox2JahrActionPerformed(evt);

      if (Update == false) {
          return;
      }
      int i;
      String Jahr;
      String Monat;
      String dummy;
      Datentabelle.clearSelection();
      SelectionChanged = true;

      for (i = 0; i < Datentabelle.getRowCount(); i++) {
          dummy = (String) (Datentabelle.getValueAt(i, 0));
          Jahr = dummy.substring(8);
          Monat = dummy.substring(5, 7);
          if (Jahr.equals(Datenliste_Jahr.getSelectedItem().toString())
                  & (Datenliste_Monat.getSelectedIndex() == Integer.parseInt(Monat)
                  || Datenliste_Monat.getSelectedItem().toString().equals("alle"))
                  & (Datenliste_TourTyp.getSelectedItem().toString().equals(sorter.getValueAt(i, 6).toString())
                  || Datenliste_TourTyp.getSelectedIndex() == 0)) {
              Datentabelle.addRowSelectionInterval(i, i);
          }
          jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());
      }
  }//GEN-LAST:event_Datenliste_MonatActionPerformed

  private void Datenliste_JahrActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Datenliste_JahrActionPerformed
      // Add your handling code here:

      if (Update == false) {
          return;
      }
      if (Datenliste_Jahr.getSelectedItem().toString().equals("nicht aktiv")) {
          Update = false;
          Datenliste_Monat.setSelectedIndex(0);
          Datenliste_Monat.setEnabled(false);
          Update = true;
          //          return;
      } else {
          Datenliste_Monat.setEnabled(true);
      }

      int i;
      String Jahr;
      String Monat;
      String dummy;
      SelectionChanged = true;
      Update = false;
      Datenliste_Monat.setSelectedIndex(0);

      Datenliste_Zeitabschnitt.setSelectedIndex(0);
      Update = true;
      Datentabelle.clearSelection();
      if (Datenliste_Jahr.getSelectedItem().toString().equals("nicht aktiv")) {
          return;
      }
      for (i = 0; i < Datentabelle.getRowCount(); i++) {
          dummy = (String) (Datentabelle.getValueAt(i, 0));
          Jahr = dummy.substring(8);
          Monat = dummy.substring(5, 7);
          if (Jahr.equals(Datenliste_Jahr.getSelectedItem().toString())
                  & (Datenliste_TourTyp.getSelectedItem().toString().equals(sorter.getValueAt(i, 6).toString())
                  || Datenliste_TourTyp.getSelectedIndex() == 0)) {
              Datentabelle.addRowSelectionInterval(i, i);
          }
          jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());

      }
  }//GEN-LAST:event_Datenliste_JahrActionPerformed

    private void InitComboMonat() {
        Datenliste_Monat.addItem("alle");
        Datenliste_Monat.addItem("Januar");
        Datenliste_Monat.addItem("Februar");
        Datenliste_Monat.addItem("März");
        Datenliste_Monat.addItem("April");
        Datenliste_Monat.addItem("Mai");
        Datenliste_Monat.addItem("Juni");
        Datenliste_Monat.addItem("Juli");
        Datenliste_Monat.addItem("August");
        Datenliste_Monat.addItem("September");
        Datenliste_Monat.addItem("Oktober");
        Datenliste_Monat.addItem("November");
        Datenliste_Monat.addItem("Dezember");
    }

    private void InitComboJahr() {
        Datenliste_Jahr.addItem("nicht aktiv");
        int i, j;
        String dummy;
        boolean vorhanden = false;
        JahrVergleich.addItem("keinem Jahr");
        for (i = 0; i < Datentabelle.getRowCount(); i++) {
            //         dummy = (String)(jTable1.getValueAt(i,0).toString());
            if (Datentabelle == null) {
                JOptionPane.showMessageDialog(null, "InitComboJahr\njTable1 = null! ", "Achtung!", JOptionPane.ERROR_MESSAGE);
            }

            dummy = (String) Datentabelle.getValueAt(i, 0);
            //         if (dummy == null)          JOptionPane.showMessageDialog(null,"dummy = null! \n  " + jTable1.getValueAt(i,0).toString() ,"Achtung!", JOptionPane.ERROR_MESSAGE);
            try {
                if (dummy.length() > 8 && dummy != null) {
                    dummy = dummy.substring(8);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "InitComboJahr\ndummy = null! \n  " + i, "Achtung!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (j = 0; j < Datenliste_Jahr.getItemCount(); j++) {
                if (Datenliste_Jahr.getItemAt(j).equals(dummy)) {
                    vorhanden = true;
                }
            }
            if (vorhanden == false) {
                Datenliste_Jahr.addItem(dummy);
                Auswahl_Übersicht.addItem(dummy);
                JahrVergleich.addItem(dummy);
            }
            vorhanden = false;
        }

    }

    private void InitComboTyp() {
        Datenliste_TourTyp.addItem("Alle");
        int i, j;
        String dummy;
        boolean vorhanden = false;
        for (i = 0; i < Datentabelle.getRowCount(); i++) {
            dummy = (String) (sorter.getValueAt(i, 6));
            try {
                for (j = 0; j < Datenliste_TourTyp.getItemCount(); j++) {
                    if (Datenliste_TourTyp.getItemAt(j).equals(dummy)) {
                        vorhanden = true;
                    }
                }
                if (vorhanden == false && dummy != null) {
                    Datenliste_TourTyp.addItem(dummy);
                }
                vorhanden = false;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "InitComboTyp\ndummy = null! \n  " + i, "Achtung!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

  private void jMenuLöschenActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLöschenActionPerformed
      // Add your handling code here:
      int i;
      int Selection[] = new int[Datentabelle.getSelectedRowCount()];
      Selection = Datentabelle.getSelectedRows();
      if (JOptionPane.showConfirmDialog(null, "Selektierte Files wirklich Löschen?", "Achtung!", JOptionPane.YES_NO_OPTION)
              == JOptionPane.OK_OPTION) {
          for (i = 0; i < Datentabelle.getSelectedRowCount(); i++) {
              DataProperty = new java.util.Properties();
              DataProperty.setProperty("Visible", "0");
              try {
                  FileOutputStream out = new FileOutputStream(sorter.getValueAt(Selection[i], 5) + ".cfg");
                  DataProperty.store(out, "Tour ist gelöscht");
                  out.close();
              } catch (Exception e) {
              };
              //         file = new File(sorter.getValueAt(Selection[i],5) + ".cfg");
              //          file.delete();
              file = new File(sorter.getValueAt(Selection[i], 5) + ".txt");
              file.delete();
          };
          ChangeModel();
      };
  }//GEN-LAST:event_jMenuLöschenActionPerformed

  private void jMenuExitActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
      // Add your handling code here:
      exitForm(null);
  }//GEN-LAST:event_jMenuExitActionPerformed

 
  private void Auswahl_StatistikActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_StatistikActionPerformed
      // Add your handling code here:
     
      if (Update) {
          setStatistik(Statistikhandle, Auswahl_Statistik.getSelectedIndex());

          Update = false;
          Auswahl_Graphik.setSelectedIndex(Auswahl_Statistik.getSelectedIndex());
          Auswahl_Histogramm.setSelectedIndex(Auswahl_Statistik.getSelectedIndex());
          Auswahl_Info.setSelectedIndex(Auswahl_Statistik.getSelectedIndex());
          Auswahl_Map.setSelectedIndex(Auswahl_Statistik.getSelectedIndex());

          Update_Graphik_paint = true;
          Update_Map_paint = true;
          Update_Info = true;
          Update = true;
      }

  }//GEN-LAST:event_Auswahl_StatistikActionPerformed

  private void Statistik_PanelComponentShown_StatistikStarten (java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Statistik_PanelComponentShown_StatistikStarten

      setCursor(new Cursor(Cursor.WAIT_CURSOR)); 
      if (SelectionChanged) {
          clearStatistik();
  
          alteAuswahl = -5;

          Statistikhandle = new JStatistik();
          Statistikhandle.StatistikStart(this);//
          Update_Graphik_paint = true;
          xygraphik = null;
          graph_min = 0;
          graph_max = 999999999;
          graph_crosshair = 0;
          nozoom = true;
   
          Update_Map_paint = true;

      }

      if (Datentabelle.getSelectedRowCount() != 0) {
          if (Statistikhandle != null) {
              Statistikhandle.TourData[0].createSubStatistik(this, graph_min, graph_max);
          }
          setStatistik(Statistikhandle, Auswahl_Statistik.getSelectedIndex());
      } else {
          clearStatistik();
      }
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
  }//GEN-LAST:event_Statistik_PanelComponentShown_StatistikStarten

    public void ChangeModel() {
   
         setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(Hauptfenster !=null)Hauptfenster.setSelectedIndex(0);
        DataProperty = new java.util.Properties();
   
        jTableaccess = Datentabelle;
        String Filename = "";
        String PlaceHolder = "          ";
        File path = new File(Properties.getProperty("data.dir"));
        final String[] names = {"Datum", "Strecke", "Höhenmeter", "Zeit", "Titel"};
 
        String[] list = path.list(new DirFilter("_Tour.cfg"));
   
        RowCount = 0;  
        int Anzahlcfg = 0;
        if (list!=null) Anzahlcfg = list.length;
        if (Anzahlcfg == 0) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            return;
        }


    Object datab[] = new Object[8];
    ArrayList <Object[]> data_list= new ArrayList<Object[]>();
        
        String hmString = "";
        DecimalFormat form = new DecimalFormat("0");//Format ohne Kommastelle
        int j = 0;
        for (int i = 0; i < Anzahlcfg; i++) {   //Überprüfen ob Höhenmeter eingetragen sind - ansonsten ermitteln (gilt für neue Dateien)
            Filename = path.getPath() + SystemProperties.getProperty("file.separator") + list[i];
            DataProperty = new java.util.Properties();
            try {
          
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(Filename));
            DataProperty.load(in);
                in.close();
                // prüfen ob Datei gezeigt werden soll - Visible = 0 wenn Datei gelöscht wurde
                if(!DataProperty.getProperty("Visible","1").equalsIgnoreCase("1"))continue;
                // wenn keine Höhenmeter eingetragen wurden (Erstaufruf) dann Höhenmeter ermitteln
                if (DataProperty.getProperty("Hoehenmeter", "novalue").equalsIgnoreCase("novalue")
                        && DataProperty.getProperty("Visible", "1").equalsIgnoreCase("1")
                        && !DataProperty.getProperty("Jahr", "keinEintrag").equalsIgnoreCase("keinEintrag")) {
                    JTourData Dummydata = new JTourData(Filename.substring(0, Filename.lastIndexOf('.')), this);
                    DataProperty.setProperty("Hoehenmeter",form.format(Dummydata.ges_Hoehep));
                    try {
                        Ausgabedatei = new FileOutputStream(Filename);
                        DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                                + DataProperty.getProperty("Jahr")
                                + DataProperty.getProperty("Monat")
                                + DataProperty.getProperty("Tag")
                                + DataProperty.getProperty("Stunde")
                                + DataProperty.getProperty("Minute"));
                        Ausgabedatei.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, " Fehler bei Speichern der DataProperty in ChangeModel", "Achtung!", JOptionPane.ERROR_MESSAGE);
              
                    }
                }

                try {
                    if (Integer.parseInt(DataProperty.getProperty("Visible", "1")) == 1
                            && !DataProperty.getProperty("Jahr", "keinEintrag").equalsIgnoreCase("keinEintrag")) {
 
                        datab[0] = new String("  " + DataProperty.getProperty("Tag", "11") + "." + DataProperty.getProperty("Monat", "11") + "." + DataProperty.getProperty("Jahr", "1111"));
                        datab[1] = PlaceHolder.substring(0, 9 - DataProperty.getProperty("Strecke", "0").length()) + DataProperty.getProperty("Strecke", "0") + " ";
                        //                      data[j][1] = new String(DataProperty.getProperty("Strecke") + " ");
                        datab[2] = new String("  " + HMS(java.lang.Integer.parseInt(DataProperty.getProperty("Dauer", "0"))));
                        datab[3] = new String(DataProperty.getProperty("Titel", "---"));
                        datab[4] = new String(DataProperty.getProperty("Jahr", "1111") + "." + DataProperty.getProperty("Monat", "11") + "." 
                                        + DataProperty.getProperty("Tag", "11") + "." + DataProperty.getProperty("Stunde","12") + "." + DataProperty.getProperty("Minute","59"));
                        datab[5] = new String(Filename.substring(0, Filename.lastIndexOf('.')));
                        datab[6] = new String(DataProperty.getProperty("Typ", "unbekannt"));
                        hmString = ""+  (int) Float.parseFloat(DataProperty.getProperty("Hoehenmeter", "0"));
                        datab[7] = PlaceHolder.substring(0, 9 - hmString.length()) + hmString   + " ";
                        data_list.add(datab.clone());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Erstellen der Datenliste " + e + " " + j, "Achtung!", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
              System.out.println("NEW IO-Fehler bei " + path.getPath() + SystemProperties.getProperty("file.separator") + list[i] + "\n " + e +"   " + e.getLocalizedMessage() + "--File deleted");
              e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Fehler beim Einlesen eines cfg Files " +path.getPath() + SystemProperties.getProperty("file.separator") + list[i] +"\n  wurde gelöscht!", "Achtung!", JOptionPane.ERROR_MESSAGE);
        File deletefile = new File(path.getPath() + SystemProperties.getProperty("file.separator") + list[i]);
           if(deletefile.exists()) deletefile.delete();
           ChangeModel();
            }
        }

        TableModel dataModel = new AbstractTableModel() {

            public int getColumnCount() {
                return names.length;
            }

            @Override
            public String getColumnName(int column) {
                return names[column];
            }

            @Override
            public int getRowCount() {
                   return data_list.size();
            }

            @Override
            public Object getValueAt(int row, int col) {
    
             Object data[] = new Object[8];
             data = data_list.get(row);
             return data[col];
            }

            @Override
            public void setValueAt(Object Ob, int row, int col) {
                Object data[] = new Object[8];
                data = data_list.get(row);
                data[col]=Ob;
                data_list.set(row, data);

            }
        };
 
        sorter = new TableSorter(dataModel);
        DatumColumn = new TableColumn(0);
        DatumColumn.setHeaderValue(names[0]);
        DatumColumn.setResizable(false);
        StreckeColumn = new TableColumn(1);
        StreckeColumn.setHeaderValue(names[1]);
        StreckeColumn.setResizable(false);
        HoeheColumn = new TableColumn(7);
        HoeheColumn.setHeaderValue(names[2]);
        HoeheColumn.setResizable(false);
        ZeitColumn = new TableColumn(2);
        ZeitColumn.setHeaderValue(names[3]);
        ZeitColumn.setResizable(false);
        NotizColumn = new TableColumn(3);
        NotizColumn.setHeaderValue(names[4]);

        DatumColumn.setMinWidth((int)80*FontSize/12);
        StreckeColumn.setMinWidth((int)65*FontSize/12);
        HoeheColumn.setMinWidth((int)75*FontSize/12);
        ZeitColumn.setMinWidth((int)75*FontSize/12);
        NotizColumn.setMinWidth((int)75*FontSize/12);
        NotizColumn.setPreferredWidth((int)75*FontSize/12+1000);
        DefaultTableCellRenderer TableCell = new DefaultTableCellRenderer();
        TableCell.setHorizontalAlignment(JLabel.CENTER);
        HoeheColumn.setCellRenderer(TableCell);
        StreckeColumn.setCellRenderer(TableCell);
        DatumColumn.setCellRenderer(TableCell);
        ZeitColumn.setCellRenderer(TableCell);
        HoeheColumn.setHeaderRenderer(TableCell);
        StreckeColumn.setHeaderRenderer(TableCell);
        DatumColumn.setHeaderRenderer(TableCell);
        ZeitColumn.setHeaderRenderer(TableCell);
        NotizColumn.setHeaderRenderer(TableCell);

        DefaultTableColumnModel FileTableModel = new DefaultTableColumnModel();
        FileTableModel.addColumn(DatumColumn);
        FileTableModel.addColumn(StreckeColumn);
        FileTableModel.addColumn(HoeheColumn);
        FileTableModel.addColumn(ZeitColumn);
        FileTableModel.addColumn(NotizColumn);
  
        Datentabelle.setModel(sorter);
        Datentabelle.setColumnModel(FileTableModel);
        Datentabelle.setRowHeight(FontSize+5);
        sorter.addMouseListenerToHeaderInTable(Datentabelle);
        sorter.sortByColumn(0, false);
        Datentabelle.clearSelection();
        SelectionChanged = true;
        
        JScrollBar verticaldummy = Datenliste_scroll_Panel.getVerticalScrollBar();
        
        verticaldummy.setPreferredSize(new Dimension(FontSize+10,FontSize+10));
        Datenliste_scroll_Panel.setVerticalScrollBar(verticaldummy);
 
        Update = false;
        Datenliste_Jahr.removeAllItems();
        Datenliste_TourTyp.removeAllItems();
        Auswahl_Übersicht.removeAllItems();
        JahrVergleich.removeAllItems();

        InitComboJahr();
        InitComboTyp();

        Update = true;

        if (Datentabelle.getRowCount() != 0) {
            Datentabelle.addRowSelectionInterval(0, 0);
            Datenliste_scroll_Panel.getViewport().setViewPosition(new java.awt.Point(0, 0));
        }

        if (Uebersicht != null) {
            Uebersicht = null;
        }
        jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());
  
        repaint();
         setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

  private void jMenuOpenallActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpenallActionPerformed
      // Add your handling code here:

      int i;
      StringBuffer Buffer = new StringBuffer();
      String[] liste = new String[1];
      byte Data[] = new byte[81930];
      File path = new File(Properties.getProperty("data.dir"));
      File Datei;

      FileFilter directoryFilter = new FileFilter() {
          public boolean accept(File file) {
              return file.isDirectory();
          }
          public String getDescription(){return "";};
      };
      
      
      chooser.setCurrentDirectory(new java.io.File(Properties.getProperty("import.dir",Properties.getProperty("data.dir"))));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      ExampleFileFilter filtera = new ExampleFileFilter();
      ExampleFileFilter filterb = new ExampleFileFilter();
      ExampleFileFilter filterc = new ExampleFileFilter();
      ExampleFileFilter filterd = new ExampleFileFilter();
      ExampleFileFilter filtere = new ExampleFileFilter();
     
      filtera.addExtension("dat");
      filtera.setDescription("HAC Rohdaten");
      filterb.addExtension("tur");
      filterb.setDescription("Hactronic Dateien");
      filterc.addExtension("hrm");
      filterc.setDescription("Polar Daten");
      filterd.addExtension("");
      filterd.setDescription("Polar V800 Verzeichnis");
      filtere.addExtension("csv");
      filtere.setDescription("Polar V800 CSV Flow export");

      
      chooser.resetChoosableFileFilters();
      chooser.addChoosableFileFilter(filtera);
      chooser.addChoosableFileFilter(filterb);
      chooser.addChoosableFileFilter(filterc);
      chooser.addChoosableFileFilter(filterd);
      chooser.addChoosableFileFilter(filtere);
      chooser.setFileFilter(filtere);

      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
     
 
      int returnVal = chooser.showDialog(this, null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {

          if (chooser.getSelectedFile().getName().endsWith(".dat")) {
              path = new File(chooser.getCurrentDirectory().getPath());
          } else {
              path = new File(chooser.getSelectedFile().getPath());
          }

          if (chooser.getFileFilter().equals(filtera)) {
              liste = path.list(new DirFilter(".dat"));
          }
          if (chooser.getFileFilter().equals(filterb)) {
              liste = path.list(new DirFilter(".tur"));
          }
          if (chooser.getFileFilter().equals(filterc)) {
              liste = path.list(new DirFilter(".hrm"));
          }
         if (chooser.getFileFilter().equals(filtere)) {
              liste = path.list(new DirFilter(".csv"));
          }          
          
          if (chooser.getFileFilter().equals(filterd)) {

              File[] files = path.listFiles();
              ArrayList<String> pathliste = new ArrayList();
              for (File file : files) {
                  try {
                      if (file.isDirectory()) {
                          pathliste.add(file.getCanonicalPath());
                      }
                  } catch (Exception e) {
                  }
              }
 
              Thread thread = new Thread(new Runnable() {

                  public void run() {
                      setCursor(new Cursor(Cursor.WAIT_CURSOR));
                      pm = new ProgressMonitor(
                              JCicloTronic.this, "Importiere...", "", 0, 100);
                      pm.setMillisToPopup(1);

                      v800export V800_export = new v800export();
                      V800_export.export_sessions(JCicloTronic.this, pathliste);
                     pm.close();
                     ChangeModel();
                     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                     
                      try {

                          Thread.sleep(100);
                      } catch (Exception e) {
                          if (Thread.interrupted()) {
                              return;
                          }
                      }
                  }

              });

              thread.start();
         
              return;

          }

//alle außer V800 Dateien importieren
          if (liste == null) {
              JOptionPane.showMessageDialog(null, "Keine Rohdaten-Files gefunden!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          final String[] liste_final = liste.clone();
          final File path_final = path;
         
          Thread thread = new Thread(new Runnable() {

              public void run() {
                  File Datei;
                   byte Data[] = new byte[81930];
                  
                  setCursor(new Cursor(Cursor.WAIT_CURSOR));
                  pm = new ProgressMonitor(
                          JCicloTronic.this, "Importiere...", "", 0, 100);
                  pm.setMillisToPopup(1);

            

                  for (int i = 0; i < liste_final.length; i++) {
                      pm.setProgress((int)100.0*i/liste_final.length);
                      pm.setNote(liste_final[i]);
                      
                      try {
                          Eingabedatei = new java.io.FileInputStream(path_final.getPath() + SystemProperties.getProperty("file.separator") + liste_final[i]);

                          try {
                              Datei = new File(path_final.getPath() + SystemProperties.getProperty("file.separator") + liste_final[i]);
                              Data = new byte[(int) Datei.length()];
                              //                        Eingabedatei.read()

                              Eingabedatei.read(Data);
                              Eingabedatei.close();
                              if (chooser.getFileFilter().equals(filtera)) {
                                  ExtractTour(Data);
                              }
                              if (chooser.getFileFilter().equals(filterb)) {
                                  ExtractHactronicFile(Data);
                              }
                              if (chooser.getFileFilter().equals(filterc)) {
                                  ExtractPolarFile(Data);
                              }
                              if (chooser.getFileFilter().equals(filtere)) {
                                  ExtractCSV(Data);
                              }

                          } catch (IOException e) {
                              JOptionPane.showMessageDialog(null, "IO-Fehler bei Datenlesen", "Achtung!", JOptionPane.ERROR_MESSAGE);
                          }

                      } catch (FileNotFoundException e) {
                               JOptionPane.showMessageDialog(null, "IO-Fehler bei " + path_final.getPath() + SystemProperties.getProperty("file.separator") + liste_final[i], "Achtung!", JOptionPane.ERROR_MESSAGE);
 
                      }

                  }
                   pm.close();
                  JOptionPane.showMessageDialog(null, "Daten  Ende", "Achtung!", JOptionPane.ERROR_MESSAGE);

                  Properties.setProperty("import.dir", chooser.getCurrentDirectory().getPath());

                  ChangeModel();
                  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                  try {

                      Thread.sleep(100);
                  } catch (Exception e) {
                      if (Thread.interrupted()) {
                          return;
                      }
                  }
              }

          });

          thread.start();

      }
  }//GEN-LAST:event_jMenuOpenallActionPerformed

  private void jMenuEinstellungenMouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuEinstellungenMouseClicked
      // Add your handling code here:
      Eigenschaften = new Eigenschaften(new javax.swing.JFrame(), false, this);
//      if (Properties.getProperty("CommPort").equals("nocom")) {
//          jMenuReceive.setEnabled(false);
//      } else {
//          jMenuReceive.setEnabled(true);
//      }
      
      
      FontSize = Integer.parseInt(Properties.getProperty("FontSize","12"));
      Font = Properties.getProperty("Font","Tahoma");
      setFontSizeGlobal(Font, FontSize);
     
      repaint();
  }//GEN-LAST:event_jMenuEinstellungenMouseClicked

  private void jMenuOpenActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpenActionPerformed
      // Add your handling code here:
      byte Data[] = new byte[81930];
      File Datei;
      int i, j;
      chooser.setCurrentDirectory(new java.io.File(Properties.getProperty("import.dir", Properties.getProperty("data.dir"))));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);

      ExampleFileFilter filtera = new ExampleFileFilter();
      ExampleFileFilter filterb = new ExampleFileFilter();
      ExampleFileFilter filterc = new ExampleFileFilter();
      ExampleFileFilter filterd = new ExampleFileFilter();
      ExampleFileFilter filtere = new ExampleFileFilter();

      //    ExampleFileFilter filterc = new ExampleFileFilter();
      filtera.addExtension("dat");
      filtera.setDescription("HAC Rohdaten");
      filterb.addExtension("tur");
      filterb.setDescription("Hactronic Dateien");
      filterc.addExtension("hrm");
      filterc.setDescription("Polar Daten");
      filterd.addExtension("");
      filterd.setDescription("Polar V800 Verzeichnis");
      filtere.addExtension("csv");
      filtere.setDescription("Polar V800 CSV Flow export");

      chooser.resetChoosableFileFilters();
      chooser.addChoosableFileFilter(filtera);
      chooser.addChoosableFileFilter(filterb);
      chooser.addChoosableFileFilter(filterc);
      chooser.addChoosableFileFilter(filterd);
      chooser.addChoosableFileFilter(filtere);
      chooser.setFileFilter(filtere);

      chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

      int returnVal = chooser.showDialog(this, null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {

          try {

              if (chooser.getFileFilter().equals(filterd)) {
                  if (chooser.getSelectedFile().isDirectory() == true) {
                      ArrayList<String> files = new ArrayList();
                      files.add(chooser.getSelectedFile().getPath());
                      v800export V800_export = new v800export();
                      V800_export.export_sessions(this, files);
                  } else {
                      return;
                  }

              } else {

                  Datei = new File(chooser.getSelectedFile().toString());
                  Data = new byte[(int) Datei.length()];

                  Eingabedatei = new java.io.FileInputStream(chooser.getSelectedFile());

                  Eingabedatei.read(Data);

                  Eingabedatei.close();
                  if (chooser.getFileFilter().equals(filtera)) {
                      ExtractTour(Data);
                  }
                  if (chooser.getFileFilter().equals(filterb)) {
                      ExtractHactronicFile(Data);
                  }
                  if (chooser.getFileFilter().equals(filterc)) {
                      ExtractPolarFile(Data);
                  }
                  if (chooser.getFileFilter().equals(filtere)) {
                      ExtractCSV(Data);
                  }

              }
          } catch (IOException e) {
              JOptionPane.showMessageDialog(null, "IO-Fehler bei Datenlesen", "Achtung!", JOptionPane.ERROR_MESSAGE);
          }

      } else {
          return;
      }

      Properties.setProperty("import.dir", chooser.getCurrentDirectory().getPath());
      JOptionPane.showMessageDialog(null, "Daten  Ende", "Achtung!", JOptionPane.ERROR_MESSAGE);
      ChangeModel();
  }//GEN-LAST:event_jMenuOpenActionPerformed

    /** Exit the Application */
  private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm

      try {
          FileOutputStream out = new FileOutputStream(Properties.getProperty("working.dir") + SystemProperties.getProperty("file.separator") + "JCicloexp.cfg");
          Properties.setProperty("Screenheight", "" + this.getSize().height);
          Properties.setProperty("Screenwidth", "" + this.getSize().width);
       
          Properties.store(out, "---Properties of HWCyclingData---");
          out.close();

      } catch (Exception e) {
      }
      System.exit(0);
  }//GEN-LAST:event_exitForm

  private void Info_Button_Suche_TrackLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Info_Button_Suche_TrackLogActionPerformed

      ExampleFileFilter filtera = new ExampleFileFilter();

      filtera.addExtension("kmz");
   
      filtera.addExtension("TCX");
 
      filtera.addExtension("GPX");
      filtera.setDescription("Track Datei");
  
      chooser.resetChoosableFileFilters();
      chooser.addChoosableFileFilter(filtera);
      chooser.setFileFilter(filtera);
 
      if (new java.io.File(Info_Track_Log.getText()).exists()) {
          chooser.setCurrentDirectory(new java.io.File(Info_Track_Log.getText()));
      } else {
          chooser.setCurrentDirectory(new java.io.File(Properties.getProperty("GPS.dir")));
      }
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

      int returnVal = chooser.showDialog(this, null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
          Info_Track_Log.setText(chooser.getSelectedFile().getPath());
      }
}//GEN-LAST:event_Info_Button_Suche_TrackLogActionPerformed

  private void Auswahl_MapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_MapActionPerformed
      // TODO add your handling code here:
      if (Update) {
 
          Update = false;
          Auswahl_Statistik.setSelectedIndex(Auswahl_Map.getSelectedIndex());
          Auswahl_Graphik.setSelectedIndex(Auswahl_Map.getSelectedIndex());
          Auswahl_Histogramm.setSelectedIndex(Auswahl_Map.getSelectedIndex());
          Auswahl_Info.setSelectedIndex(Auswahl_Map.getSelectedIndex());

          Update_Graphik_paint = true;
          Update_Map_paint = true;
          Update_Info = true;
          Update = true;
          Draw_Map();

      }


}//GEN-LAST:event_Auswahl_MapActionPerformed

  private void LoadGoogleEarthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadGoogleEarthActionPerformed
      // TODO add your handling code here:

      if (new java.io.File(Properties.getProperty("GoogleEarthPath", "no Path")).exists() == false) {
          return;
      }

      String Datei = Statistikhandle.TourData[Auswahl_Map.getSelectedIndex()].DataProperty.getProperty("GoogleEarth", "");
      String tempstring = new String();
      File tempfile = new File(Properties.getProperty("working.dir","c:")+"/temp.kml");

      if (Datei.endsWith(".gpx") || Datei.endsWith(".tcx")) {
        //  tempstring = tokml(Auswahl_Map.getSelectedIndex()-1);
          tempstring = tokml(Auswahl_Map.getSelectedIndex());

          try {
              FileOutputStream fileoutputstream = new FileOutputStream(tempfile);
              fileoutputstream.write(tempstring.getBytes());
              fileoutputstream.close();
              Runtime.getRuntime().exec(Properties.getProperty("GoogleEarthPath") + " " + tempfile.getPath());
     
          } catch (Exception e) {
          }
      }

      if (Datei.endsWith(".kmz") || Datei.endsWith(".kml")) {

          try {
              Runtime.getRuntime().exec(Properties.getProperty("GoogleEarthPath") + " " + Statistikhandle.TourData[Auswahl_Map.getSelectedIndex()].DataProperty.getProperty("GoogleEarth", ""));
          } catch (Exception ex) {
              Logger.getLogger(JCicloTronic.class.getName()).log(Level.SEVERE, null, ex);
          }

  }//GEN-LAST:event_LoadGoogleEarthActionPerformed
    }
      
      
  private void Map_PanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Map_PanelComponentShown
      // TODO add your handling code here:
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      if (SelectionChanged) {
          Statistik_PanelComponentShown_StatistikStarten(evt);
          alteAuswahl = -6;
          Draw_Map();
          Update_Graphik_paint = true;
      }

      if (Update_Map_paint == true) {
          Draw_Map();
      }
 //      Draw_Map();
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));


  }//GEN-LAST:event_Map_PanelComponentShown

//    public interface User32 extends StdCallLibrary {
//      User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
// 
//      HWND FindWindow(String lpClassName, String lpWindowName);
//   };
    
    
    
    private void DatentabelleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DatentabelleMouseDragged
        // Add your handling code here:
        SelectionChanged = true;

        Update = false;
        Datenliste_Monat.setEnabled(false);
        Datenliste_Monat.setSelectedIndex(0);
        Datenliste_Jahr.setSelectedIndex(0);
        Datenliste_Zeitabschnitt.setSelectedIndex(0);
        Datenliste_TourTyp.setSelectedIndex(0);
        Update = true;

        jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());
    }//GEN-LAST:event_DatentabelleMouseDragged

    private void DatentabelleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DatentabelleMouseClicked
        // Add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getButton() != MouseEvent.BUTTON1) {

            int dummy = Datentabelle.rowAtPoint(evt.getPoint());
            int selection[] = Datentabelle.getSelectedRows();

            boolean isselected = false;
            for (int i = 0; i < selection.length; i++) {
                if (dummy == selection[i]) {
                    isselected = true;
                }
            };
            Datentabelle.clearSelection();
            if (!isselected) {
                Datentabelle.addRowSelectionInterval(dummy, dummy);
            }

            for (int i = 0; i < selection.length; i++) {
                if (dummy != selection[i]) {
                    Datentabelle.addRowSelectionInterval(selection[i], selection[i]);
                }

            }

        }
        SelectionChanged = true;
        Update = false;
        Datenliste_Monat.setEnabled(false);
        Datenliste_Monat.setSelectedIndex(0);
        Datenliste_Jahr.setSelectedIndex(0);
        Datenliste_Zeitabschnitt.setSelectedIndex(0);
        Datenliste_TourTyp.setSelectedIndex(0);

        Update = true;
        jLabel69_Selektiert.setText(Datentabelle.getSelectedRowCount() + " / " + Datentabelle.getRowCount());
        if (evt.getClickCount() == 2) {
            Hauptfenster.setSelectedIndex(1);
        }
    }//GEN-LAST:event_DatentabelleMouseClicked

    private void Graphik_PanelComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Graphik_PanelComponentHidden
        // TODO add your handling code here:
    
        Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
        
    }//GEN-LAST:event_Graphik_PanelComponentHidden

    private void Graphik_check_SchrittlängeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graphik_check_SchrittlängeActionPerformed
        // TODO add your handling code here:
 
        if (Graphik_check_Schrittlänge.isSelected()) {
            Properties.setProperty("View Schrittlänge", "1");
        } else {
            Properties.setProperty("View Schrittlänge", "0");
        }
        Save_Min_Max(Auswahl_Graphik.getSelectedIndex());
        Update_XYGraphik();
    }//GEN-LAST:event_Graphik_check_SchrittlängeActionPerformed

    private void jMenuTourEditorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuTourEditorMouseClicked
        clearStatistik();
        repaint();
        Editmode = true;
        Statistikhandle = new JStatistik();
        Statistikhandle.StatistikStart(this);

        TourEditor = new JTourEditor(this, true);
        Editmode = false;
        SelectionChanged = true;
        Datentabelle.clearSelection();
        Datentabelle.addRowSelectionInterval(0, 0);
        Datenliste_scroll_Panel.getViewport().setViewPosition(new java.awt.Point(0, 0));
        Statistikhandle = new JStatistik();
        Statistikhandle.StatistikStart(this);        
    }//GEN-LAST:event_jMenuTourEditorMouseClicked

    private void jMenu_V800_LadenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu_V800_LadenMouseClicked
       
        V800_Download_Training V800_read = new V800_Download_Training();
        if(!V800_read.start(this)) return;
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        ArrayList<String> sessions = V800_read.get_all_sessions();
        ArrayList<String> NewData = new ArrayList();

        for (int i = 0; i < sessions.size(); i++) {

            String Name = sessions.get(i).replace("/", "");
            Name = Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                    + SystemProperties.getProperty("file.separator")
                    + Name.substring(0, Name.length()-2) + "_Tour.cfg";

            File file = new File(Name);

            if ((file.exists() != true || Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1)) {
                NewData.add(sessions.get(i));
             }

        }

        Thread thread = new Thread(new Runnable() {

            public void run() {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                pm = new ProgressMonitor(
                        JCicloTronic.this, "Download...", "", 0, 100);
                pm.setMillisToPopup(0);
                 pm.setMillisToDecideToPopup(0);

                ArrayList<String> PathList = V800_read.get_sessions(NewData);

                V800_read.stop();
                //    V800_read = null;

                pm.close();

                v800export V800_export = new v800export();

                pm = new ProgressMonitor(
                        JCicloTronic.this, "Importiere...", "", 0, 100);
                pm.setMillisToPopup(0);
                pm.setMillisToDecideToPopup(0);

                V800_export.export_sessions(JCicloTronic.this, PathList);
                
                pm.close();
                 ChangeModel();
                
                try {

                    Thread.sleep(100);
                } catch (Exception e) {
                    if (Thread.interrupted()) {
                        return;
                    }
                }
            }

        });

        thread.start();

        ChangeModel();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jMenu_V800_LadenMouseClicked

    private void Map_TypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Map_TypeActionPerformed
        // TODO add your handling code here:
        if (!locmap && mapKit!=null){
          int zoom = mapKit.getMainMap().getZoom();
          GeoPosition Center = mapKit.getMainMap().getCenterPosition();
               
           Draw_Map();  
           
        mapKit.setCenterPosition(Center);
        int minzoom = mapKit.getMainMap().getTileFactory().getInfo().getMinimumZoomLevel();
        if (minzoom <= zoom)
               mapKit.getMainMap().setZoom(zoom);
                        else
               mapKit.getMainMap().setZoom(minzoom);
            
        }
           
    }//GEN-LAST:event_Map_TypeActionPerformed

  
    
    public void setStatistik(JStatistik Statistikhandle, int selected) {
        format = new java.text.DecimalFormat("###0.00");

        Statistik_Minimale_Höhe.setText(format.format(Statistikhandle.TourData[selected].min_Hoehe));
        Statistik_Minimale_Höhe.setToolTipText(Statistikhandle.TourData[selected].min_HoeheStr);
        Statistik_Maximale_Höhe.setText(format.format(Statistikhandle.TourData[selected].max_Hoehe));
        Statistik_Maximale_Höhe.setToolTipText(Statistikhandle.TourData[selected].max_HoeheStr);
        Statistik_Summe_Hm_Steigung.setText(format.format(Statistikhandle.TourData[selected].ges_Hoehep));
        Statistik_Summe_Hm_Gefälle.setText(format.format(Statistikhandle.TourData[selected].ges_Hoehem));
        Statistik_HM_pro_km.setText(format.format(Statistikhandle.TourData[selected].ges_Hoehep / Statistikhandle.TourData[selected].Zoomstrecke));
        Statistik_min_Temp.setText(format.format(Statistikhandle.TourData[selected].min_Temperatur));
        Statistik_min_Temp.setToolTipText(Statistikhandle.TourData[selected].min_TemperaturStr);
        Statistik_max_Temp.setText(format.format(Statistikhandle.TourData[selected].max_Temperatur));
        Statistik_max_Temp.setToolTipText(Statistikhandle.TourData[selected].max_TemperaturStr);
        Statistik_av_Temp.setText(format.format(Statistikhandle.TourData[selected].av_Temperatur));
        Statistik_av_Temp.setToolTipText(Statistikhandle.TourData[selected].av_Temperatur_maxStr);
        Statistik_Zeit_absolut.setText(HMS((int) Statistikhandle.TourData[selected].gesammtZeit));
        Statistik_Zeit_aktiv.setText(HMS((int) Statistikhandle.TourData[selected].gefahreneZeit));
        Statistik_Max_Geschw.setText(format.format(Statistikhandle.TourData[selected].max_Geschw));
        Statistik_Max_Geschw.setToolTipText(Statistikhandle.TourData[selected].max_GeschwStr);
        Statistik_av_Geschw.setText(format.format(Statistikhandle.TourData[selected].av_Geschw));
        Statistik_av_Geschw.setToolTipText(Statistikhandle.TourData[selected].av_Geschw_maxStr);
        Statistik_max_HF.setText(format.format(Statistikhandle.TourData[selected].max_Hf));
        Statistik_max_HF.setToolTipText(Statistikhandle.TourData[selected].max_HfStr);
        Statistik_av_HF.setText(format.format(Statistikhandle.TourData[selected].av_Hf));
        Statistik_av_HF.setToolTipText(Statistikhandle.TourData[selected].av_Hf_maxStr);
        Statistik_max_Cadence.setText(format.format(Statistikhandle.TourData[selected].max_Cadence));
        Statistik_max_Cadence.setToolTipText(Statistikhandle.TourData[selected].max_CadenceStr);
        Statistik_av_Cadence.setText(format.format(Statistikhandle.TourData[selected].av_Cadence));
        Statistik_av_Cadence.setToolTipText(Statistikhandle.TourData[selected].av_Cadence_maxStr);
        
        Statistik_max_Schrittlänge.setText(format.format(Statistikhandle.TourData[selected].max_Schritt_länge));
        Statistik_max_Schrittlänge.setToolTipText(Statistikhandle.TourData[selected].max_Schritt_längeStr);
        Statistik_av_Schrittlänge.setText(format.format(Statistikhandle.TourData[selected].av_Schritt_länge));
        Statistik_av_Schrittlänge.setToolTipText(Statistikhandle.TourData[selected].av_Schritt_länge_maxStr);
        
        Statistik_max_Steigung_m.setText(format.format(Statistikhandle.TourData[selected].max_Steigmeterpositiv));
        Statistik_max_Steigung_m.setToolTipText(Statistikhandle.TourData[selected].max_SteigmeterpositivStr);
        Statistik_av_Steigung_m.setText(format.format(Statistikhandle.TourData[selected].av_Steigmeterpositiv));
        Statistik_av_Steigung_m.setToolTipText(Statistikhandle.TourData[selected].av_Steigmeterpositiv_maxStr);
        Statistik_max_Gefälle_m.setText(format.format(Statistikhandle.TourData[selected].max_Steigmeternegativ));
        Statistik_max_Gefälle_m.setToolTipText(Statistikhandle.TourData[selected].max_SteigmeternegativStr);
        Statistik_av_Gefälle_m.setText(format.format(Statistikhandle.TourData[selected].av_Steigmeternegativ));
        Statistik_av_Gefälle_m.setToolTipText(Statistikhandle.TourData[selected].av_Steigmeternegativ_maxStr);
        Statistik_max_Steigung_p.setText(format.format(Statistikhandle.TourData[selected].max_Steigprozentpositiv));
        Statistik_max_Steigung_p.setToolTipText(Statistikhandle.TourData[selected].max_SteigprozentpositivStr);
        Statistik_av_Steigung_p.setText(format.format(Statistikhandle.TourData[selected].av_Steigprozentpositiv));
        Statistik_av_Steigung_p.setToolTipText(Statistikhandle.TourData[selected].av_Steigprozentpositiv_maxStr);
        Statistik_max_Gefälle_p.setText(format.format(Statistikhandle.TourData[selected].max_Steigprozentnegativ));
        Statistik_max_Gefälle_p.setToolTipText(Statistikhandle.TourData[selected].max_SteigprozentnegativStr);
        Statistik_av_Gefälle_p.setText(format.format(Statistikhandle.TourData[selected].av_Steigprozentnegativ));
        Statistik_av_Gefälle_p.setToolTipText(Statistikhandle.TourData[selected].av_Steigprozentnegativ_maxStr);
        Statistik_Teilstrecke.setText(format.format(Statistikhandle.TourData[selected].Zoomstrecke)+ " km");
        Statistik_Belastung.setText(format.format(Statistikhandle.TourData[selected].Belastung));
        Statistik_Belastung.setToolTipText(Statistikhandle.TourData[selected].max_BelastungStr);
        int erhl_tag = (int)(Statistikhandle.TourData[selected].Erholungszeit/(3600.0*24));
        int erhl_std = (int)((Statistikhandle.TourData[selected].Erholungszeit - erhl_tag*3600*24)/3600.0);
        Statistik_Erholungszeit.setText(""+ erhl_tag +" T; "+ erhl_std +" h");
        Statistik_Erholungszeit.setToolTipText(Statistikhandle.TourData[selected].max_ErholungszeitStr);
        
        Statistik_Kalorien_absolut.setText(format.format(Statistikhandle.TourData[selected].Kalorien));
        Statistik_Kalorien_absolut.setToolTipText(Statistikhandle.TourData[selected].max_KalorienStr);
        Statistik_Fett.setText(format.format(Statistikhandle.TourData[selected].Fett));
        Statistik_Fett.setToolTipText(Statistikhandle.TourData[selected].max_FettStr);
        
        Statistik_Protein.setText(format.format(Statistikhandle.TourData[selected].Protein));
        Statistik_Protein.setToolTipText(Statistikhandle.TourData[selected].max_ProteinStr);
        
        Statistik_Kalorien_h.setText(format.format(Statistikhandle.TourData[selected].Kalorien_h));
        Statistik_Kalorien_h.setToolTipText(Statistikhandle.TourData[selected].max_Kalorien_hStr);
        
        Statistik_Lauf_Index.setText(format.format(Statistikhandle.TourData[selected].Lauf_Index));
        Statistik_Lauf_Index.setToolTipText(Statistikhandle.TourData[selected].max_Lauf_IndexStr);
                
                
        if (selected !=0) Statistik_Titel.setText("<html>" + Statistikhandle.TourData[selected].DataProperty.getProperty("Titel","")+"</html>");
        else Statistik_Titel.setText("");
      

    }

    private void clearStatistik() {

        Statistik_Minimale_Höhe.setText("---");
        Statistik_Minimale_Höhe.setToolTipText(null);
        Statistik_Maximale_Höhe.setText("---");
        Statistik_Maximale_Höhe.setToolTipText(null);
        Statistik_Summe_Hm_Steigung.setText("---");
        Statistik_Summe_Hm_Steigung.setToolTipText(null);
        Statistik_Summe_Hm_Gefälle.setText("---");
        Statistik_Summe_Hm_Gefälle.setToolTipText(null);
        Statistik_HM_pro_km.setText("---");
        Statistik_HM_pro_km.setToolTipText(null);
        Statistik_min_Temp.setText("---");
        Statistik_min_Temp.setToolTipText(null);
        Statistik_max_Temp.setText("---");
        Statistik_max_Temp.setToolTipText(null);
        Statistik_av_Temp.setText("---");
        Statistik_av_Temp.setToolTipText(null);
        Statistik_Zeit_absolut.setText("---");
        Statistik_Zeit_absolut.setToolTipText(null);
        Statistik_Zeit_aktiv.setText("---");
        Statistik_Zeit_aktiv.setToolTipText(null);
        Statistik_Max_Geschw.setText("---");
        Statistik_Max_Geschw.setToolTipText(null);
        Statistik_av_Geschw.setText("---");
        Statistik_av_Geschw.setToolTipText(null);
        Statistik_max_HF.setText("---");
        Statistik_max_HF.setToolTipText(null);
        Statistik_av_HF.setText("---");
        Statistik_av_HF.setToolTipText(null);
        Statistik_max_Cadence.setText("---");
        Statistik_max_Cadence.setToolTipText(null);
        Statistik_av_Cadence.setText("---");
        Statistik_av_Cadence.setToolTipText(null);
        Statistik_max_Schrittlänge.setText("---");
        Statistik_max_Schrittlänge.setToolTipText(null);
        Statistik_av_Schrittlänge.setText("---");
        Statistik_av_Schrittlänge.setToolTipText(null);
        Statistik_max_Steigung_m.setText("---");
        Statistik_max_Steigung_m.setToolTipText(null);
        Statistik_av_Steigung_m.setText("---");
        Statistik_av_Steigung_m.setToolTipText(null);
        Statistik_max_Gefälle_m.setText("---");
        Statistik_max_Gefälle_m.setToolTipText(null);
        Statistik_av_Gefälle_m.setText("---");
        Statistik_av_Gefälle_m.setToolTipText(null);
        Statistik_max_Steigung_p.setText("---");
        Statistik_max_Steigung_p.setToolTipText(null);
        Statistik_av_Steigung_p.setText("---");
        Statistik_av_Steigung_p.setToolTipText(null);
        Statistik_max_Gefälle_p.setText("---");
        Statistik_max_Gefälle_p.setToolTipText(null);
        Statistik_av_Gefälle_p.setText("---");
        Statistik_av_Gefälle_p.setToolTipText(null);
        Statistik_Teilstrecke.setText("---");
        Statistik_Teilstrecke.setToolTipText(null);
        Statistik_Belastung.setText("---");
        Statistik_Belastung.setToolTipText(null);
        Statistik_Erholungszeit.setText("---");
        Statistik_Erholungszeit.setToolTipText(null);
        Statistik_Kalorien_absolut.setText("---");
        Statistik_Kalorien_absolut.setToolTipText(null);
        Statistik_Fett.setText("---");
        Statistik_Fett.setToolTipText(null);
        Statistik_Protein.setText("---");
        Statistik_Protein.setToolTipText(null);

    }

   
  
    private void ExtractTour(byte Data[]) {
        int i, j, m, n, x, c, t; //i,j Zähler, m = Marken, n= Datensätze x=Buffer c= cadence, t=temperatur
        int Höhe, Puls, Strecke, Zeit;
        boolean Indata = false;
        boolean Hac = false;
        boolean Hacpro = false;
        boolean cm414 = false;
        int Datenlänge = 0;
        int DataYear = 0;
        int DataMonth = 0;
        int DataDate = 0;
        File file = new File("Dummy");
        Buffer = new StringBuffer();
        String Dummy = new String(Data);
        Dummy = Dummy.toUpperCase();

        if (Data[262] == 0x31) {
            Hacpro = true;
        }
        if (Data[648] == 53) {
            Hac = true;
        }
        if (Data[648] == 51) {
            cm414 = true;
        }
        if (!(Hacpro || Hac || cm414)) {
            JOptionPane.showMessageDialog(null, "Weder CM414 noch HAC4 Dateien gefunden!", "Achtung!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        formatproperty = new java.text.DecimalFormat("00");
        format = new java.text.DecimalFormat("000000");
        formatneg = new java.text.DecimalFormat("00000");
        if (Hac == true) {

            DataYear = Integer.parseInt(new String(Data, 715, 4));
            DataMonth = Integer.parseInt(new String(Data, 720, 2));
            DataDate = Integer.parseInt(new String(Data, 722, 2));
        }
        if (cm414 == true) {
            DataYear = Integer.parseInt(new String(Data, 680, 4));
            DataMonth = Integer.parseInt(new String(Data, 675, 2));
            DataDate = Integer.parseInt(new String(Data, 677, 2));
        }
        if (Hacpro == true) {
            DataYear = Data[266] / 16;
            DataMonth = Data[265] / 16;
            DataDate = Data[264] / 16;

            DataYear = 2000 + 10 * DataYear + (int) Data[266] - 16 * DataYear; //Jahr
            DataMonth = 10 * DataMonth + (int) Data[265] - 16 * DataMonth;//Monat
            DataDate = 10 * DataDate + (int) Data[264] - 16 * DataDate;
        }
  
        int HexLo, HexHi, Hex;
        if (Hac || cm414) {
            Data = Dummy.getBytes();
            byte DataBuffer[] = new byte[81930];
            i = 765;
            while ((Data[i + 2] != (byte) 'A' || Data[i + 3] != (byte) 'A' || Data[i + 42] != (byte) 'B') && i != 81925) {
                i += 40;
            }

            if (i == 81925) {
                JOptionPane.showMessageDialog(null, "Keine Daten gefunden!", "Achtung!", JOptionPane.ERROR_MESSAGE);
                return;
            }
             for (j = i; j < 81925; j++) {
                DataBuffer[j - i] = Data[j];
            }
            for (j = 765; j < i; j++) {
                DataBuffer[81925 - i + j - 765] = Data[j];
            }
            i = 0;
            m = 0;
            n = 0;
            Höhe = 0;
            Puls = 0;
            Strecke = 0;
            Zeit = 0;

            StringDummy = new String(DataBuffer);

            while (i != 81160) {
                if (StringDummy.charAt(i + 2) == 'A' && StringDummy.charAt(i + 3) == 'A' && StringDummy.charAt(i + 42) == 'B') {
                    Indata = true;
                    m = 0;
                    n = 0;
                    DataProperty = new java.util.Properties();
                    Buffer = new StringBuffer();
                    Strecke = 0;

                    int tempYear = DataYear;

                    DataProperty.setProperty("Tag", StringDummy.substring(i + 17, i + 19));
                    DataProperty.setProperty("Monat", StringDummy.substring(i + 15, i + 17));

                    if (DataMonth < Integer.parseInt(DataProperty.getProperty("Monat"))) {
                        tempYear -= 1;
                    }
                    if (DataMonth == Integer.parseInt(DataProperty.getProperty("Monat")) && DataDate < Integer.parseInt(DataProperty.getProperty("Tag"))) {
                        tempYear -= 1;
                    }
                    DataProperty.setProperty("Jahr", "" + tempYear);


                    DataProperty.setProperty("Stunde", StringDummy.substring(i + 10, i + 12));
                    DataProperty.setProperty("Minute", StringDummy.substring(i + 12, i + 14));

                    HexLo = Integer.parseInt(StringDummy.substring(i, i + 1), 16);
                    HexLo = HexLo & 0x0003;

                    if (Hac) {
                        if (HexLo == 2) {
                            DataProperty.setProperty("Typ", "Bike");
                        }
                        if (HexLo == 1) {
                            DataProperty.setProperty("Typ", "Ski");
                        }
                        if (HexLo == 3) {
                            DataProperty.setProperty("Typ", "Ski & Bike");
                        }
                        if (HexLo == 0) {
                            DataProperty.setProperty("Typ", "Jogging");
                        }
                    } else {
                        if (HexLo == 3) {
                            DataProperty.setProperty("Typ", "Bike 1");
                        }
                        if (HexLo == 2) {
                            DataProperty.setProperty("Typ", "Bike 2");
                        }
                        if (HexLo == 1) {
                            DataProperty.setProperty("Typ", "Unknown");
                        }
                        if (HexLo == 0) {
                            DataProperty.setProperty("Typ", "Jogging");
                        }
                    }

                    Höhe = Integer.parseInt(StringDummy.substring(i + 30, i + 34), 16);
                    Puls = Integer.parseInt(StringDummy.substring(i + 35, i + 39), 16);

                    Buffer = Buffer.append(format.format(0)).append('\t').append(format.format(0)).append('\t');
                    Buffer = Buffer.append(format.format(Höhe)).append('\t');
                    Buffer = Buffer.append(format.format(Puls)).append('\t');
                    Buffer = Buffer.append(format.format(0)).append('\t').append(format.format(0)).append('\n');
                }

                if (StringDummy.charAt(i + 2) == 'B' && StringDummy.charAt(i + 3) == 'B' && Indata == true) {
                    x = Integer.parseInt(StringDummy.substring(i + 5, i + 7), 16);
                    if (x != 0) {
                        m += 1;
                        DataProperty.setProperty("Marke " + m, Integer.toString(n * 20 + x));
                        DataProperty.setProperty("AnzahlMarken", Integer.toString(m));
                    }
                    c = Integer.parseInt(StringDummy.substring(i + 7, i + 9), 16);
                    t = Integer.parseInt(StringDummy.substring(i, i + 2), 16);

                    for (j = 0; j < 6; j++) {
                        n += 1;
                        HexLo = Integer.parseInt(StringDummy.substring(i + j * 5 + 10, i + j * 5 + 14), 16);

                        Hex = ((HexLo & 0xF000) << 16) >> 27;
                        if (Hac == true) {
                            Puls = Puls + Hex;
                        }

                        Hex = ((HexLo & 0x0FC0) << 20) >> 26;
                        if (Hex > 16) {
                            Hex = 16 + (Hex - 16) * 7;
                        }
                        if (Hex < -16) {
                            Hex = -16 + (Hex + 16) * 7;
                        }
                        Höhe = Höhe + Hex;

                        Hex = HexLo & 0x003F;
                        Strecke = Strecke + Hex;


                        Buffer = Buffer.append(format.format(n * 20)).append('\t').append(format.format(Strecke)).append('\t');
                        if (Höhe < 0) {
                            Buffer = Buffer.append(formatneg.format(Höhe)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Höhe)).append('\t');
                        }
                        if (Puls < 0) {
                            Buffer = Buffer.append(formatneg.format(Puls)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Puls)).append('\t');
                        }
                        Buffer = Buffer.append(format.format(c)).append('\t');
                        if (t > 125) {
                            Buffer = Buffer.append(formatneg.format(t - 256)).append('\n');
                        } else {
                            Buffer = Buffer.append(format.format(t)).append('\n');
                        }

                    }
                }
                if (StringDummy.charAt(i + 2) == 'C' && StringDummy.charAt(i + 3) == 'C' && Indata == true) {
                    x = Integer.parseInt(StringDummy.substring(i + 5, i + 7), 16);

                    c = Integer.parseInt(StringDummy.substring(i + 7, i + 9), 16);
                    t = Integer.parseInt(StringDummy.substring(i, i + 2), 16);
                    m = n;
                    j = -1;
                    while (m * 20 + x > n * 20) {
    
                        n++;
                        j++;
                        HexLo = Integer.parseInt(StringDummy.substring(i + j * 5 + 10, i + j * 5 + 14), 16);

                        Hex = ((HexLo & 0xF000) << 16) >> 27;
                        if (Hac == true) {
                            Puls = Puls + Hex;
                        }

                        Hex = ((HexLo & 0x0FC0) << 20) >> 26;
                        if (Hex > 16) {
                            Hex = 16 + (Hex - 16) * 7;
                        }
                        if (Hex < -16) {
                            Hex = -16 + (Hex + 16) * 7;
                        }
                        Höhe = Höhe + Hex;

                        Hex = HexLo & 0x003F;
                        Strecke = Strecke + Hex;

                        if (m * 20 + x > n * 20) {
                            Buffer = Buffer.append(format.format(n * 20));
                        } else {
                            Buffer = Buffer.append(format.format(m * 20 + x));
                        }
                        Buffer = Buffer.append('\t').append(format.format(Strecke)).append('\t');
                        if (Höhe < 0) {
                            Buffer = Buffer.append(formatneg.format(Höhe)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Höhe)).append('\t');
                        }
                        if (Puls < 0) {
                            Buffer = Buffer.append(formatneg.format(Puls)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Puls)).append('\t');
                        }
                        Buffer = Buffer.append(format.format(c)).append('\t');
                        if (t > 125) {
                            Buffer = Buffer.append(formatneg.format(t - 256.0)).append('\n');
                        } else {
                            Buffer = Buffer.append(format.format(t)).append('\n');
                        }
                        if (m * 20 + x <= n * 20) {
                            break;
                        }
                    }
                    Zeit = m * 20 + x;
                }

                if (StringDummy.charAt(i + 2) == 'D' && StringDummy.charAt(i + 3) == 'D' && Indata == true) {
                    Indata = false;
                    int tempYear = DataYear;
                    if (DataMonth < Integer.parseInt(DataProperty.getProperty("Monat"))) {
                        tempYear -= 1;
                    }
                    if (DataMonth == Integer.parseInt(DataProperty.getProperty("Monat")) && DataDate < Integer.parseInt(DataProperty.getProperty("Tag"))) {
                        tempYear -= 1;
                    }

                    file = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                            + SystemProperties.getProperty("file.separator")
                            + tempYear + DataProperty.getProperty("Monat")
                            + DataProperty.getProperty("Tag")
                            + DataProperty.getProperty("Stunde")
                            + DataProperty.getProperty("Minute")
                            + "_Tour.txt");


                    file2 = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                            + SystemProperties.getProperty("file.separator")
                            + tempYear + DataProperty.getProperty("Monat")
                            + DataProperty.getProperty("Tag")
                            + DataProperty.getProperty("Stunde")
                            + DataProperty.getProperty("Minute")
                            + "_Tour.cfg");

                    try {
                        if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                            Ausgabedatei = new FileOutputStream(file);
                            Ausgabedatei.write(Buffer.toString().getBytes());
                            Ausgabedatei.close();
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei", "Achtung!", JOptionPane.ERROR_MESSAGE);
                    }

                    try {
                        DataProperty.setProperty("Anzahl Datenpunkte", Integer.toString(n + 1));
                        formatb = new java.text.DecimalFormat("0.00");
                        //                   DataProperty.setProperty("Strecke",java.lang.Float.toString((float)Strecke/100));
                        DataProperty.setProperty("Strecke", formatb.format(Strecke / 100.0));

                        DataProperty.setProperty("Dauer", Integer.toString(Zeit));
                        if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                            Ausgabedatei = new FileOutputStream(file2);
                            DataProperty.store(Ausgabedatei, "Tour Eigenschaften: " + tempYear + DataProperty.getProperty("Monat")
                                    + DataProperty.getProperty("Tag")
                                    + DataProperty.getProperty("Stunde")
                                    + DataProperty.getProperty("Minute"));
                            Ausgabedatei.close();

                        }
                    } catch (Exception e) {
                    }

                }
                i += 40;
            }
        }

        if (Hacpro) {
            int Zeitintervall = 0;
            byte DataBuffer[] = new byte[65542];
            i = 326;
            //Suche nach erstem Dateneintrag und danach die Daten sortieren
            while ((Data[i + 0] != (byte) 0xAA || Data[i + 16] != (byte) 0xBB) && i != 65542) {
                i += 16;
            }

            if (i == 65542) {
                JOptionPane.showMessageDialog(null, "Keine Daten gefunden!", "Achtung!", JOptionPane.ERROR_MESSAGE);
                return;
            }
  
            for (j = i; j < 65542; j++) {
                DataBuffer[j - i] = Data[j];
            }
            for (j = 326; j < i; j++) {
                DataBuffer[65542 - i + j - 326] = Data[j];
            }
            i = 0;
            m = 0;
            n = 0;
            Höhe = 0;
            Puls = 0;
            Strecke = 0;
            Zeit = 0;

            while (i != 65216) {
                if (DataBuffer[i + 0] == (byte) 0xAA && DataBuffer[i + 16] == (byte) 0xBB) {
                    Indata = true;
                    m = 0;
                    n = 0;
                    DataProperty = new java.util.Properties();
                    Buffer = new StringBuffer();
                    Strecke = 0;

                    int tempYear = DataYear;
                    int tempDay;
                    int tempMonth;
                    int tempStunde;
                    int tempMinute;

                    //Monat, Tag, Stunde, Minute sind als Byte aber direkt im ASCI Mode abgespeichert.
                    // Deshalb der umständliche Gang über extrahieren der Zehnerstelle und anschliessender Addition der Einer (*16!!)

                    tempMonth = DataBuffer[i + 7] / 16;
                    tempDay = DataBuffer[i + 6] / 16;
                    tempStunde = DataBuffer[i + 5] / 16;
                    tempMinute = DataBuffer[i + 4] / 16;

                    tempMonth = 10 * tempMonth + (int) DataBuffer[i + 7] - 16 * tempMonth;
                    tempDay = 10 * tempDay + (int) DataBuffer[i + 6] - 16 * tempDay;
                    tempStunde = 10 * tempStunde + (int) DataBuffer[i + 5] - 16 * tempStunde;
                    tempMinute = 10 * tempMinute + (int) DataBuffer[i + 4] - 16 * tempMinute;


                    DataProperty.setProperty("Tag", formatproperty.format(tempDay));
                    DataProperty.setProperty("Monat", formatproperty.format(tempMonth));

                    if (DataMonth < Integer.parseInt(DataProperty.getProperty("Monat"))) {
                        tempYear -= 1;
                    }
                    if (DataMonth == Integer.parseInt(DataProperty.getProperty("Monat")) && DataDate < Integer.parseInt(DataProperty.getProperty("Tag"))) {
                        tempYear -= 1;
                    }
                    DataProperty.setProperty("Jahr", "" + tempYear);

                    DataProperty.setProperty("Stunde", formatproperty.format(tempStunde));
                    DataProperty.setProperty("Minute", formatproperty.format(tempMinute));

                    HexLo = DataBuffer[i + 1] & 0xF0 / 16;

                    if (HexLo == 2) {
                        DataProperty.setProperty("Typ", "Bike2");
                    }
                    if (HexLo == 1) {
                        DataProperty.setProperty("Typ", "Jogging");
                    }
                    if (HexLo == 3) {
                        DataProperty.setProperty("Typ", "Bike1");
                    }
                    if (HexLo == 0) {
                        DataProperty.setProperty("Typ", "Unbekannt");
                    }

                    HexLo = DataBuffer[i + 1] & 0x0F;

                    if (HexLo == 2) {
                        Zeitintervall = 10;
                    }
                    if (HexLo == 1) {
                        Zeitintervall = 5;
                    }
                    if (HexLo == 3) {
                        Zeitintervall = 20;
                    }
                    if (HexLo == 0) {
                        Zeitintervall = 2;
                    }
                  
                    Puls = (int) (DataBuffer[i + 14] & 0x000000ff);

                    Höhe = ((((int) (DataBuffer[i + 13]) << 24) & 0xff000000)
                            | (((int) (DataBuffer[i + 12]) << 16) & 0x00ff0000)) / 256 / 256;


                    Buffer = Buffer.append(format.format(0)).append('\t').append(format.format(0)).append('\t');
                    Buffer = Buffer.append(format.format(Höhe)).append('\t');
                    Buffer = Buffer.append(format.format(Puls)).append('\t');
                    Buffer = Buffer.append(format.format(0)).append('\t').append(format.format(0)).append('\n');
                }

                if (DataBuffer[i + 0] == (byte) 0xBB && Indata == true) {
                    x = DataBuffer[i + 3];
                    if (x != 0) {
                        m += 1;
                        DataProperty.setProperty("Marke " + m, Integer.toString(n * Zeitintervall + x));
                        DataProperty.setProperty("AnzahlMarken", Integer.toString(m));
                    }
                    c = (int) DataBuffer[i + 2];
                    t = (int) DataBuffer[i + 1];

                    for (j = 0; j < 6; j++) {
                        n += 1;
                        //                    HexLo = DataBuffer[i+4 + j*2]*256 + DataBuffer[i+5+j*2];
                        HexLo =
                                ((((int) (DataBuffer[i + 5 + j * 2]) << 24) & 0xff000000)
                                | (((int) (DataBuffer[i + 4 + j * 2]) << 16) & 0x00ff0000)) / 256 / 256;

                        Hex = ((HexLo & 0x0000F000) << 16) >> 27;
                        Puls = Puls + Hex;

                        Hex = ((HexLo & 0x00000FC0) << 20) >> 26;
                        if (Hex > 16) {
                            Hex = 16 + (Hex - 16) * 7;
                        }
                        if (Hex < -16) {
                            Hex = -16 + (Hex + 16) * 7;
                        }
                        Höhe = Höhe + Hex;

                        Hex = HexLo & 0x0000003F;
                        Strecke = Strecke + Hex;


                        Buffer = Buffer.append(format.format(n * Zeitintervall)).append('\t').append(format.format(Strecke)).append('\t');
                        if (Höhe < 0) {
                            Buffer = Buffer.append(formatneg.format(Höhe)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Höhe)).append('\t');
                        }
                        if (Puls < 0) {
                            Buffer = Buffer.append(formatneg.format(Puls)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Puls)).append('\t');
                        }
                        Buffer = Buffer.append(format.format(c)).append('\t');
                        if (t > 125) {
                            Buffer = Buffer.append(formatneg.format(t - 256)).append('\n');
                        } else {
                            Buffer = Buffer.append(format.format(t)).append('\n');
                        }

                    }
                }
                if (DataBuffer[i + 0] == (byte) 0xCC && Indata == true) {
                    x = DataBuffer[i + 3];
                    c = DataBuffer[i + 2];
                    t = DataBuffer[i + 1];
                    m = n;
                    j = -1;
                    while (m * Zeitintervall + x > n * Zeitintervall) {
 

                        n++;
                        j++;
  
                        HexLo = ((((int) (DataBuffer[i + 5 + j * 2]) << 24) & 0xff000000)
                                | (((int) (DataBuffer[i + 4 + j * 2]) << 16) & 0x00ff0000)) / 256 / 256;

                        Hex = ((HexLo & 0x0000F000) << 16) >> 27;
                        Puls = Puls + Hex;

                        Hex = ((HexLo & 0x00000FC0) << 20) >> 26;
                        if (Hex > 16) {
                            Hex = 16 + (Hex - 16) * 7;
                        }
                        if (Hex < -16) {
                            Hex = -16 + (Hex + 16) * 7;
                        }
                        Höhe = Höhe + Hex;

                        Hex = HexLo & 0x0000003F;
                        Strecke = Strecke + Hex;

                        if (m * Zeitintervall + x > n * Zeitintervall) {
                            Buffer = Buffer.append(format.format(n * Zeitintervall));
                        } else {
                            Buffer = Buffer.append(format.format(m * Zeitintervall + x));
                        }
                        Buffer = Buffer.append('\t').append(format.format(Strecke)).append('\t');
                        if (Höhe < 0) {
                            Buffer = Buffer.append(formatneg.format(Höhe)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Höhe)).append('\t');
                        }
                        if (Puls < 0) {
                            Buffer = Buffer.append(formatneg.format(Puls)).append('\t');
                        } else {
                            Buffer = Buffer.append(format.format(Puls)).append('\t');
                        }
                        Buffer = Buffer.append(format.format(c)).append('\t');
                        if (t > 125) {
                            Buffer = Buffer.append(formatneg.format(t - 256.0)).append('\n');
                        } else {
                            Buffer = Buffer.append(format.format(t)).append('\n');
                        }
                        if (m * Zeitintervall + x <= n * Zeitintervall) {
                            break;
                        }
                    }
                    Zeit = m * Zeitintervall + x;
                }

                if (DataBuffer[i + 0] == (byte) 0xDD && Indata == true) {

                    Indata = false;
                    int tempYear = DataYear;
                    if (DataMonth < Integer.parseInt(DataProperty.getProperty("Monat"))) {
                        tempYear -= 1;
                    }
                    if (DataMonth == Integer.parseInt(DataProperty.getProperty("Monat")) && DataDate < Integer.parseInt(DataProperty.getProperty("Tag"))) {
                        tempYear -= 1;
                    }


                    file = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                            + SystemProperties.getProperty("file.separator")
                            + tempYear + DataProperty.getProperty("Monat")
                            + DataProperty.getProperty("Tag")
                            + DataProperty.getProperty("Stunde")
                            + DataProperty.getProperty("Minute")
                            + "_Tour.txt");


                    file2 = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                            + SystemProperties.getProperty("file.separator")
                            + tempYear + DataProperty.getProperty("Monat")
                            + DataProperty.getProperty("Tag")
                            + DataProperty.getProperty("Stunde")
                            + DataProperty.getProperty("Minute")
                            + "_Tour.cfg");

                    try {
                        if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                            Ausgabedatei = new FileOutputStream(file);
                            Ausgabedatei.write(Buffer.toString().getBytes());
                            Ausgabedatei.close();
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei", "Achtung!", JOptionPane.ERROR_MESSAGE);
                    }

                    try {
                        DataProperty.setProperty("Anzahl Datenpunkte", Integer.toString(n + 1));
                        formatb = new java.text.DecimalFormat("0.00");
 
                        DataProperty.setProperty("Strecke", formatb.format(Strecke / 100.0));

                        DataProperty.setProperty("Dauer", Integer.toString(Zeit));
                        if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                            Ausgabedatei = new FileOutputStream(file2);
                            DataProperty.store(Ausgabedatei, "Tour Eigenschaften: " + tempYear + DataProperty.getProperty("Monat")
                                    + DataProperty.getProperty("Tag")
                                    + DataProperty.getProperty("Stunde")
                                    + DataProperty.getProperty("Minute"));
                            Ausgabedatei.close();

                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei (cfg)", "Achtung!", JOptionPane.ERROR_MESSAGE);
                    }

                }
                i += 16;
            }
        }

    }

    private void ExtractPolarFile(byte[] Data) {

        int Filezeiger = 0;
        int AnzahlBloecke = 0;
        int Intervall = 0;
        int Block;

        int i, j;

        String Dummy;
        String DataString = new String(Data);

        float Stunden;
        float Minuten;
        float Sekunden;
        int Zeitpunkt;
        long RR_Zeit;

        int Ausgangshoehe;
        int Startkilometer;
        int Start_HF;

        int Akt_Hoehe;
        int Akt_HF;
        double Distanz = 0;
        double Rest;
        int Strecke;
        int Cadence;
        double Geschw;
        int gef_Zeit;
        int alt_Zeitpunkt;
        int alt_Hoehe;

        boolean speed = false;
        boolean cadence = false;
        boolean alti = false;
        boolean unit = false;
        boolean exit = false;
        boolean RR = false;
        boolean skip = false;

        short Daten1, Daten2, Daten3, Daten4, Daten5;

        File file = new File("Dummy");
        DataProperty = new java.util.Properties();
        Buffer = new StringBuffer();
        StringBuffer BufferString;

        format = new java.text.DecimalFormat("000000");
        formatneg = new java.text.DecimalFormat("00000");

        DataProperty.setProperty("Computer", "Polar");

        Filezeiger = DataString.indexOf("SMode=") + "SMode=".length();

        Dummy = DataString.substring(Filezeiger, Filezeiger + 8);
        if (Dummy.charAt(0) == '1') {
            speed = true;
        }
        if (Dummy.charAt(1) == '1') {
            cadence = true;
        }
        if (Dummy.charAt(2) == '1') {
            alti = true;
        }
        if (Dummy.charAt(7) == '1') {
            unit = true;
        }

        Filezeiger = DataString.indexOf("Interval=") + "Interval=".length();
        Intervall = Integer.parseInt(DataString.substring(Filezeiger, DataString.indexOf('\n', Filezeiger) - 1));

        if (Intervall == 238) {

            RR = true;

        }

        Filezeiger = DataString.indexOf("Date=") + "Date=".length();
        Dummy = DataString.substring(Filezeiger, Filezeiger + 8);

        DataProperty.setProperty("Jahr", Dummy.substring(0, 4));
        DataProperty.setProperty("Monat", Dummy.substring(4, 6));
        DataProperty.setProperty("Tag", Dummy.substring(6, 8));

        Filezeiger = DataString.indexOf("StartTime=") + "StartTime=".length();
        Dummy = DataString.substring(Filezeiger, Filezeiger + 10);
        DataProperty.setProperty("Stunde", Dummy.substring(0, 2));
        DataProperty.setProperty("Minute", Dummy.substring(3, 5));

        Filezeiger = DataString.indexOf("[Note]") + "[Note]".length();
        Dummy = DataString.substring(Filezeiger + 1, DataString.indexOf('\n', Filezeiger));
        DataProperty.setProperty("Notiz", Dummy);

        Filezeiger = DataString.indexOf("Upper1=") + "Upper1=".length();
        DataProperty.setProperty("obere Hf", DataString.substring(Filezeiger, DataString.indexOf('\n', Filezeiger)));

        Filezeiger = DataString.indexOf("Lower1=") + "Lower1=".length();
        DataProperty.setProperty("untere Hf", DataString.substring(Filezeiger, DataString.indexOf('\n', Filezeiger)));

        Filezeiger = DataString.indexOf("Weight=") + "Weight=".length();
        DataProperty.setProperty("Gewicht", DataString.substring(Filezeiger, DataString.indexOf('\n', Filezeiger)));

        // Handling LapDaten offen
        AnzahlBloecke = 0;
        Zeitpunkt = 0;
        RR_Zeit = 0;
        Strecke = 0;
        Rest = 0;
        Akt_Hoehe = 0;
        Cadence = 0;
        Akt_HF = 0;
        Geschw = 0;
        BufferString = new StringBuffer();

        Filezeiger = DataString.indexOf("[HRData]") + "[HRData]".length() + 1;

        while (Filezeiger != -1 && Filezeiger < Data.length - 5) {
            if (!skip) {
                AnzahlBloecke++;
            }
            exit = false;

            Dummy = DataString.substring(Filezeiger + 1, DataString.indexOf('\n', Filezeiger + 1));

            if (Dummy.indexOf('\t') != -1) {
                Akt_HF = Integer.parseInt(Dummy.substring(0, Dummy.indexOf('\t')));
                if (RR) {
                    RR_Zeit += Akt_HF;
                    Akt_HF = (int) 60000 / Akt_HF;
                }
            } else {
                Akt_HF = Integer.parseInt(Dummy.substring(0, Dummy.length() - 1));
                if (RR) {
                    RR_Zeit += Akt_HF;
                    Akt_HF = (int) 60000 / Akt_HF;
                }
                exit = true;
            }

            Dummy = Dummy.substring(Dummy.indexOf('\t') + 1);
            if (speed && !exit) {
                if (Dummy.indexOf('\t') != -1) {
                    Geschw = Double.parseDouble(Dummy.substring(0, Dummy.indexOf('\t')));
                    Distanz = Geschw / 36 * Intervall / 10;
                    if (unit) {
                        Distanz = Distanz / 0.3048006;
                    }
                    Distanz += Rest;
                    Rest = Distanz % 1;
                    Strecke += Distanz - Rest;
                } else {
                    Geschw = Double.parseDouble(Dummy.substring(0, Dummy.length() - 1));
                    Distanz = Geschw / 36 * Intervall / 10;
                    if (unit) {
                        Distanz = Distanz / 0.3048006;
                    }
                    Distanz += Rest;
                    Rest = Distanz % 1;
                    Strecke += Distanz - Rest;
                    exit = true;
                }
                Dummy = Dummy.substring(Dummy.indexOf('\t') + 1);
            }

            if (cadence && !exit) {
                if (Dummy.indexOf('\t') != -1) {
                    Cadence = Integer.parseInt(Dummy.substring(0, Dummy.indexOf('\t')));
                } else {
                    Cadence = Integer.parseInt(Dummy.substring(0, Dummy.length() - 1));
                    exit = true;
                }
            }
            Dummy = Dummy.substring(Dummy.indexOf('\t') + 1);

            if (alti && !exit) {
                if (Dummy.indexOf('\t') != -1) {
                    Akt_Hoehe = Integer.parseInt(Dummy.substring(0, Dummy.indexOf('\t')));
                } else {
                    Akt_Hoehe = Integer.parseInt(Dummy.substring(0, Dummy.length() - 1));
                    exit = true;
                }
            }

            if (!skip) {

                BufferString = BufferString.append(format.format(Zeitpunkt)).append('\t').append(format.format(Strecke)).append('\t');
                if (Akt_Hoehe < 0) {
                    BufferString = BufferString.append(formatneg.format(Akt_Hoehe)).append('\t');
                } else {
                    BufferString = BufferString.append(format.format(Akt_Hoehe)).append('\t');
                }
                if (Akt_HF < 0) {
                    BufferString = BufferString.append(formatneg.format(Akt_HF)).append('\t');
                } else {
                    BufferString = BufferString.append(format.format(Akt_HF)).append('\t');
                }
                BufferString = BufferString.append(format.format(Cadence)).append('\t');

                BufferString = BufferString.append(format.format(Geschw)).append('\n');
            }

            if (RR) {
                if (Zeitpunkt < (int) ((double) RR_Zeit / 1000.0)) {
                    Zeitpunkt = (int) ((double) RR_Zeit / 1000.0);
                    skip = false;
                } else {
                    skip = true;
                }
            } else {
                Zeitpunkt += Intervall;
            }
            Filezeiger = DataString.indexOf('\n', Filezeiger + 1);

        }

        file = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                + SystemProperties.getProperty("file.separator")
                + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                + DataProperty.getProperty("Tag")
                + DataProperty.getProperty("Stunde")
                + DataProperty.getProperty("Minute")
                + "_Tour.txt");

        file2 = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                + SystemProperties.getProperty("file.separator")
                + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                + DataProperty.getProperty("Tag")
                + DataProperty.getProperty("Stunde")
                + DataProperty.getProperty("Minute")
                + "_Tour.cfg");

        try {
            if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                Ausgabedatei = new FileOutputStream(file);
                Ausgabedatei.write(BufferString.toString().getBytes());
                Ausgabedatei.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei", "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        try {
            DataProperty.setProperty("Anzahl Datenpunkte", Integer.toString(AnzahlBloecke));
            formatb = new java.text.DecimalFormat("0.00");

            DataProperty.setProperty("Strecke", formatb.format(Strecke / (float) 100.0));

            DataProperty.setProperty("Dauer", Integer.toString(Zeitpunkt - Intervall));
            if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                Ausgabedatei = new FileOutputStream(file2);
                DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                        + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                        + DataProperty.getProperty("Tag")
                        + DataProperty.getProperty("Stunde")
                        + DataProperty.getProperty("Minute"));
                Ausgabedatei.close();

// Ermitteln und Abspeichern des Trainingstyps sowie Höhenmeter
                JTourData Dummydata = new JTourData(file.getPath().substring(0, file.getPath().lastIndexOf('.')), this);
                DataProperty.setProperty("Hoehenmeter", "" + Dummydata.ges_Hoehep);

                if (Dummydata.max_Geschw > 25) {
                    DataProperty.setProperty("Typ", "Bike");
                }
                if (Dummydata.max_Geschw <= 25 && Dummydata.max_Geschw > 1) {
                    DataProperty.setProperty("Typ", "Jogging");
                }
                if (Dummydata.max_Geschw <= 1) {
                    DataProperty.setProperty("Typ", "Studio");
                }

                Ausgabedatei = new FileOutputStream(file2);
                DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                        + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                        + DataProperty.getProperty("Tag")
                        + DataProperty.getProperty("Stunde")
                        + DataProperty.getProperty("Minute"));
                Ausgabedatei.close();

            }

        } catch (Exception e) {
        }

    }
    
    
    private void ExtractCSV(byte[] Data) {

        int Filezeiger = 0;
        int AnzahlBloecke = 0;
        int Intervall = 0;
        int Block;

        int i, j;

        String Dummy;
        String DataString = new String(Data);

        float Stunden;
        float Minuten;
        float Sekunden;
        int Zeitpunkt;
        long RR_Zeit;

        int Ausgangshoehe;
        int Startkilometer;
        int Start_HF;

        int Akt_Hoehe;
        int Akt_HF;
        double Distanz = 0;
        double Rest;
        int Strecke;
        int Cadence;
        int Schrittlänge;
        float Temperatur;
        double Geschw;
        int gef_Zeit;
        int alt_Zeitpunkt;
        int alt_Hoehe;

        boolean speed = false;
        boolean cadence = false;
        boolean alti = false;
        boolean unit = false;
        boolean exit = false;
        boolean RR = false;
        boolean skip = false;

        short Daten1, Daten2, Daten3, Daten4, Daten5;

        File file = new File("Dummy");
        DataProperty = new java.util.Properties();
        Buffer = new StringBuffer();
        StringBuffer BufferString;

        format = new java.text.DecimalFormat("0000000");
        formatneg = new java.text.DecimalFormat("000000");

        DataProperty.setProperty("Computer", "Polar_V/M_Serie");

        Filezeiger = DataString.indexOf("VO2max") + "VO2max".length() + 2;  //Start der Daten im CSV; +2 wegen \n

        DataProperty.setProperty("Vorname", DataString.substring(Filezeiger,
                DataString.indexOf(" ", Filezeiger)));
        Filezeiger = DataString.indexOf(" ", Filezeiger) + 1;

        DataProperty.setProperty("Name", DataString.substring(Filezeiger,
                DataString.indexOf(",", Filezeiger)));
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        DataProperty.setProperty("Typ", DataString.substring(Filezeiger,
                DataString.indexOf(",", Filezeiger)));
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        DataProperty.setProperty("Jahr", DataString.substring(Filezeiger + 6, Filezeiger + 10));
        DataProperty.setProperty("Monat", DataString.substring(Filezeiger + 3, Filezeiger + 5));
        DataProperty.setProperty("Tag", DataString.substring(Filezeiger + 0, Filezeiger + 2));
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        DataProperty.setProperty("Stunde", DataString.substring(Filezeiger + 0, Filezeiger + 2));
        DataProperty.setProperty("Minute", DataString.substring(Filezeiger + 3, Filezeiger + 5));
        DataProperty.setProperty("Sekunde", DataString.substring(Filezeiger + 6, Filezeiger + 8));
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        // Dauer überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Strecke", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        //       Filezeiger =  DataString.indexOf(",", Filezeiger+1)+1;
        //durchschnittliche HF überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        //Durchschnittsgeschwindigketi überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        //maximale Geschwindigkeit überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        //maximale pace und durchschnitts pace überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Kalorien", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Fett", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        //durchschnittliche Cadence überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        //durchschnittliche Schrittlänge überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Running-Index", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Belastung", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        //Anstieg überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        //Abstieg überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        //Notiz überspringen
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Größe", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("Gewicht", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("obere Hf", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("untere Hf", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
            DataProperty.setProperty("vo2max", DataString.substring(Filezeiger,
                    DataString.indexOf(",", Filezeiger)));
        }
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        for (i = 0; i < 11; i++) {
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
        }
        Filezeiger++; //\n überspringen
        //       String d = DataString.substring(Filezeiger,
        //                       DataString.indexOf(",", Filezeiger));
        Intervall = Integer.parseInt(DataString.substring(Filezeiger,
                DataString.indexOf(",", Filezeiger)));
        Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

        // Handling LapDaten offen
        AnzahlBloecke = 0;
        Zeitpunkt = 0;

        Strecke = 0;
        Rest = 0;
        Akt_Hoehe = 0;
        Cadence = 0;
        Akt_HF = 0;
        Geschw = 0;
        Schrittlänge = 0;
        Temperatur = 0;
        BufferString = new StringBuffer();
try{
        while (Filezeiger != -1 && Filezeiger < Data.length) {

            AnzahlBloecke++;
            exit = false;
            //Zeitpunkt überspringen
            Filezeiger++; //erstes Komma überspringen
            //Zeitpunkt überspringen
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Akt_HF = Integer.parseInt(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger)));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Geschw = Double.parseDouble(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger)));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            //pace überspringen
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Cadence = Integer.parseInt(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger)));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Akt_Hoehe = Integer.parseInt(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger)));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Schrittlänge = Integer.parseInt(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger)));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Strecke = (int) (Float.parseFloat(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger))));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            if (!DataString.substring(Filezeiger, Filezeiger + 1).equalsIgnoreCase(",")) {
                Temperatur = Float.parseFloat(DataString.substring(Filezeiger,
                        DataString.indexOf(",", Filezeiger)));
            }
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;

            //Power überspringen
            Filezeiger = DataString.indexOf(",", Filezeiger) + 1;
            Filezeiger++; //\n überspringen

            

            BufferString = BufferString.append(format.format(Zeitpunkt)).append('\t')
                    .append(format.format(Strecke)).append('\t');
            if (Akt_Hoehe < 0) {
                BufferString = BufferString.append(formatneg.format(Akt_Hoehe * 10)).append('\t');
            } else {
                BufferString = BufferString.append(format.format(Akt_Hoehe * 10)).append('\t');
            }
            if (Akt_HF < 0) {
                BufferString = BufferString.append(formatneg.format(Akt_HF)).append('\t');
            } else {
                BufferString = BufferString.append(format.format(Akt_HF)).append('\t');
            }
            BufferString = BufferString.append(format.format(Cadence)).append('\t');

            BufferString = BufferString.append(format.format(Schrittlänge)).append('\t');

            BufferString = BufferString.append(format.format(Geschw * 100)).append('\t');

            if (Temperatur >=0){
                BufferString = BufferString.append(format.format(Temperatur * 10)).append('\n');
            } else {
                BufferString = BufferString.append(formatneg.format(Temperatur * 10)).append('\n');
            }
            
            Zeitpunkt += Intervall;
        }
}catch(Exception e){
     JOptionPane.showMessageDialog(null, "Fehler im CSV File", "Achtung!", JOptionPane.ERROR_MESSAGE);
}
        
        if(BufferString.length()==0){
            
          JOptionPane.showMessageDialog(null, "Keine Daten im CSV File gefunden", "Achtung!", JOptionPane.ERROR_MESSAGE);   
            return;
        }
       
        
        
        
        file = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                + SystemProperties.getProperty("file.separator")
                + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                + DataProperty.getProperty("Tag")
                + DataProperty.getProperty("Stunde")
                + DataProperty.getProperty("Minute")
                + "_Tour.txt");

        file2 = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                + SystemProperties.getProperty("file.separator")
                + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                + DataProperty.getProperty("Tag")
                + DataProperty.getProperty("Stunde")
                + DataProperty.getProperty("Minute")
                + "_Tour.cfg");

        try {
            if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                Ausgabedatei = new FileOutputStream(file);
                Ausgabedatei.write(BufferString.toString().getBytes());
                Ausgabedatei.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei", "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        try {
            DataProperty.setProperty("Anzahl Datenpunkte", Integer.toString(AnzahlBloecke));
            formatb = new java.text.DecimalFormat("0.00");

            DataProperty.setProperty("Strecke", formatb.format(Strecke / (float) 1000.0));

            DataProperty.setProperty("Dauer", Integer.toString(Zeitpunkt - Intervall));
            boolean neu = false;
            if ((file2.exists() == true && Properties.getProperty("Daten ueberschreiben", "0").equalsIgnoreCase("1")) || file2.exists() == false) {
                neu = true;
                Ausgabedatei = new FileOutputStream(file2);
                DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                        + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                        + DataProperty.getProperty("Tag")
                        + DataProperty.getProperty("Stunde")
                        + DataProperty.getProperty("Minute"));
                Ausgabedatei.close();

// Ermitteln und Abspeichern des Trainingstyps sowie Höhenmeter
                JTourData Dummydata = new JTourData(file.getPath().substring(0, file.getPath().lastIndexOf('.')), this);
                DataProperty.setProperty("Hoehenmeter", "" + Dummydata.ges_Hoehep);

//                if (Dummydata.max_Geschw > 25) {
//                    DataProperty.setProperty("Typ", "Bike");
//                }
//                if (Dummydata.max_Geschw <= 25 && Dummydata.max_Geschw > 1) {
//                    DataProperty.setProperty("Typ", "Jogging");
//                }
//                if (Dummydata.max_Geschw <= 1) {
//                    DataProperty.setProperty("Typ", "Studio");
//                }
                if (neu) {
                    Ausgabedatei = new FileOutputStream(file2);

                    DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                            + DataProperty.getProperty("Jahr")
                            + DataProperty.getProperty("Monat")
                            + DataProperty.getProperty("Tag")
                            + DataProperty.getProperty("Stunde")
                            + DataProperty.getProperty("Minute"));
                    Ausgabedatei.close();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der cfg Datei  \n  " + e.getStackTrace(), "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

    }
        
     
    private void ExtractHactronicFile(byte[] Data) {

        int Filezeiger = 0;
        int AnzahlBloecke = 0;
        int Block;
        //    byte Buffer;
        int Version = 0;
        int Typ = 0;
        int i, j;
        char test;

        String Dummy;
        String DataString = new String(Data);

        float Stunden;
        float Minuten;
        float Sekunden;
        int Zeitpunkt;

        int Ausgangshoehe;
        int Startkilometer;
        int Start_HF;

        int Akt_Hoehe;
        int Akt_HF;
        int Distanz;
        int VStrecke;
        int VCadence;
        int VTemperatur;
        int gef_Zeit;
        int alt_Zeitpunkt;
        int alt_Hoehe;

        short Daten1, Daten2, Daten3, Daten4, Daten5;

        File file = new File("Dummy");
        DataProperty = new java.util.Properties();
        Buffer = new StringBuffer();
        StringBuffer BufferString;

        format = new java.text.DecimalFormat("000000");
        formatneg = new java.text.DecimalFormat("00000");
        Filezeiger = -1;
        do {
            Filezeiger++;

        } while (Data[Filezeiger] != '\n');

        Filezeiger++;
        Version = Integer.parseInt(DataString.substring(Filezeiger, Filezeiger + 1));
        if (Version == 0) {
            JOptionPane.showMessageDialog(null, "Alte Version von HACtronic!\n Auf Version 1.3 oder höher updaten!", "Achtung!", JOptionPane.ERROR_MESSAGE);
            return;
        } else {

            //          Filezeiger++;
            do {
                Filezeiger++;
            } while (Data[Filezeiger] != '\n');
            Filezeiger++;
            Typ = Integer.parseInt(DataString.substring(Filezeiger, Filezeiger + 1));

            Filezeiger++;

            do {
                Filezeiger++;
            } while (Data[Filezeiger] != '\n');
        }

        Buffer = new StringBuffer();
        do {
            Filezeiger++;

            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');

        DataProperty.setProperty("Titel", Buffer.toString());

        Buffer = new StringBuffer();
        do {
            Filezeiger++;

            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }

        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Startort", Buffer.toString());

        Buffer = new StringBuffer();
        do {
            Filezeiger++;

            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Zielort", Buffer.toString());
        Buffer = new StringBuffer();
        do {
            Filezeiger++;

            Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
        } while (Data[Filezeiger] != '\n');
        //     Buffer = Buffer.append(" ");

        DataProperty.setProperty("Jahr", Buffer.toString().substring(6, 10));
        DataProperty.setProperty("Monat", Buffer.toString().substring(3, 5));
        DataProperty.setProperty("Tag", Buffer.toString().substring(0, 2));
        Buffer = new StringBuffer();
        do {
            Filezeiger++;

            Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Stunde", Buffer.toString().substring(0, 2));
        DataProperty.setProperty("Minute", Buffer.toString().substring(3, 5));
        Buffer = new StringBuffer();

        //Abfragen wieviel Zeilen Notitzen es gibt:
        Dummy = "";
        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');

        i = Integer.parseInt(Buffer.toString());
        Buffer = new StringBuffer();

        if (i == 0) {
            Filezeiger++;
            DataProperty.setProperty("Notiz", "keine");
        } else {
            for (j = 0; j < i; j++) {
                do {
                    Filezeiger++;

                    if (Data[Filezeiger] != '\n') {
                        Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
                    } else {
                        Buffer = Buffer.append(" ");
                    }
                } while (Data[Filezeiger] != '\n');
            }
            DataProperty.setProperty("Notiz", Buffer.toString());
        }

        for (i = 0; i < 11; i++) {
            do {
                Filezeiger++;
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            } while (Data[Filezeiger] != '\n');

        }
        Buffer = new StringBuffer();
        //Typ suchen
        do {
            Filezeiger++;

            //          Buffer = Buffer.append(DataString.substring(Filezeiger,Filezeiger+1));
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');

        i = Integer.parseInt(Buffer.toString());
        Buffer = new StringBuffer();

        if (Typ == 1) {
            if (i == 2) {
                DataProperty.setProperty("Typ", "Bike");
            }
            if (i == 1) {
                DataProperty.setProperty("Typ", "Ski");
            }
            if (i == 3) {
                DataProperty.setProperty("Typ", "Ski & Bike");
            }
            if (i == 0) {
                DataProperty.setProperty("Typ", "Jogging");
            }
        }
        if (Typ == 2) {
            if (i == 3) {
                DataProperty.setProperty("Typ", "Bike 1");
            }
            if (i == 2) {
                DataProperty.setProperty("Typ", "Bike 2");
            }
            if (i == 1) {
                DataProperty.setProperty("Typ", "Unknown");
            }
            if (i == 0) {
                DataProperty.setProperty("Typ", "Jogging");
            }
        }

        for (i = 0; i < 8; i++) {
            do {
                Filezeiger++;
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            } while (Data[Filezeiger] != '\n');

        }

        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Name", Buffer.toString());
        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Vorname", Buffer.toString());
        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Geburtsdatum", Buffer.toString());
        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Verein", Buffer.toString());
        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Gewicht", Buffer.toString());
        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("obere Hf", Buffer.toString());
        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("untere Hf", Buffer.toString());

        Buffer = new StringBuffer();

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("Material", Buffer.toString());
        Buffer = new StringBuffer();

        for (i = 0; i < 15; i++) {
            do {
                Filezeiger++;
            } while (Data[Filezeiger] != '\n');
        }

        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');

        AnzahlBloecke = Integer.parseInt(Buffer.toString());

        byte[] Daten = new byte[AnzahlBloecke * 20];

        Block = 0;

        if (Version == 3) {
            do {
                for (j = 0; j < 20; j++) {
                    Filezeiger++;
                    Daten[Block + j] = Data[Filezeiger];
                }
                Block += 20;
            } while (AnzahlBloecke != Block / 20);
        } else {
            do {
                for (j = 0; j < 16; j++) {
                    Filezeiger++;
                    Daten[(int) Block + j] = Data[Filezeiger];
                }
                Block += 16;
            } while (AnzahlBloecke != Block / 16);
        }

        Filezeiger++;

        Buffer = new StringBuffer();
        do {
            Filezeiger++;
            if (Data[Filezeiger] != '\n') {
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
            }
        } while (Data[Filezeiger] != '\n');
        DataProperty.setProperty("AnzahlMarken", Buffer.toString());

        i = (int) java.lang.Float.parseFloat(Buffer.toString());
        Buffer = new StringBuffer();

        for (j = 0; j < i; j++) {

            Buffer = new StringBuffer();
            do {
                Filezeiger++;
                if (Data[Filezeiger] != '\n') {
                    Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));
                }
            } while (Data[Filezeiger] != '\n');

            Zeitpunkt = Integer.parseInt(Buffer.toString());

            Buffer = new StringBuffer();
            do {
                Filezeiger++;
                Buffer = Buffer.append(DataString.substring(Filezeiger, Filezeiger + 1));

            } while (Data[Filezeiger] != ';');

            do {
                Filezeiger++;
            } while (Data[Filezeiger] != '\n');

            DataProperty.setProperty("Marke " + (j + 1), Integer.toString(Zeitpunkt) + " " + Buffer.toString());

        }

        if (Version == 3) {
            Version = 4;
        } else {
            Version = 0;
        }
        Buffer = new StringBuffer();

        Daten1 = (short) Daten[8 + Version];
        Daten1 += Daten1 < 0 ? 256 : 0;
        Daten2 = (short) Daten[9 + Version];
        Daten2 += Daten2 < 0 ? 256 : 0;
        Ausgangshoehe = Daten1 + 256 * Daten2;
        Daten1 = (short) Daten[4 + Version];
        Daten1 += Daten1 < 0 ? 256 : 0;
        Daten2 = (short) Daten[5 + Version];
        Daten2 += Daten2 < 0 ? 256 : 0;
        Daten3 = (short) Daten[6 + Version];
        Daten3 += Daten3 < 0 ? 256 : 0;
        Daten4 = (short) Daten[7 + Version];
        Daten4 += Daten4 < 0 ? 256 : 0;
        Startkilometer = (Daten1 + 256 * Daten2 + 65536 * Daten3 + 16777216 * Daten4) / 100;
        Start_HF = (short) Daten[10 + Version];
        Start_HF += Start_HF < 0 ? 256 : 0;

        Buffer = Buffer.append(format.format(0)).append('\t').append(format.format(0)).append('\t');
        Buffer = Buffer.append(format.format(Ausgangshoehe)).append('\t');
        Buffer = Buffer.append(format.format(Start_HF)).append('\t');
        Buffer = Buffer.append(format.format(0)).append('\t').append(format.format(0)).append('\n');

        VStrecke = 0;
        alt_Zeitpunkt = 0;
        alt_Hoehe = Ausgangshoehe;
        gef_Zeit = 0;
        BufferString = new StringBuffer(Buffer.toString());
        for (i = 1; i < AnzahlBloecke; i++) {
            Daten1 = (short) Daten[i * (16 + Version) + 0 + Version];
            Daten1 += Daten1 < 0 ? 256 : 0;
            Daten2 = (short) Daten[i * (16 + Version) + 1 + Version];
            Daten2 += Daten2 < 0 ? 256 : 0;
            Daten3 = (short) Daten[i * (16 + Version) + 2 + Version];
            Daten3 += Daten3 < 0 ? 256 : 0;
            Daten4 = (short) Daten[i * (16 + Version) + 3 + Version];
            Daten4 += Daten4 < 0 ? 256 : 0;
            Zeitpunkt = Daten1 + 256 * Daten2 + 65536 * Daten3 + 16777216 * Daten4;

            Daten1 = (short) Daten[i * (16 + Version) + 8 + Version];
            Daten1 += Daten1 < 0 ? 256 : 0;
            Daten2 = (short) Daten[i * (16 + Version) + 9 + Version]; //Daten2 += Daten2<0 ? 256 : 0;
            Akt_Hoehe = (int) (Daten1 + 256 * Daten2);

            Daten1 = (short) Daten[i * (16 + Version) + 4 + Version];
            Daten1 += Daten1 < 0 ? 256 : 0;
            Daten2 = (short) Daten[i * (16 + Version) + 5 + Version];
            Daten2 += Daten2 < 0 ? 256 : 0;
            Daten3 = (short) Daten[i * (16 + Version) + 6 + Version];
            Daten3 += Daten3 < 0 ? 256 : 0;
            Daten4 = (short) Daten[i * (16 + Version) + 7 + Version];
            Daten4 += Daten4 < 0 ? 256 : 0;

            Distanz = (int) ((Daten1 + 256 * Daten2 + 65536 * Daten3 + 16777216 * Daten4));

            Akt_HF = (int) Daten[i * (16 + Version) + 10 + Version];
            Akt_HF += Akt_HF < 0 ? 256 : 0;

            VCadence = (int) Daten[i * (16 + Version) + 11 + Version];
            VCadence += VCadence < 0 ? 256 : 0;

            VTemperatur = (int) Daten[i * (16 + Version) + 12 + Version];

            BufferString = BufferString.append(format.format(Zeitpunkt)).append('\t').append(format.format(Distanz)).append('\t');
            if (Akt_Hoehe < 0) {
                BufferString = BufferString.append(formatneg.format(Akt_Hoehe)).append('\t');
            } else {
                BufferString = BufferString.append(format.format(Akt_Hoehe)).append('\t');
            }
            if (Akt_HF < 0) {
                BufferString = BufferString.append(formatneg.format(Akt_HF)).append('\t');
            } else {
                BufferString = BufferString.append(format.format(Akt_HF)).append('\t');
            }
            BufferString = BufferString.append(format.format(VCadence)).append('\t');
            if (VTemperatur < 0) {
                BufferString = BufferString.append(formatneg.format(VTemperatur)).append('\n');
            } else {
                BufferString = BufferString.append(format.format(VTemperatur)).append('\n');
            }

            VStrecke = Distanz;
            alt_Hoehe = Akt_Hoehe;

            alt_Zeitpunkt = Zeitpunkt;

        }

        file = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                + SystemProperties.getProperty("file.separator")
                + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                + DataProperty.getProperty("Tag")
                + DataProperty.getProperty("Stunde")
                + DataProperty.getProperty("Minute")
                + "_Tour.txt");

        file2 = new File(Properties.getProperty("data.dir", Properties.getProperty("working.dir"))
                + SystemProperties.getProperty("file.separator")
                + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                + DataProperty.getProperty("Tag")
                + DataProperty.getProperty("Stunde")
                + DataProperty.getProperty("Minute")
                + "_Tour.cfg");

        try {
            if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                Ausgabedatei = new FileOutputStream(file);
                Ausgabedatei.write(BufferString.toString().getBytes());
                Ausgabedatei.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO-Fehler bei Speicherung der Tour Datei", "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        try {

            DataProperty.setProperty("Anzahl Datenpunkte", Integer.toString(AnzahlBloecke));
            formatb = new java.text.DecimalFormat("0.00");
            //                   DataProperty.setProperty("Strecke",java.lang.Float.toString((float)Strecke/100));
            DataProperty.setProperty("Strecke", formatb.format(VStrecke / (float) 100.0));

            DataProperty.setProperty("Dauer", Integer.toString(alt_Zeitpunkt));
            if (file2.exists() == true && Integer.parseInt(Properties.getProperty("Daten ueberschreiben")) == 1 || file2.exists() == false) {
                Ausgabedatei = new FileOutputStream(file2);
                DataProperty.store(Ausgabedatei, "Tour Eigenschaften: "
                        + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat")
                        + DataProperty.getProperty("Tag")
                        + DataProperty.getProperty("Stunde")
                        + DataProperty.getProperty("Minute"));
                Ausgabedatei.close();
            }

        } catch (Exception e) {
        }

    }



    public String HMS(int Sekunden) {
        int Stunden = 0;
        int Minuten = 0;
        java.text.DecimalFormat format = new java.text.DecimalFormat("00");
        StringBuffer Buffer = new StringBuffer();
        Stunden = Sekunden / 3600;
        Minuten = (Sekunden - Stunden * 3600) / 60;
        Sekunden = Sekunden - Stunden * 3600 - Minuten * 60;
        Buffer = Buffer.append(format.format(Stunden)).append(':').append(format.format(Minuten)).append(':').append(format.format(Sekunden));
        return Buffer.toString();
    }

    public int ToSec(String HMS) {

        StringTokenizer st = new StringTokenizer(HMS, " :");
        if (st.countTokens() < 3) {
            return (-1);
        }
        int Stunden;
        int Minuten;
        int Sekunden;
        //     int Offset = HMS.length()-8; //für Stunden >99
        try {
            Stunden = Integer.parseInt(st.nextToken()); //HMS.substring(0,2+Offset));
            Minuten = Integer.parseInt(st.nextToken()); //HMS.substring(3+Offset,5+Offset));
            Sekunden = Integer.parseInt(st.nextToken()); //HMS.substring(6+Offset,8+Offset));
        } catch (Exception e) {
            //         throw e ;
            //JOptionPane.showMessageDialog(null,"Fehler beim Zeitwert!","Achtung!", JOptionPane.ERROR_MESSAGE);

            return (-1);
        }
        return (Sekunden + 60 * Minuten + 3600 * Stunden);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //     new JCicloTronic ().show ();



        if (args.length != 0 && args[0].equalsIgnoreCase("nodebug")) {
            debug = false;
        } else {
            debug = true;
        }
        JCicloTronic Frame = new JCicloTronic();
        Frame.setVisible(true);
     

        int x = 0, y = 0;
        try {
            y = Integer.parseInt(Frame.Properties.getProperty("Screenheight", new Double(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50).toString()));
            x = Integer.parseInt(Frame.Properties.getProperty("Screenwidth", new Double(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 50).toString()));
        } catch (Exception e) {
        };

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        if (x > (int) width) {
            x = (int) width;
        }
        if (y > (int) height) {
            y = (int) height;
        }
        if (x > width - 200 || y > height - 200) {
            Frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            Frame.setPreferredSize(new Dimension((int)width - 200, (int)height - 200));
        }else    Frame.setSize(x, y);
 
   Frame.ChangeModel();
 
     //  Frame.repaint();

     
    }

    private void Update_XYGraphik() {

          
        ChartPanel Panelb;
        Panelb = (ChartPanel) xygraphik;
        boolean nograph = false;
        if (Panelb == null) {
            nograph = true;
        }

        Update_Graphik_paint = false;
        Graphik_Sub_Panel.removeAll();
         int j = Auswahl_Graphik.getSelectedIndex();
          try {
        if (graphik != null) {
            graphik = null;
        }

  
 
        
        if (Statistikhandle.TourData[j].gefahreneZeit==0) Graphik_Radio_Zeit.setSelected(true);
                
        
        graphik = new XYGraphik();
        xygraphik = graphik.StartGraphik(this);

        if (xygraphik != null) {
            Graphik_Sub_Panel.add(xygraphik);

            xygraphik.setSize(Graphik_Sub_Panel.getSize());

        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update_XYGraphik\nFehler: " + e, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        

  
        
        Panelb = (ChartPanel) xygraphik;
        if (!nograph && !nozoom) {
            if (Graphik_Radio_Strecke.isSelected() == true) {
                Panelb.getChart().getXYPlot().getDomainAxis().setLowerBound(graph_min);
                Panelb.getChart().getXYPlot().getDomainAxis().setUpperBound(graph_max);
            } else {
                if (j != 0) {

                    Stunden = (int) (graph_min / 3600);
                    Minuten = (int) ((graph_min - Stunden * 3600) / 60);
                    Sekunden = (int) (graph_min - Stunden * 3600 - Minuten * 60);

                    Minuten = Minuten + Statistikhandle.TourData[j].StartMinuten;
                    if (Minuten >= 60) {
                        Minuten -= 60;
                        Stunden++;
                    }
                    Stunden = Stunden + Statistikhandle.TourData[j].StartStunden;

                    offsetsecond = new Second(Sekunden,
                            Minuten,
                            Stunden,
                            Statistikhandle.TourData[j].Tag,
                            Statistikhandle.TourData[j].Monat,
                            Statistikhandle.TourData[j].Jahr);
                    Panelb.getChart().getXYPlot().getDomainAxis().setLowerBound(offsetsecond.getFirstMillisecond());

                    Stunden = (int) (graph_max / 3600);
                    Minuten = (int) ((graph_max - Stunden * 3600) / 60);
                    Sekunden = (int) (graph_max - Stunden * 3600 - Minuten * 60);

                    Minuten = Minuten + Statistikhandle.TourData[j].StartMinuten;
                    if (Minuten >= 60) {
                        Minuten -= 60;
                        Stunden++;
                    };
                    Stunden = Stunden + Statistikhandle.TourData[j].StartStunden;

                    offsetsecond = new Second(Sekunden,
                            Minuten,
                            Stunden,
                            Statistikhandle.TourData[j].Tag,
                            Statistikhandle.TourData[j].Monat,
                            Statistikhandle.TourData[j].Jahr);
                    Panelb.getChart().getXYPlot().getDomainAxis().setUpperBound(offsetsecond.getFirstMillisecond());

                }
                if (j == 0) {
                    Stunden = (int) (graph_min / 3600);
                    Minuten = (int) ((graph_min - Stunden * 3600) / 60);
                    Sekunden = (int) (graph_min - Stunden * 3600 - Minuten * 60);

                    Minuten = Minuten + Statistikhandle.TourData[j].StartMinuten;
                    if (Minuten >= 60) {
                        Minuten -= 60;
                        Stunden++;
                    };
                    Stunden = Stunden + Statistikhandle.TourData[j].StartStunden;

                    offsetsecond = new Second(Sekunden,
                            Minuten,
                            Stunden,
                            1,
                            1,
                            1900);
                    Panelb.getChart().getXYPlot().getDomainAxis().setLowerBound(offsetsecond.getFirstMillisecond());

                    Stunden = (int) (graph_max / 3600);
                    Minuten = (int) ((graph_max - Stunden * 3600) / 60);
                    Sekunden = (int) (graph_max - Stunden * 3600 - Minuten * 60);

                    Minuten = Minuten + Statistikhandle.TourData[j].StartMinuten;
                    if (Minuten >= 60) {
                        Minuten -= 60;
                        Stunden++;
                    }
                    Stunden = Stunden + Statistikhandle.TourData[j].StartStunden;

                    offsetsecond = new Second(Sekunden,
                            Minuten,
                            Stunden,
                            1,
                            1,
                            1900);
                    Panelb.getChart().getXYPlot().getDomainAxis().setUpperBound(offsetsecond.getFirstMillisecond());

                }

            }

        } else {
            nozoom = false;
            Save_Min_Max(j);
            alteAuswahl = j;
        }

        repaint();

    }

    private void UpdateHistogram() {

        jPanel16_HistoHF.removeAll();
        jPanel17_HistoHM.removeAll();
        jPanel18_HistoSP.removeAll();
        jPanel19_HistoCd.removeAll();

        //      try {
        if (HistoGram != null) {
            HistoGram = null;
        }
        HistoGram = new JHistogram(this);

        HFHistogramm = HistoGram.StartHistoHf(this);
        if (HFHistogramm != null) {
            jPanel16_HistoHF.add(HFHistogramm);

        }
        if (Statistikhandle.TourData[Auswahl_Histogramm.getSelectedIndex()].av_Schritt_länge != 0) {
            HMHistogramm = HistoGram.StartHistoSchrittlänge(this);
        } else {
            HMHistogramm = HistoGram.StartHistoHm(this);
        }
        if (HMHistogramm != null) {
            jPanel17_HistoHM.add(HMHistogramm);
        }

        SPHistogramm = HistoGram.StartHistoSp(this);
        if (SPHistogramm != null) {
            jPanel18_HistoSP.add(SPHistogramm);
    

        }

        CdHistogramm = HistoGram.StartHistoCd(this);
        if (CdHistogramm != null) {
            jPanel19_HistoCd.add(CdHistogramm);
            }

        if (Auswahl_Statistik.getSelectedIndex() != 0) {
            jLabel26_Histotitel.setText("<html>" + Statistikhandle.TourData[Auswahl_Statistik.getSelectedIndex()].DataProperty.getProperty("Titel", "") + "</html>");
        } else {
            jLabel26_Histotitel.setText("");
        }
        repaint();

    }

    private void UpdateInfos() {

        Update_Info = false;
        int AnzahlMarken;
        String dummy;
        Update_Info = false;

        if (Auswahl_Info.getSelectedIndex() == 0) {
            Info_Titel.setText("");
            Info_Notiz.setText("");
            Info_Startort.setText("");
            Info_Zielort.setText("");
            Info_Vorname.setText("");
            Info_Name.setText("");
            Info_GebTag.setText("");
            Info_Gewicht.setText("");
            Info_Verein.setText("");
            Info_Materialgewicht.setText("");//Materialgewicht
            Info_Material.setText("");//Materieal
            jLabel24Uhrzeit.setText("");
            jLabel65Typ.setText("");
            Info_Track_Log.setText("");

            Update = false;
            //          jComboBox5MarkenText.removeAllItems();
//            Info_MarkenZeit.removeAllItems();

            Update = true;
        } else {
            Info_Titel.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Titel", ""));
            Info_Notiz.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Notiz", ""));
            Info_Startort.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Startort", ""));
            Info_Zielort.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Zielort", ""));
            Info_Vorname.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Vorname", ""));
            Info_Name.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Name", ""));
            Info_GebTag.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Geburtsdatum", ""));
            Info_Gewicht.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Gewicht", ""));
            Info_Verein.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Verein", ""));
            Info_Materialgewicht.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Materialgewicht", ""));//Materialgewicht
            Info_Material.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Material", ""));//Materieal
            Info_Track_Log.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("GoogleEarth", ""));
            jLabel24Uhrzeit.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Stunde", "") + ":" + Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Minute", ""));
            jLabel65Typ.setText(Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].DataProperty.getProperty("Typ", ""));
//            AnzahlMarken = Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].AnzahlMarken;
            Update = false;
//            Info_MarkenZeit.removeAllItems();
//            if (AnzahlMarken != 0) {
//                for (int i = 0; i < AnzahlMarken; i++) {
//                    dummy = Statistikhandle.TourData[Auswahl_Info.getSelectedIndex()].Marken[i];
//                    while (!CheckComboEntry(Info_MarkenZeit, dummy)) {
//                        dummy += " ";
//                    }
//                    Info_MarkenZeit.addItem(dummy);
//                }
//                Info_MarkenZeit.setSelectedIndex(0);
//                oldselection = 0;
//            }
            Update = true;
        }
    }

    private void Draw_Map() {

        Update_Map_paint = false;

        Map_internal_Panel.removeAll();
        if (mapKit != null) {

            mapKit.removeAll();
            mapKit = null;
        }

       mapKit = new org.jxmapviewer.JXMapKit();
       
        mapKit.setName("mapKit");

        mapKit.setPreferredSize(Map_internal_Panel.getSize());

        Map_internal_Panel.add(mapKit, java.awt.BorderLayout.CENTER);

    //    mapKit.setDefaultProvider(DefaultProviders.OpenStreetMaps);
    if(Map_Type.getSelectedIndex()==0)
         mapKit.getMainMap().setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
    if(Map_Type.getSelectedIndex()==1)
        mapKit.getMainMap().setTileFactory(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP))); 
    if(Map_Type.getSelectedIndex()==2)
        mapKit.getMainMap().setTileFactory(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE))); 
    if(Map_Type.getSelectedIndex()==3)
        mapKit.getMainMap().setTileFactory(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID))); 
 
        
        int j = Auswahl_Map.getSelectedIndex();

        if (j == 0) {
            this.LoadGoogleEarth.setEnabled(false);
            this.Kein_kmz_text.setText("Übersicht");
            this.jLabel_map_streckenlänge.setText("");

        } else {
            if (new java.io.File(Statistikhandle.TourData[Auswahl_Map.getSelectedIndex()].DataProperty.getProperty("GoogleEarth", "")).exists() == false
                    ||Statistikhandle.TourData[Auswahl_Map.getSelectedIndex()].GeoDataArray.isEmpty() ) {
                this.LoadGoogleEarth.setEnabled(false);
                this.Kein_kmz_text.setText("kein Log File");
                this.jLabel_map_streckenlänge.setText("");
                mapKit.removeAll();
                return;
            }
            this.LoadGoogleEarth.setEnabled(true);

            this.Kein_kmz_text.setText("<html>" + Statistikhandle.TourData[Auswahl_Map.getSelectedIndex()].DataProperty.getProperty("Titel", "") + "</html>");

        }

        int Datenp = Statistikhandle.TourData[j].Datenpunkte;

        from_x = 0;
        to_x = 999999999;
        mark_x = 0;
        if (j == 0) {

            int Anzahl_Kurven = Integer.parseInt(Properties.getProperty("AnzahlKurven", "5")) + 1;

            if (Anzahl_Kurven > Auswahl_Map.getItemCount()) {
                Anzahl_Kurven = Auswahl_Map.getItemCount();
            }
            map_x_max = Statistikhandle.TourData[1].map_x_max;
            map_x_min = map_x_max;
            map_y_max = Statistikhandle.TourData[1].map_y_max;
            map_y_min = map_y_max;

            for (int i = 1; i < Anzahl_Kurven; i++) {
                if (Statistikhandle.TourData[i].map_x_max > map_x_max) {
                    map_x_max = Statistikhandle.TourData[i].map_x_max;
                }
                if (Statistikhandle.TourData[i].map_x_min < map_x_min) {
                    map_x_min = Statistikhandle.TourData[i].map_x_min;
                }
                if (Statistikhandle.TourData[i].map_y_max > map_y_max) {
                    map_y_max = Statistikhandle.TourData[i].map_y_max;
                }
                if (Statistikhandle.TourData[i].map_y_min < map_y_min) {
                    map_y_min = Statistikhandle.TourData[i].map_y_min;
                }

            }

        }

        if (j != 0
                && Auswahl_Map.getSelectedIndex() <= Integer.parseInt(Properties.getProperty("AnzahlKurven", "5"))) {
// Markierung aus Zoombereich auslesen
            int i = 0;
            if (Graphik_Radio_Strecke.isSelected() == true) {

                while (Statistikhandle.TourData[j].Strecke_gesZeit[i] < graph_min && i + 1 < Datenp) {
                    i++;
                }
                from_x = Statistikhandle.TourData[j].Strecke_gesZeit[i];
                while (Statistikhandle.TourData[j].Strecke_gesZeit[i] < graph_max && i + 1 < Datenp) {
                    i++;
                }

                to_x = Statistikhandle.TourData[j].Strecke_gesZeit[i];
            } else {

                while (Statistikhandle.TourData[j].gesZeit[i] < graph_min && i + 1 < Datenp) {
                    i++;
                }

                from_x = Statistikhandle.TourData[j].Strecke_gesZeit[i];
                while (Statistikhandle.TourData[j].gesZeit[i] < graph_max && i + 1 < Datenp) {
                    i++;
                }

                to_x = Statistikhandle.TourData[j].Strecke_gesZeit[i];
            }
// Streckenmarkierung (crosshair) auslesen
            
            i = 0;
            if (Graphik_Radio_Strecke.isSelected() == true) {

                while (Statistikhandle.TourData[j].Strecke_gesZeit[i] < graph_crosshair && i + 1 < Datenp) {
                    i++;
                }
                mark_x = Statistikhandle.TourData[j].Strecke_gesZeit[i];
                
            } else {

                while (Statistikhandle.TourData[j].gesZeit[i] < graph_crosshair && i + 1 < Datenp) {
                    i++;
                }

                mark_x = Statistikhandle.TourData[j].Strecke_gesZeit[i];
              
            }

            
            double distance = 0;
            int selection = 0;

            //int selection = Auswahl_Map.getSelectedIndex() - 1;
            if (Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(0)) != 0) {
                map_y_max = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(0));
                map_y_min = map_y_max;
            }
            if (Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(1)) != 0) {
                map_x_max = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(1));
                map_x_min = map_x_max;
            }
            do {
                distance = distance + distFrom(Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1)),
                        Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection)),
                        Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 3)),
                        Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 2)));
                selection = selection + 2;

            } while (from_x > distance && selection < Statistikhandle.TourData[j].GeoDataArray.size() - 4);

            map_y_max = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection));
            map_y_min = map_y_max;
            map_x_max = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1));
            map_x_min = map_x_max;

            do {
                distance = distance + distFrom(Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1)),
                        Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection)),
                        Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 3)),
                        Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 2)));

                if (map_y_max < Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection))) {
                    map_y_max = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection));
                }
                if (map_y_min > Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection))) {
                    map_y_min = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection));
                }
                if (map_x_max < Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1))) {
                    map_x_max = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1));
                }
                if (map_x_min > Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1))) {
                    map_x_min = Double.parseDouble(Statistikhandle.TourData[j].GeoDataArray.get(selection + 1));
                }

                selection = selection + 2;
            } while (to_x > distance && selection < Statistikhandle.TourData[j].GeoDataArray.size() - 4);

        }

        mapKit.setCenterPosition(new GeoPosition(((map_x_max + map_x_min) / 2), ((map_y_max + map_y_min) / 2)));

        Point2D pointa = mapKit.getMainMap().convertGeoPositionToPoint(new GeoPosition(map_x_max, map_y_max));
        Point2D pointb = mapKit.getMainMap().convertGeoPositionToPoint(new GeoPosition(map_x_min, map_y_min));
        double rectX = Map_internal_Panel.getWidth();
        double rectY = Map_internal_Panel.getHeight();

        int maxzoom = mapKit.getMainMap().getTileFactory().getInfo().getMaximumZoomLevel();
        double deltaX = 0;
        double deltaY = 0;
        for (int i = 0; i < maxzoom; i++) {
 
        mapKit.getMainMap().setZoom(maxzoom-i);  
        pointa = mapKit.getMainMap().convertGeoPositionToPoint(new GeoPosition(map_x_max, map_y_max));
        pointb = mapKit.getMainMap().convertGeoPositionToPoint(new GeoPosition(map_x_min, map_y_min));
        deltaX = pointa.getX()- pointb.getX();
        deltaY = pointb.getY()- pointa.getY();
            if (rectX <  deltaX || rectY <  deltaY) {
            mapKit.getMainMap().setZoom(maxzoom-i+1);  
                break;
            }

        }


        if (Auswahl_Map.getSelectedIndex() != 0) {
            this.jLabel27.setEnabled(true);
            this.jLabel_map_streckenlänge.setText(Statistikhandle.TourData[Auswahl_Map.getSelectedIndex()].map_Streckenlänge + " km");
        } else {
            this.jLabel27.setEnabled(false);
        }

      

        read_Waypoint(); 
        
        DefaultWaypoint Mark = new DefaultWaypoint(Markierung);
        
      
        
        Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(new DefaultWaypoint( Markierung)));
      
            
        WaypointPainter waypointPainter = new WaypointPainter();

        waypointPainter.setWaypoints(waypoints); 
        
        ArrayList painters = new ArrayList();


        painters.add(lineOverlay); 
        
        painters.add(waypointPainter);



        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);

   
        mapKit.getMainMap().setOverlayPainter(painter);
        
        
        repaint();
        Update_Map_paint = true;

    }
    
    void read_Waypoint() {
        Markierung = new GeoPosition(0, 0);
     
        if (Auswahl_Map.getSelectedIndex()==0) return;
  
        double Strecke = 0;

        int j = Auswahl_Map.getSelectedIndex() - 1;

        for (int i = 0; i < Statistikhandle.TourData[j + 1].GeoDataArray.size(); i = i + 2) {
            if (i != 0) {
                Strecke = Strecke + distFrom(Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i - 1)),
                        Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i - 2)),
                        Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i + 1)),
                        Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i)));

            }

            if (Strecke > mark_x && Markierung.getLatitude() == 0) {
                Markierung = new GeoPosition(Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i + 1)),
                        Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i)));
                break;
            }
        }

    }
    
    
    
    Painter<JXMapViewer> lineOverlay = new Painter<JXMapViewer>() {

        
        
        @Override
        public void paint(Graphics2D g, JXMapViewer map, int w, int h) {

            g = (Graphics2D) g.create();

            Rectangle rect = mapKit.getMainMap().getViewportBounds();
            g.translate(-rect.x, -rect.y);

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(5));

            boolean single = false;
            int selected = Auswahl_Map.getSelectedIndex();
            if (selected == 0) {
                single = false;
            } else {
                single = true;
            }

            double geoX;
            double geoY;
            
            

            double Strecke = 0;

            int lastX = -1;
            int lastY = -1;

            int Anzahl_Kurven = Integer.parseInt(Properties.getProperty("AnzahlKurven", "5")) + 1;

            if (Anzahl_Kurven > Auswahl_Map.getItemCount()) {
                Anzahl_Kurven = Auswahl_Map.getItemCount() - 1;
            }
            if (single) {
                Anzahl_Kurven = 1;
            }
            int j = 0;
            Color color = Color.GRAY;
            for (int m = 0; m < Anzahl_Kurven; m++) {
                if (single) {
                    j = Auswahl_Map.getSelectedIndex() - 1;
                } else {
                    j = m;
                }
                Strecke = 0;

                lastX = -1;
                lastY = -1;
                color = getColor(m + 1);
            
                for (int i = 0; i < Statistikhandle.TourData[j + 1].GeoDataArray.size(); i = i + 2) {
                    if (i != 0) {
                        Strecke = Strecke + distFrom(Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i - 1)),
                                Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i - 2)),
                                Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i + 1)),
                                Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i)));

                    }
                    
                                
                    if (Strecke < from_x || Strecke > to_x) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(color);
                    }

                    geoX = Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i + 1));
                    geoY = Double.parseDouble(Statistikhandle.TourData[j + 1].GeoDataArray.get(i));

                    Point2D pt = mapKit.getMainMap().getTileFactory().geoToPixel(new GeoPosition(geoX, geoY), mapKit.getMainMap().getZoom());

                    if (lastX != -1 && lastY != -1) {
                        g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                    }
                    lastX = (int) pt.getX();
                    lastY = (int) pt.getY();
                }
            }
            
            g.dispose();
            
   
        }
        
        
  
            
        
        
    };


  
  public boolean CheckComboEntry(JComboBox ComboBox, String Text) {
        int i;
        for (i = 0; i < ComboBox.getItemCount(); i++) {
            if (ComboBox.getItemAt(i).toString().equals(Text)) {
                return (false);
            }
        }
        return true;
    }

    class DirFilter implements FilenameFilter {

        String afn;

        DirFilter(String afn) {
            this.afn = afn;
        }

        public boolean accept(File dir, String name) {
            // Strip path information:
            String f = new File(name).getName();
            return f.indexOf(afn) != -1;
        }
    } 

    
//    public void SortMarken(javax.swing.JComboBox Box) {
//        int i, j;
//        int Eintragungen = Box.getItemCount();
//        boolean Enthalten = false;
//        String dummy[] = new String[Eintragungen];
//        for (i = 0; i < Eintragungen; i++) {
//            dummy[i] = Box.getItemAt(i).toString();
//        }
//        Update = false;
//        Box.removeAllItems();
//        Box.addItem(dummy[0]);
//        for (i = 1; i < Eintragungen; i++) {
//            Enthalten = false;
//            for (j = 0; j < Box.getItemCount(); j++) {
//                if (ToSec(dummy[i]) < ToSec(Box.getItemAt(j).toString())) {
//                    Box.insertItemAt(dummy[i], j);
//                    Enthalten = true;
//                    break;
//                }
//            }
//            if (Enthalten == false) {
//                Box.addItem(dummy[i]);
//            }
//        }
//        Update = true;
//
//    }

        private void UpdateJahresuebersicht() {
        jPanel17Übersichtchart.removeAll();
        if (Uebersicht == null) {
            return;
        }
        try {

            Jahresüberblick = Uebersicht.Update_Uebersicht(this);
            if (Jahresüberblick != null) {
                jPanel17Übersichtchart.add(Jahresüberblick);

                Jahresüberblick.setSize(jPanel17Übersichtchart.getSize());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "UpdateJahresübersicht\nFehler: " + e, "Achtung!", JOptionPane.ERROR_MESSAGE);
        }

        jPanel17Übersichtchart.updateUI();
        repaint();

    }

    ;
  
  
    private static Color getColor(int Farbe) {

        Color Colour = Color.BLACK;
        Farbe = Farbe % 8;
        switch (Farbe) {
            case 2:
                Colour = Color.BLACK;
                break;
            case 1:
                Colour = Color.BLUE;
                break;
            case 0:
                Colour = Color.RED;
                break;
            //     case 3: Colour = Color.GREEN; break;
            case 4:
                Colour = Color.MAGENTA;
                break;
            case 5:
                Colour = Color.ORANGE;
                break;
            case 3:
                Colour = Color.PINK;
                break;
//            case 7: Colour = Color.CYAN; break;
            case 6:
                Colour = Color.YELLOW;
                break;
            case 7:
                Colour = Color.WHITE;
                break;

        }
        return Colour;
    }

    private String tokml(int j) {

        StringBuffer temp = new StringBuffer();

        temp = temp.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<kml xmlns=\"http://www.opengis.net/kml/2.2\"\n")
                .append("xmlns:gx=\"http://www.google.com/kml/ext/2.2\"\n")
                .append("xmlns:kml=\"http://www.opengis.net/kml/2.2\"\n")
                .append("xmlns:atom=\"http://www.w3.org/2005/Atom\">\n")
                .append("<Document>\n")
                .append("<Style id=\"Line_2_2\">\n")
                .append("<LineStyle>\n<color>ffff0000</color>\n<width>3</width>\n</LineStyle>\n")
                .append("</Style>\n")
                .append("<Style id=\"Line_2_20\">\n")
                .append("<LineStyle>\n<color>ffff0000</color>\n<width>3</width>\n</LineStyle>\n")
                .append("</Style>\n")
                .append("<StyleMap id=\"Line_2_21\">\n")
		.append("<Pair>\n<key>normal</key>\n")
		.append("<styleUrl>#Line_2_2</styleUrl>\n</Pair>\n")
		.append("<Pair>\n<key>highlight</key>\n")
		.append("<styleUrl>#Line_2_20</styleUrl>\n</Pair>\n </StyleMap>\n")
		.append("<Placemark>\n")
                .append("<styleUrl>#Line_2_21</styleUrl>\n")
                .append("<LineString>\n<coordinates>\n");

        for (int i = 0; i < Statistikhandle.TourData[j].GeoDataArray.size(); i = i + 2) {
            temp = temp.append(Statistikhandle.TourData[j].GeoDataArray.get(i)).append(",")
                    .append(Statistikhandle.TourData[j].GeoDataArray.get(i+1)).append(",0\n");
        }
        temp = temp.append("</coordinates>\n").append("</LineString>\n")
                .append("</Placemark>\n").append("</Document>").append("</kml>");

        return temp.toString();

    }


    
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {

        double Radius = 6369.63; //in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Radius * c;

    }
    
  
    
    public static void setFontSizeGlobal(String font, int size) {
        for (Enumeration e = UIManager.getDefaults().keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof Font) {
                Font f = (Font) value;

                UIManager.put(key, new javax.swing.plaf.FontUIResource(font, f.getStyle(), size));
            }
        }
    }
    
    
    public void applyChartTheme(JFreeChart chart) {

        final StandardChartTheme chartTheme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();

        final Font extraLargeFont = new Font(Hauptfenster.getFont().getFontName(), Hauptfenster.getFont().getStyle(), (int) (FontSize * 1.2));
        final Font largeFont = new Font(Hauptfenster.getFont().getFontName(), Hauptfenster.getFont().getStyle(), (int) (FontSize * 1));
        final Font regularFont = new Font(Hauptfenster.getFont().getFontName(), Hauptfenster.getFont().getStyle(), (int) (FontSize * 0.8));
        final Font smallFont = new Font(Hauptfenster.getFont().getFontName(), Hauptfenster.getFont().getStyle(), (int) (FontSize * 0.4));

        chartTheme.setExtraLargeFont(extraLargeFont);
        chartTheme.setLargeFont(largeFont);
        chartTheme.setRegularFont(regularFont);
        chartTheme.setSmallFont(smallFont);

        chartTheme.apply(chart);

    }
   
    public void setFileChooserFont(Component[] comp) {
        for (int x = 0; x < comp.length; x++) {
            if (comp[x] instanceof Container) {
                setFileChooserFont(((Container) comp[x]).getComponents());
            }
            try {
                comp[x].setFont(new Font(Font, 0, FontSize));
            } catch (Exception e) {
            }//do nothing
        }
    }

   private void Save_Min_Max(int j){
     
       ChartPanel Panelb = (ChartPanel) xygraphik;
       if (Panelb != null) {

           graph_min = Panelb.getChart().getXYPlot().getDomainAxis().getLowerBound();
           graph_max = Panelb.getChart().getXYPlot().getDomainAxis().getUpperBound();
           graph_crosshair = Panelb.getChart().getXYPlot().getDomainCrosshairValue();

           if (j != 0 && Graphik_Radio_Strecke.isSelected() == false) {
               Stunden = (int) (Statistikhandle.TourData[j].gesZeit[0] / 3600);
               Minuten = (int) ((Statistikhandle.TourData[j].gesZeit[0] - Stunden * 3600) / 60);
               Sekunden = (int) (Statistikhandle.TourData[j].gesZeit[0] - Stunden * 3600 - Minuten * 60);

               Minuten = Minuten + Statistikhandle.TourData[j].StartMinuten;
               if (Minuten >= 60) {
                   Minuten -= 60;
                   Stunden++;
               }
               Stunden = Stunden + Statistikhandle.TourData[j].StartStunden;

               offsetsecond = new Second(Sekunden, Minuten, Stunden,
                       Statistikhandle.TourData[j].Tag,
                       Statistikhandle.TourData[j].Monat,
                       Statistikhandle.TourData[j].Jahr);
               graph_min = (graph_min - offsetsecond.getFirstMillisecond()) / 1000;
               graph_max = (graph_max - offsetsecond.getFirstMillisecond()) / 1000;
               graph_crosshair = (graph_crosshair - offsetsecond.getFirstMillisecond()) / 1000;
           }
           if (j == 0 && Graphik_Radio_Strecke.isSelected() == false) {
               Stunden = 0;
               Minuten = 0;
               Sekunden = 0;

               offsetsecond = new Second(Sekunden, Minuten, Stunden,
                       1,
                       1,
                       1900);
               graph_min = (graph_min - offsetsecond.getFirstMillisecond()) / 1000;
               graph_max = (graph_max - offsetsecond.getFirstMillisecond()) / 1000;
               graph_crosshair = (graph_crosshair - offsetsecond.getFirstMillisecond()) / 1000;
           }

       }
          
   }
   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox Auswahl_Graphik;
    public javax.swing.JComboBox Auswahl_Histogramm;
    public javax.swing.JComboBox Auswahl_Info;
    public javax.swing.JComboBox Auswahl_Map;
    public javax.swing.JComboBox Auswahl_Statistik;
    public javax.swing.JComboBox Auswahl_Übersicht;
    private javax.swing.JComboBox Datenliste_Jahr;
    private javax.swing.JComboBox Datenliste_Monat;
    public javax.swing.JPanel Datenliste_Panel;
    private javax.swing.JComboBox Datenliste_TourTyp;
    private javax.swing.JComboBox Datenliste_Zeitabschnitt;
    private javax.swing.JScrollPane Datenliste_scroll_Panel;
    private javax.swing.JTextField Datenliste_search;
    private javax.swing.JButton Datenliste_searchButton;
    public javax.swing.JTable Datentabelle;
    private javax.swing.JPanel Graphik_Panel;
    public javax.swing.JRadioButton Graphik_Radio_Strecke;
    public javax.swing.JRadioButton Graphik_Radio_Zeit;
    private javax.swing.JPanel Graphik_Sub_Panel;
    public javax.swing.JCheckBox Graphik_check_Abstand;
    public javax.swing.JCheckBox Graphik_check_Cadence;
    public javax.swing.JCheckBox Graphik_check_Geschwindigkeit;
    public javax.swing.JCheckBox Graphik_check_HF;
    public javax.swing.JCheckBox Graphik_check_Höhe;
    public javax.swing.JCheckBox Graphik_check_Schrittlänge;
    public javax.swing.JCheckBox Graphik_check_Steigung_m;
    public javax.swing.JCheckBox Graphik_check_Steigung_p;
    public javax.swing.JCheckBox Graphik_check_Temp;
    public javax.swing.JCheckBox Graphik_check_av_Geschw;
    public javax.swing.JTabbedPane Hauptfenster;
    private javax.swing.JPanel Histogramm_Panel;
    private javax.swing.JButton Info_Button_Suche_TrackLog;
    private javax.swing.JButton Info_Button_einfügen;
    private javax.swing.JButton Info_Button_kopieren;
    private javax.swing.JButton Info_Button_speichern;
    private javax.swing.JTextField Info_GebTag;
    private javax.swing.JTextField Info_Gewicht;
    private javax.swing.JTextField Info_Material;
    private javax.swing.JTextField Info_Materialgewicht;
    private javax.swing.JTextField Info_Name;
    private javax.swing.JTextArea Info_Notiz;
    private javax.swing.JPanel Info_Panel;
    private javax.swing.JTextField Info_Startort;
    private javax.swing.JTextField Info_Titel;
    private javax.swing.JTextField Info_Track_Log;
    private javax.swing.JTextField Info_Verein;
    private javax.swing.JTextField Info_Vorname;
    private javax.swing.JTextField Info_Zielort;
    public javax.swing.JComboBox JahrVergleich;
    private javax.swing.JPanel Jahresuebersicht_Panel;
    private javax.swing.JLabel Kein_kmz_text;
    private javax.swing.JButton LoadGoogleEarth;
    private javax.swing.JPanel Map_Panel;
    private javax.swing.JComboBox<String> Map_Type;
    private javax.swing.JPanel Map_internal_Panel;
    private javax.swing.JLabel Statistik_Belastung;
    private javax.swing.JPanel Statistik_Cadence;
    private javax.swing.JLabel Statistik_Erholungszeit;
    private javax.swing.JLabel Statistik_Fett;
    private javax.swing.JPanel Statistik_Gefälle_m;
    private javax.swing.JPanel Statistik_Gefälle_p;
    private javax.swing.JPanel Statistik_Geschwindigkeit;
    private javax.swing.JLabel Statistik_HM_pro_km;
    private javax.swing.JPanel Statistik_Herzfrequenz;
    private javax.swing.JPanel Statistik_Höhe;
    private javax.swing.JPanel Statistik_Kalorien;
    private javax.swing.JLabel Statistik_Kalorien_absolut;
    private javax.swing.JLabel Statistik_Kalorien_h;
    private javax.swing.JLabel Statistik_Lauf_Index;
    private javax.swing.JLabel Statistik_Max_Geschw;
    private javax.swing.JLabel Statistik_Maximale_Höhe;
    private javax.swing.JLabel Statistik_Minimale_Höhe;
    private javax.swing.JPanel Statistik_Panel;
    private javax.swing.JLabel Statistik_Protein;
    private javax.swing.JPanel Statistik_Schrittlänge;
    private javax.swing.JPanel Statistik_Steigung_m;
    private javax.swing.JPanel Statistik_Steigung_p;
    private javax.swing.JLabel Statistik_Summe_Hm_Gefälle;
    private javax.swing.JLabel Statistik_Summe_Hm_Steigung;
    private javax.swing.JLabel Statistik_Teilstrecke;
    private javax.swing.JPanel Statistik_Temperatur;
    private javax.swing.JLabel Statistik_Titel;
    private javax.swing.JPanel Statistik_Training;
    private javax.swing.JPanel Statistik_Zeit;
    private javax.swing.JLabel Statistik_Zeit_absolut;
    private javax.swing.JLabel Statistik_Zeit_aktiv;
    private javax.swing.JLabel Statistik_av_Cadence;
    private javax.swing.JLabel Statistik_av_Gefälle_m;
    private javax.swing.JLabel Statistik_av_Gefälle_p;
    private javax.swing.JLabel Statistik_av_Geschw;
    private javax.swing.JLabel Statistik_av_HF;
    private javax.swing.JLabel Statistik_av_Schrittlänge;
    private javax.swing.JLabel Statistik_av_Steigung_m;
    private javax.swing.JLabel Statistik_av_Steigung_p;
    private javax.swing.JLabel Statistik_av_Temp;
    private javax.swing.JLabel Statistik_max_Cadence;
    private javax.swing.JLabel Statistik_max_Gefälle_m;
    private javax.swing.JLabel Statistik_max_Gefälle_p;
    private javax.swing.JLabel Statistik_max_HF;
    private javax.swing.JLabel Statistik_max_Schrittlänge;
    private javax.swing.JLabel Statistik_max_Steigung_m;
    private javax.swing.JLabel Statistik_max_Steigung_p;
    private javax.swing.JLabel Statistik_max_Temp;
    private javax.swing.JLabel Statistik_min_Temp;
    public javax.swing.JCheckBox Summenhistogramm_Check;
    private javax.swing.ButtonGroup buttonGroup_Karte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel24Uhrzeit;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel26_Histotitel;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel65Typ;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel69_Selektiert;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_map_streckenlänge;
    private javax.swing.JLabel jLabel_search;
    private javax.swing.JMenu jMenuDatei;
    private javax.swing.JMenu jMenuEinstellungen;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenuBar jMenuHaupt;
    private javax.swing.JMenu jMenuHilfe;
    private javax.swing.JMenuItem jMenuLöschen;
    private javax.swing.JMenuItem jMenuOpen;
    private javax.swing.JMenuItem jMenuOpenall;
    private javax.swing.JMenu jMenuTourEditor;
    private javax.swing.JMenu jMenu_V800_Laden;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16_HistoHF;
    private javax.swing.JPanel jPanel17_HistoHM;
    private javax.swing.JPanel jPanel17Übersichtchart;
    public javax.swing.JPanel jPanel18_HistoSP;
    private javax.swing.JPanel jPanel19_HistoCd;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JRadioButton jRadioButton_jahresverlauf;
    private javax.swing.JRadioButton jRadioButton_monatsübersicht;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
//    public SerialPort serialPort;
//    public ReadHACData read;
    private java.io.FileOutputStream Ausgabedatei;
    private java.io.FileInputStream Eingabedatei;
    private Eigenschaften Eigenschaften;
    private java.io.File file, file2;
    private javax.swing.JFileChooser chooser;
    private java.lang.StringBuffer Buffer;
    private java.lang.String StringDummy;
    public byte RawData[];
    public java.util.Properties SystemProperties;
    public java.util.Properties Properties;
    private java.util.Properties DataProperty;
    private java.util.Properties TempProperty;
    private java.text.DecimalFormat formatproperty;
    private java.text.DecimalFormat format;
    private java.text.DecimalFormat formatneg;
    private java.text.DecimalFormat formatb;  
    private javax.swing.filechooser.FileFilter Dateifilter;
    private Dimension ScreenSize;
    private Dimension Size;
    private boolean cycle = true;
    private int TabSizeDiffx;
    private int TabSizeDiffy;
    private int RowCount = 0;
    private TableColumn DatumColumn;
    private TableColumn StreckeColumn;
    private TableColumn HoeheColumn;
    private TableColumn ZeitColumn;
    private TableColumn NotizColumn;
    public JStatistik Statistikhandle;
    public JTourEditor TourEditor;
    public JUebersicht Uebersicht;
    public javax.swing.JTable jTableaccess;
    private int Selection[];
    public javax.swing.JComboBox jComboBox1access;
    public TableSorter sorter;
    public boolean Update = true;
    public boolean SelectionChanged;
    public boolean Update_Graphik_paint = true;
    public boolean Update_Map_paint = true;
    public boolean Update_Info = true;
    public int Value;
    public java.awt.Cursor Cursor;
    private ButtonGroup X_Axis;
    private ButtonGroup Übersicht;
    public XYGraphik graphik;
    public JHistogram HistoGram;
    public JPanel xygraphik;
    public JPanel HFHistogramm;
    public JPanel HMHistogramm;
    public JPanel SPHistogramm;
    public JPanel CdHistogramm;
    public JPanel Jahresüberblick;
    public JPanel Landkarte;
    public java.io.PrintStream outStream;
    private static boolean debug;
    private String args[];
    int oldselection; //für jComboBoxMarkenZeit benötigt, um Einträge ändern zu können
    public boolean Editmode = false;
    public ImageIcon icon;
    public org.jxmapviewer.JXMapKit mapKit;
    public double map_x_max;
    public double map_x_min;
    public double map_y_max;
    public double map_y_min;
    public double from_x;
    public double to_x;
    public double mark_x;
    public GeoPosition MapCenter;
    public int MapZoom;
    public boolean Mapdrawn = false;
    public String mapkitpath;
    public boolean nozoom = true; 
    public int alteAuswahl =-1;
    public int Stunden;
    public int Minuten;
    public int Sekunden;

    public double graph_min = 0, graph_max = 999999999;
    public double graph_crosshair =0;

    public int FontSize;
    public String Font;
    public Second offsetsecond;
//    public hrmcom.POLAR_SSET_GENERAL psg;
//    public hrmcom.POLAR_SSET_MONITORINFO psmi;
//    public hrmcom.POLAR_EXERCISEFILE pef;

    public ProgressMonitor pm;
    
    boolean locmap = true;
    GeoPosition Markierung = new GeoPosition(0,0);

}
