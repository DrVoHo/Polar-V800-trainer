package v800_trainer;

 
/*
 * Eigenschaften.java
 *
 * SourceFile is part of Chainwheel

 * Editor for Program Options
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

//import gnu.io.*;

import java.util.*;

import javax.swing.*;
import java.io.*;
import javax.swing.ImageIcon;
import java.awt.*;


public class Eigenschaften extends javax.swing.JDialog {

    /**
     * Creates new form Eigenschaften
     */
    public Eigenschaften(java.awt.Frame par, boolean modal, JCicloTronic DummyHandle) {
        super(par, modal);
        parent = par;
        TronicHandle = DummyHandle;
        initComponents();
        nocom = false;

        Image temp;
        ImageIcon icon = new ImageIcon("logo_hw.jpg");

        temp = icon.getImage();
        icon.setImage(temp.getScaledInstance(340, 70, Image.SCALE_FAST));

        jLabel21_icon.setIcon(icon);

        dummy = new String();
        chooser = new javax.swing.JFileChooser();

//        if (Integer.parseInt(System.getProperties().get("sun.arch.data.model").toString()) == 32) {
//
//          try{ portList = CommPortIdentifier.getPortIdentifiers();}catch(UnsatisfiedLinkError e){
//            jComboBox_CommPort.addItem("nocom - rxtxSerial.dll nicht gefunden");
//          
//          };
//        //    jComboBox_CommPort.addItem("nocom");
//            if(portList !=null){
//            if (!portList.hasMoreElements()) {
//                nocom = true;
//
//                jComboBox_CommPort.addItem("Keinen Comm-Port gefunden!");
//            } else {
//                nocom = false;
//            }
//            while (portList.hasMoreElements()) {
//                portId = (CommPortIdentifier) portList.nextElement();
//                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//                    jComboBox_CommPort.addItem(portId.getName());
//                }
//
//            }
//            jComboBox_CommPort.setSelectedIndex(0);
//            };
//        } else {
//            jComboBox_CommPort.addItem("nocom (64bit java)");
//        }

        init = true;
        // läd alle Look&Feels in die ComboBox
        for (UIManager.LookAndFeelInfo info : UIManager
                .getInstalledLookAndFeels()) {
            jComboBox1_LookandFeel.addItem(info.getName());

        }

        init = false;

        dummy = TronicHandle.Properties.getProperty("LookFeel", " ");

        //jComboBox1_LookandFeel mit gespeichertem Wert selektieren
        for (UIManager.LookAndFeelInfo info : UIManager
                .getInstalledLookAndFeels()) {

            if (info.getClassName().equalsIgnoreCase(dummy)) {

                jComboBox1_LookandFeel.setSelectedItem(info.getName());

            }

        }

        try {

            UIManager.setLookAndFeel(dummy);
            SwingUtilities.updateComponentTreeUI(this);

        } catch (Exception exc) {
        }

        if (TronicHandle.Properties.getProperty("data.dir","").equalsIgnoreCase("")) {
            jTextFieldDatenDir.setText(TronicHandle.SystemProperties.getProperty("user.dir"));
        } else {
            jTextFieldDatenDir.setText(TronicHandle.Properties.getProperty("data.dir", TronicHandle.SystemProperties.getProperty("user.dir")));
        }
        jTextFieldGPSDir.setText(TronicHandle.Properties.getProperty("GPS.dir", TronicHandle.SystemProperties.getProperty("user.dir")));
        jTextFieldGoogleEarthPath.setText(TronicHandle.Properties.getProperty("GoogleEarthPath", "no Path"));
        jTextField5vonHf.setText(TronicHandle.Properties.getProperty("HistovonHf", "100"));
        jTextField4bisHf.setText(TronicHandle.Properties.getProperty("HistobisHf", "200"));
        jTextField6stepHf.setText(TronicHandle.Properties.getProperty("HistostepHf", "25"));
        jTextField8vonHm.setText(TronicHandle.Properties.getProperty("HistovonHm", "0"));
        jTextField7bisHm.setText(TronicHandle.Properties.getProperty("HistobisHm", "30"));
        jTextField9stepHm.setText(TronicHandle.Properties.getProperty("HistostepHm", "25"));
        jTextField11vonSp.setText(TronicHandle.Properties.getProperty("HistovonSp", "0"));
        jTextField10bisSp.setText(TronicHandle.Properties.getProperty("HistobisSp", "100"));
        jTextField12stepSp.setText(TronicHandle.Properties.getProperty("HistostepSp", "25"));
        jTextField1vonCd.setText(TronicHandle.Properties.getProperty("HistovonCd", "0"));
        jTextField2bisCd.setText(TronicHandle.Properties.getProperty("HistobisCd", "100"));
        jTextField3stepCd.setText(TronicHandle.Properties.getProperty("HistostepCd", "25"));
        jTextField1vonSchrittlänge.setText(TronicHandle.Properties.getProperty("HistovonSchrittlänge", "0"));
        jTextField2bisSchrittlänge.setText(TronicHandle.Properties.getProperty("HistobisSchrittlänge", "200"));
        jTextField3stepSchrittlänge.setText(TronicHandle.Properties.getProperty("HistostepSchrittlänge", "25"));
        jTextField1Höhenakkumulierung.setText(TronicHandle.Properties.getProperty("Höhenmeterakkumulierung", "1"));
        jTextField1HFMittelwert.setText(TronicHandle.Properties.getProperty("HFMittelwert", "1"));
        jTextField1GeschwMittelwert.setText(TronicHandle.Properties.getProperty("GeschwMittelwert", "15"));
        jTextField2HmMittelwert.setText(TronicHandle.Properties.getProperty("HmMittelwert", "1"));
        jTextField3SteigpMittelwert.setText(TronicHandle.Properties.getProperty("SteigpMittelwert", "30"));
        jTextField4SteigmMittelwert.setText(TronicHandle.Properties.getProperty("SteigmMittelwert", "30"));
        jTextField5CadenceMittelwert.setText(TronicHandle.Properties.getProperty("CadenceMittelwert", "1"));
        jTextField5SchrittlängeMittelwert.setText(TronicHandle.Properties.getProperty("SchrittlängeMittelwert", "1"));
        jMax_Anz_Graphik.setText(TronicHandle.Properties.getProperty("AnzahlKurven", "5"));
        jLabel33_FontSize.setText(TronicHandle.Properties.getProperty("FontSize", "20"));
        jLabel32_Font.setText(TronicHandle.Properties.getProperty("Font", "Tahoma"));
//        jComboBox_CommPort.setSelectedItem(TronicHandle.Properties.getProperty("CommPort", "nocom"));

//        if (TronicHandle.serialPort != null) {
//            jComboBox_CommPort.setEnabled(false);
//        }

        if (Integer.parseInt(TronicHandle.Properties.getProperty("Daten ueberschreiben", "0")) == 0) {
            jCheckBox1.setSelected(false);
        } else {
            jCheckBox1.setSelected(true);
        }

        setSize(500 + TronicHandle.FontSize * 20, 500);
        setVisible(true);

    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButtonOK = new javax.swing.JButton();
        jButtonAbbrechen = new javax.swing.JButton();
        jLabel21_icon = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldGoogleEarthPath = new javax.swing.JTextField();
        jButtonSucheGoogleEarthpath = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextFieldDatenDir = new javax.swing.JTextField();
        jButtonSucheDatenDir = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jTextFieldGPSDir = new javax.swing.JTextField();
        jButton1_GPS_Verzeichnis_wählen = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField5vonHf = new javax.swing.JTextField();
        jTextField4bisHf = new javax.swing.JTextField();
        jTextField6stepHf = new javax.swing.JTextField();
        jTextField11vonSp = new javax.swing.JTextField();
        jTextField10bisSp = new javax.swing.JTextField();
        jTextField12stepSp = new javax.swing.JTextField();
        jTextField8vonHm = new javax.swing.JTextField();
        jTextField7bisHm = new javax.swing.JTextField();
        jTextField9stepHm = new javax.swing.JTextField();
        jTextField1vonCd = new javax.swing.JTextField();
        jTextField2bisCd = new javax.swing.JTextField();
        jTextField3stepCd = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jTextField1vonSchrittlänge = new javax.swing.JTextField();
        jTextField2bisSchrittlänge = new javax.swing.JTextField();
        jTextField3stepSchrittlänge = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField1HFMittelwert = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField1GeschwMittelwert = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField2HmMittelwert = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField3SteigpMittelwert = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField4SteigmMittelwert = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField5CadenceMittelwert = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField1Höhenakkumulierung = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jTextField5SchrittlängeMittelwert = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jMax_Anz_Graphik = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1_LookandFeel = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jButton1_FontChooser = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel32_Font = new javax.swing.JLabel();
        jLabel33_FontSize = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();

        setTitle("  Einstellungen");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        getContentPane().add(jButtonOK, gridBagConstraints);

        jButtonAbbrechen.setText("Abbrechen");
        jButtonAbbrechen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbbrechenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        getContentPane().add(jButtonAbbrechen, gridBagConstraints);

        jLabel21_icon.setText("  ");
        jLabel21_icon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel21_icon.setDoubleBuffered(true);
        jLabel21_icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel21_icon.setIconTextGap(0);
        jLabel21_icon.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabel21_icon.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabel21_icon.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 320;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jLabel21_icon, gridBagConstraints);

        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel1Layout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        jPanel1.setLayout(jPanel1Layout);

        jLabel11.setText("Google Earth Pfad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel11, gridBagConstraints);

        jTextFieldGoogleEarthPath.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jTextFieldGoogleEarthPath, gridBagConstraints);

        jButtonSucheGoogleEarthpath.setText("...");
        jButtonSucheGoogleEarthpath.setToolTipText("Verzeichnis ändern");
        jButtonSucheGoogleEarthpath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucheGoogleEarthpathActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        jPanel1.add(jButtonSucheGoogleEarthpath, gridBagConstraints);

        jCheckBox1.setText("Datenfiles überschreiben");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jCheckBox1, gridBagConstraints);

        jTextFieldDatenDir.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jTextFieldDatenDir, gridBagConstraints);

        jButtonSucheDatenDir.setText("...");
        jButtonSucheDatenDir.setToolTipText("Verzeichnis ändern");
        jButtonSucheDatenDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucheDatenDirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jButtonSucheDatenDir, gridBagConstraints);

        jLabel21.setText("Daten Verzeichnis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel21, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jPanel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jPanel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jPanel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jPanel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jLabel32.setText("GPS log Verzeichnis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel32, gridBagConstraints);

        jTextFieldGPSDir.setEditable(false);
        jTextFieldGPSDir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jTextFieldGPSDir, gridBagConstraints);

        jButton1_GPS_Verzeichnis_wählen.setText("...");
        jButton1_GPS_Verzeichnis_wählen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_GPS_Verzeichnis_wählenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        jPanel1.add(jButton1_GPS_Verzeichnis_wählen, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(jPanel22, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jPanel30, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 120, 0, 0);
        jPanel1.add(jPanel29, gridBagConstraints);

        jTabbedPane1.addTab("Verzeichnisse / Schnittstelle", jPanel1);

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel2Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel3.setText("Histogramme");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel4.setText("von");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setText("bis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Anzahl Teilungen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Herzfrequenz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel9.setText("Geschwindigkeit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel9, gridBagConstraints);

        jLabel8.setText("Steigung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel10.setText("Cadence");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel10, gridBagConstraints);

        jTextField5vonHf.setText("jTextField1");
        jTextField5vonHf.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField5vonHf.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField5vonHf, gridBagConstraints);

        jTextField4bisHf.setText("jTextField2");
        jTextField4bisHf.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField4bisHf.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField4bisHf, gridBagConstraints);

        jTextField6stepHf.setText("jTextField3");
        jTextField6stepHf.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField6stepHf.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField6stepHf, gridBagConstraints);

        jTextField11vonSp.setText("jTextField1");
        jTextField11vonSp.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField11vonSp.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField11vonSp, gridBagConstraints);

        jTextField10bisSp.setText("jTextField2");
        jTextField10bisSp.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField10bisSp.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField10bisSp, gridBagConstraints);

        jTextField12stepSp.setText("jTextField3");
        jTextField12stepSp.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField12stepSp.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField12stepSp, gridBagConstraints);

        jTextField8vonHm.setText("jTextField1");
        jTextField8vonHm.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField8vonHm.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField8vonHm, gridBagConstraints);

        jTextField7bisHm.setText("jTextField2");
        jTextField7bisHm.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField7bisHm.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField7bisHm, gridBagConstraints);

        jTextField9stepHm.setText("jTextField3");
        jTextField9stepHm.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField9stepHm.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField9stepHm, gridBagConstraints);

        jTextField1vonCd.setText("jTextField1");
        jTextField1vonCd.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField1vonCd.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField1vonCd, gridBagConstraints);

        jTextField2bisCd.setText("jTextField2");
        jTextField2bisCd.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField2bisCd.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField2bisCd, gridBagConstraints);

        jTextField3stepCd.setText("jTextField3");
        jTextField3stepCd.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField3stepCd.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField3stepCd, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(jPanel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(jPanel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jPanel14, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel15, gridBagConstraints);

        jLabel35.setText("Schrittlänge");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jLabel35, gridBagConstraints);

        jTextField1vonSchrittlänge.setText("jTextField1");
        jTextField1vonSchrittlänge.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField1vonSchrittlänge.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField1vonSchrittlänge, gridBagConstraints);

        jTextField2bisSchrittlänge.setText("jTextField2");
        jTextField2bisSchrittlänge.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField2bisSchrittlänge.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField2bisSchrittlänge, gridBagConstraints);

        jTextField3stepSchrittlänge.setText("jTextField3");
        jTextField3stepSchrittlänge.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField3stepSchrittlänge.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jTextField3stepSchrittlänge, gridBagConstraints);

        jTabbedPane1.addTab("Histogramme", jPanel2);

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel3Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel3.setLayout(jPanel3Layout);

        jLabel15.setText("Zeit in s über die gemittelt werden soll (Kurvenglättung)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel15, gridBagConstraints);
        jLabel15.getAccessibleContext().setAccessibleName("Zeit über die gemittelt werden soll (Kurvenglättung)");

        jLabel14.setText("Herzfrequenz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel14, gridBagConstraints);

        jTextField1HFMittelwert.setText("jTextField1");
        jTextField1HFMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField1HFMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField1HFMittelwert, gridBagConstraints);

        jLabel24.setText("Geschwindigkeit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel24, gridBagConstraints);

        jTextField1GeschwMittelwert.setText("jTextField1");
        jTextField1GeschwMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField1GeschwMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField1GeschwMittelwert, gridBagConstraints);

        jLabel17.setText("Höhenmeter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel17, gridBagConstraints);

        jTextField2HmMittelwert.setText("jTextField2");
        jTextField2HmMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField2HmMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField2HmMittelwert, gridBagConstraints);

        jLabel18.setText("Steigung [%]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel18, gridBagConstraints);

        jTextField3SteigpMittelwert.setText("jTextField3");
        jTextField3SteigpMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField3SteigpMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField3SteigpMittelwert, gridBagConstraints);

        jLabel19.setText("Steigung [m/min]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel19, gridBagConstraints);

        jTextField4SteigmMittelwert.setText("jTextField4");
        jTextField4SteigmMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField4SteigmMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField4SteigmMittelwert, gridBagConstraints);

        jLabel20.setText("Cadence");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel20, gridBagConstraints);

        jTextField5CadenceMittelwert.setText("jTextField5");
        jTextField5CadenceMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField5CadenceMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField5CadenceMittelwert, gridBagConstraints);

        jLabel12.setText("Schwelle Höhenakkumulierung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel12, gridBagConstraints);

        jTextField1Höhenakkumulierung.setText("jTextField1");
        jTextField1Höhenakkumulierung.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField1Höhenakkumulierung.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField1Höhenakkumulierung, gridBagConstraints);

        jLabel13.setText("m");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        jPanel3.add(jLabel13, gridBagConstraints);

        jLabel25.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        jPanel3.add(jLabel25, gridBagConstraints);

        jLabel26.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        jPanel3.add(jLabel26, gridBagConstraints);

        jLabel27.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        jPanel3.add(jLabel27, gridBagConstraints);

        jLabel28.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        jPanel3.add(jLabel28, gridBagConstraints);

        jLabel29.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        jPanel3.add(jLabel29, gridBagConstraints);

        jLabel30.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        jPanel3.add(jLabel30, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 5;
        jPanel3.add(jSeparator1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jPanel16, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel3.add(jPanel17, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanel18, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanel19, gridBagConstraints);

        jLabel33.setText("Schrittlänge");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel33, gridBagConstraints);

        jTextField5SchrittlängeMittelwert.setText("jTextField5");
        jTextField5SchrittlängeMittelwert.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField5SchrittlängeMittelwert.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jTextField5SchrittlängeMittelwert, gridBagConstraints);

        jLabel34.setText("s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 16;
        jPanel3.add(jLabel34, gridBagConstraints);

        jTabbedPane1.addTab("Datenhandling", jPanel3);

        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWidths = new int[] {0, 13, 0, 13, 0, 13, 0, 13, 0, 13, 0};
        jPanel4Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel4.setLayout(jPanel4Layout);

        jLabel22.setText("Max. angezeigter  Touren / Tracks");
        jLabel22.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jLabel22, gridBagConstraints);

        jMax_Anz_Graphik.setText("jTextField1");
        jMax_Anz_Graphik.setMinimumSize(new java.awt.Dimension(50, 20));
        jMax_Anz_Graphik.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jMax_Anz_Graphik, gridBagConstraints);

        jLabel2.setText("Look & Feel");
        jLabel2.setMaximumSize(new java.awt.Dimension(50000, 14));
        jLabel2.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel2.setPreferredSize(new java.awt.Dimension(150, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel4.add(jLabel2, gridBagConstraints);

        jComboBox1_LookandFeel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1_LookandFeelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel4.add(jComboBox1_LookandFeel, gridBagConstraints);

        jLabel16.setText("Font ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jLabel16, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel4.add(jPanel20, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel4.add(jPanel21, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel23, gridBagConstraints);

        jLabel31.setText("Größe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jLabel31, gridBagConstraints);

        jButton1_FontChooser.setText("Font auswählen");
        jButton1_FontChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_FontChooserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel4.add(jButton1_FontChooser, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel28, gridBagConstraints);

        jLabel32_Font.setText("jLabel32");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jLabel32_Font, gridBagConstraints);
        jLabel32_Font.getAccessibleContext().setAccessibleName("jLabel32_Font");

        jLabel33_FontSize.setText("jLabel33");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel4.add(jLabel33_FontSize, gridBagConstraints);
        jLabel33_FontSize.getAccessibleContext().setAccessibleName("jLabel33_FontSize");

        jTabbedPane1.addTab("Oberfläche", jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(jPanel24, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(jPanel25, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(jPanel26, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(jPanel27, gridBagConstraints);

        setSize(new java.awt.Dimension(895, 760));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jComboBox1_LookandFeelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1_LookandFeelActionPerformed
      
        if (init) {
            return;
        }
        for (UIManager.LookAndFeelInfo info : UIManager
                .getInstalledLookAndFeels()) {

            if (info.getName().equalsIgnoreCase(jComboBox1_LookandFeel.getSelectedItem().toString())) {
                try {

                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);

                } catch (Exception exc) {
                }

            }
        }


    }//GEN-LAST:event_jComboBox1_LookandFeelActionPerformed
    
  private void jButtonAbbrechenActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbbrechenActionPerformed
    
      setVisible(false);
      dispose();
  }//GEN-LAST:event_jButtonAbbrechenActionPerformed
  
  private void jButtonOKActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed

      int test1, test2;
      TronicHandle.Properties.put("data.dir", jTextFieldDatenDir.getText());

      String dummy1 = TronicHandle.Properties.getProperty("GPS.dir", TronicHandle.SystemProperties.getProperty("user.dir"));
      String dummy2 = jTextFieldGPSDir.getText();
      if (!dummy1.equals(dummy2)) {
          ChangeGPSDir();
      }

      TronicHandle.Properties.put("GPS.dir", jTextFieldGPSDir.getText());
      TronicHandle.Properties.put("GoogleEarthPath", jTextFieldGoogleEarthPath.getText());

//      if (!nocom) {
//          TronicHandle.Properties.put("CommPort", jComboBox_CommPort.getSelectedItem());
//      } else {
//          TronicHandle.Properties.put("CommPort", "nocom");
//      }
      try {
          test1 = Integer.parseInt(jTextField5vonHf.getText());
          test2 = Integer.parseInt(jTextField4bisHf.getText());
          if (test1 >= test2) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"von\" muss immer kleiner sein als\"bis\"!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField6stepHf.getText());
          if (test1 <= 0) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"Anzahl Teilungen\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField8vonHm.getText());
          test2 = Integer.parseInt(jTextField7bisHm.getText());
          if (test1 >= test2) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"von\" muss immer kleiner sein als\"bis\"!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField9stepHm.getText());
          if (test1 <= 0) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"Anzahl Teilungen\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField11vonSp.getText());
          test2 = Integer.parseInt(jTextField10bisSp.getText());
          if (test1 >= test2) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"von\" muss immer kleiner sein als\"bis\"!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField12stepSp.getText());
          if (test1 <= 0) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"Anzahl Teilungen\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField1vonCd.getText());
          test2 = Integer.parseInt(jTextField2bisCd.getText());
          if (test1 >= test2) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"von\" muss immer kleiner sein als\"bis\"!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField1vonSchrittlänge.getText());
          test2 = Integer.parseInt(jTextField2bisSchrittlänge.getText());
          if (test1 >= test2) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"von\" muss immer kleiner sein als\"bis\"!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField3stepCd.getText());
          if (test1 <= 0) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"Anzahl Teilungen\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
          test1 = Integer.parseInt(jTextField3stepSchrittlänge.getText());
          if (test1 <= 0) {
              JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten:\n \"Anzahl Teilungen\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
              return;
          }
      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei den Histogrammwerten: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField1Höhenakkumulierung.getText());
      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Höhenmeterakkumulierung!: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField1HFMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nHerzfrequenz: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nHerzfrequenz: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField1GeschwMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nGeschwindigkeit: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nGeschwindigkeit: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField2HmMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nHöhenmeter: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nHöhenmeter: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField3SteigpMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nSteigung [%]: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nSteigung [%]: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jMax_Anz_Graphik.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die maximal angezeigten Kurven oder Tracks \nSie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die maximal angezeigten Kurven oder Tracks \nSteigung [%]: \n \"Anzahl der Kurven\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField4SteigmMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nSteigung [m/min]: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nSteigung [m/min]: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      try {
          test1 = Integer.parseInt(jTextField5CadenceMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nCadence: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nCadence: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      try {
          test1 = Integer.parseInt(jTextField5SchrittlängeMittelwert.getText());

      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nSchrittlänge: \n Sie haben keine ganze Zahl eingegeben", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }
      if (test1 <= 0) {
          JOptionPane.showMessageDialog(null, "Fehler bei dem Wert für die Zeit der Kurfenglättung bei \nSchrittlänge: \n \"Anzahl der Datenpunkte\" muss immer größer Null sein!", "Achtung!", JOptionPane.ERROR_MESSAGE);
          return;
      }

      if (!TronicHandle.Properties.getProperty("Höhenmeterakkumulierung", "0").equals(jTextField1Höhenakkumulierung.getText())) {
          TronicHandle.Properties.setProperty("Höhenmeterakkumulierung", jTextField1Höhenakkumulierung.getText());
          if (TronicHandle.Datentabelle != null) {
              UpdateHöhenmeterakkumulierung();
          }
      }

      TronicHandle.Properties.setProperty("HistovonHf", jTextField5vonHf.getText());
      TronicHandle.Properties.setProperty("HistobisHf", jTextField4bisHf.getText());
      TronicHandle.Properties.setProperty("HistostepHf", jTextField6stepHf.getText());
      TronicHandle.Properties.setProperty("HistovonHm", jTextField8vonHm.getText());
      TronicHandle.Properties.setProperty("HistobisHm", jTextField7bisHm.getText());
      TronicHandle.Properties.setProperty("HistostepHm", jTextField9stepHm.getText());
      TronicHandle.Properties.setProperty("HistovonSp", jTextField11vonSp.getText());
      TronicHandle.Properties.setProperty("HistobisSp", jTextField10bisSp.getText());
      TronicHandle.Properties.setProperty("HistostepSp", jTextField12stepSp.getText());
      TronicHandle.Properties.setProperty("HistovonCd", jTextField1vonCd.getText());
      TronicHandle.Properties.setProperty("HistobisCd", jTextField2bisCd.getText());
      TronicHandle.Properties.setProperty("HistostepCd", jTextField3stepCd.getText());
      TronicHandle.Properties.setProperty("HistovonSchrittlänge", jTextField1vonSchrittlänge.getText());
      TronicHandle.Properties.setProperty("HistobisSchrittlänge", jTextField2bisSchrittlänge.getText());
      TronicHandle.Properties.setProperty("HistostepSchrittlänge", jTextField3stepSchrittlänge.getText());
      TronicHandle.Properties.setProperty("Höhenmeterakkumulierung", jTextField1Höhenakkumulierung.getText());
      TronicHandle.Properties.setProperty("HFMittelwert", jTextField1HFMittelwert.getText());
      TronicHandle.Properties.setProperty("GeschwMittelwert", jTextField1GeschwMittelwert.getText());
      TronicHandle.Properties.setProperty("HmMittelwert", jTextField2HmMittelwert.getText());
      TronicHandle.Properties.setProperty("SteigpMittelwert", jTextField3SteigpMittelwert.getText());
      TronicHandle.Properties.setProperty("SteigmMittelwert", jTextField4SteigmMittelwert.getText());
      TronicHandle.Properties.setProperty("CadenceMittelwert", jTextField5CadenceMittelwert.getText());
      TronicHandle.Properties.setProperty("SchrittlängeMittelwert", jTextField5SchrittlängeMittelwert.getText());
      TronicHandle.Properties.setProperty("AnzahlKurven", jMax_Anz_Graphik.getText());
      TronicHandle.Properties.setProperty("FontSize", jLabel33_FontSize.getText());
      TronicHandle.FontSize = Integer.parseInt(jLabel33_FontSize.getText());
      TronicHandle.Properties.setProperty("Font", jLabel32_Font.getText());
      TronicHandle.Font = jLabel32_Font.getText();

      if (jCheckBox1.isSelected()) {
          TronicHandle.Properties.setProperty("Daten ueberschreiben", "1");
      } else {
          TronicHandle.Properties.setProperty("Daten ueberschreiben", "0");
      }

      for (UIManager.LookAndFeelInfo info : UIManager
              .getInstalledLookAndFeels()) {

          if (info.getName().equalsIgnoreCase(jComboBox1_LookandFeel.getSelectedItem().toString())) {
              try {

                  UIManager.setLookAndFeel(info.getClassName());
                  SwingUtilities.updateComponentTreeUI(this);

              } catch (Exception exc) {
              }

              TronicHandle.Properties.setProperty("LookFeel", info.getClassName());

          }
      }

      setVisible(false);

      File file = new File(jTextFieldDatenDir.getText());
      if (!file.exists()) {
          file.mkdir();
      }
      file = new File(jTextFieldDatenDir.getText() + TronicHandle.SystemProperties.getProperty("file.separator") + "V800_Raw");
      if (!file.exists()) {
          file.mkdir();
      }

      TronicHandle.setTitle("ChainWheel    Datadir: " + TronicHandle.Properties.getProperty("data.dir"));

      try {
          FileOutputStream out = new FileOutputStream(TronicHandle.Properties.getProperty("working.dir") + TronicHandle.SystemProperties.getProperty("file.separator") + "JCicloexp.cfg");

          TronicHandle.Properties.store(out, "---Properties of HWCyclingData---");
          out.close();

      } catch (Exception e) {}

      TronicHandle.ChangeModel();
      SwingUtilities.updateComponentTreeUI(TronicHandle);
      if(TronicHandle.Hauptfenster!=null)TronicHandle.Hauptfenster.setSelectedIndex(0);

      dispose();
  }//GEN-LAST:event_jButtonOKActionPerformed
  
  private void jButtonSucheDatenDirActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucheDatenDirActionPerformed
    
      chooser.setCurrentDirectory(new java.io.File(jTextFieldDatenDir.getText()));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = chooser.showDialog(this, null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
          jTextFieldDatenDir.setText(chooser.getSelectedFile().getPath());
      }

      
  }//GEN-LAST:event_jButtonSucheDatenDirActionPerformed
  
  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
      setVisible(false);
      dispose();
  }//GEN-LAST:event_closeDialog

  private void jButtonSucheGoogleEarthpathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucheGoogleEarthpathActionPerformed
      chooser.setCurrentDirectory(new java.io.File(jTextFieldGoogleEarthPath.getText()));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnVal = chooser.showDialog(this, null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
          jTextFieldGoogleEarthPath.setText(chooser.getSelectedFile().getPath());
      }
}//GEN-LAST:event_jButtonSucheGoogleEarthpathActionPerformed

    private void jButton1_FontChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_FontChooserActionPerformed
 
        Font fc = FontChooser.showDialog(this.parent, "Systemfont wählen", new Font(jLabel32_Font.getText(), 0, Integer.parseInt(jLabel33_FontSize.getText())));

        jLabel32_Font.setText(fc.getFontName());
        jLabel33_FontSize.setText("" + fc.getSize());

    }//GEN-LAST:event_jButton1_FontChooserActionPerformed

    private void jButton1_GPS_Verzeichnis_wählenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_GPS_Verzeichnis_wählenActionPerformed
      
      chooser.setCurrentDirectory(new java.io.File(jTextFieldGPSDir.getText()));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
      int returnVal = chooser.showDialog(this,null);
      if(returnVal == JFileChooser.APPROVE_OPTION)
          jTextFieldGPSDir.setText(chooser.getSelectedFile().getPath());      
    }//GEN-LAST:event_jButton1_GPS_Verzeichnis_wählenActionPerformed
  
  
  
  public void setVisible(boolean bool){
      super.setVisible(bool);
  }
  
  
    private void UpdateHöhenmeterakkumulierung() {
        
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
  

                for (int i = 0; i < TronicHandle.Datentabelle.getRowCount(); i++) {
  
                    String Filename = (String) TronicHandle.sorter.getValueAt(i, 5);// + ".cfg";
          
                    Dummydata = new JTourData(Filename, TronicHandle);
                    Dummydata.DataProperty.setProperty("Hoehenmeter", "" + Dummydata.ges_Hoehep);

                    try {
                        Ausgabedatei = new FileOutputStream(Filename + ".cfg");
                        Dummydata.DataProperty.store(Ausgabedatei, "Tour Eigenschaften: " + Dummydata.DataProperty.getProperty("Jahr") + Dummydata.DataProperty.getProperty("Monat")
                                + Dummydata.DataProperty.getProperty("Tag")
                                + Dummydata.DataProperty.getProperty("Stunde")
                                + Dummydata.DataProperty.getProperty("Minute"));
                        Ausgabedatei.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "UpdateHöhenmeterakkumulierung\nIO-Fehler bei " + Filename + e, "Achtung!", JOptionPane.ERROR_MESSAGE);
                    }

                } 
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

  
  private void ChangeGPSDir(){
      if (TronicHandle.Datentabelle == null)return;
      
      JOptionPane.showMessageDialog(null,"GPS Dir changed!","Achtung!", JOptionPane.ERROR_MESSAGE);
          
      String dummy, dummy2;
      String path;
      java.util.Properties   DataProperty;
      File f;
      
      int i;
      for (i=0; i< TronicHandle.Datentabelle.getRowCount();i++){
          
          String    Filename =  (String)TronicHandle.sorter.getValueAt(i,5) + ".cfg";
          
          DataProperty = new java.util.Properties();
        
        
        try{
            FileInputStream in = new FileInputStream(Filename);
            DataProperty.load(in);
            in.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"IO-Fehler bei "+ Filename + e,"Achtung!", JOptionPane.ERROR_MESSAGE);
        }
  
          dummy = DataProperty.getProperty("GoogleEarth","");
         f = new File(dummy);

         dummy = f.getName();
         f = new File(jTextFieldGPSDir.getText()+"\\"+dummy);
         //f = new File(jTextFieldGPSDir.getText()+"\\" + dummy);
          if (f.exists()) DataProperty.setProperty("GoogleEarth",jTextFieldGPSDir.getText()+"\\"+dummy);
          else DataProperty.setProperty("GoogleEarth","");
          
   
          
          try{
              Ausgabedatei = new FileOutputStream(Filename);
              DataProperty.store(Ausgabedatei,"Tour Eigenschaften: " + DataProperty.getProperty("Jahr") + DataProperty.getProperty("Monat") +
                      DataProperty.getProperty("Tag") +
                     DataProperty.getProperty("Stunde") +
                      DataProperty.getProperty("Minute") );
              Ausgabedatei.close();
          }catch(Exception e){
              JOptionPane.showMessageDialog(null,"Änderung GPS Verzeichnis\nIO-Fehler bei "+ Filename + e,"Achtung!", JOptionPane.ERROR_MESSAGE);
          }
           
      }
  }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_FontChooser;
    private javax.swing.JButton jButton1_GPS_Verzeichnis_wählen;
    private javax.swing.JButton jButtonAbbrechen;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonSucheDatenDir;
    private javax.swing.JButton jButtonSucheGoogleEarthpath;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1_LookandFeel;
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
    private javax.swing.JLabel jLabel21_icon;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel32_Font;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel33_FontSize;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jMax_Anz_Graphik;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField10bisSp;
    private javax.swing.JTextField jTextField11vonSp;
    private javax.swing.JTextField jTextField12stepSp;
    private javax.swing.JTextField jTextField1GeschwMittelwert;
    private javax.swing.JTextField jTextField1HFMittelwert;
    private javax.swing.JTextField jTextField1Höhenakkumulierung;
    private javax.swing.JTextField jTextField1vonCd;
    private javax.swing.JTextField jTextField1vonSchrittlänge;
    private javax.swing.JTextField jTextField2HmMittelwert;
    private javax.swing.JTextField jTextField2bisCd;
    private javax.swing.JTextField jTextField2bisSchrittlänge;
    private javax.swing.JTextField jTextField3SteigpMittelwert;
    private javax.swing.JTextField jTextField3stepCd;
    private javax.swing.JTextField jTextField3stepSchrittlänge;
    private javax.swing.JTextField jTextField4SteigmMittelwert;
    private javax.swing.JTextField jTextField4bisHf;
    private javax.swing.JTextField jTextField5CadenceMittelwert;
    private javax.swing.JTextField jTextField5SchrittlängeMittelwert;
    private javax.swing.JTextField jTextField5vonHf;
    private javax.swing.JTextField jTextField6stepHf;
    private javax.swing.JTextField jTextField7bisHm;
    private javax.swing.JTextField jTextField8vonHm;
    private javax.swing.JTextField jTextField9stepHm;
    private javax.swing.JTextField jTextFieldDatenDir;
    private javax.swing.JTextField jTextFieldGPSDir;
    private javax.swing.JTextField jTextFieldGoogleEarthPath;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JFileChooser chooser;
    private JCicloTronic TronicHandle;
    private Enumeration portList;
//    private CommPortIdentifier portId;
    private javax.swing.ButtonGroup LookFeel;
    private String dummy;
    private JTourData Dummydata;
    private boolean nocom;
    private java.io.FileOutputStream Ausgabedatei;
    private  boolean init = false;
    private Frame parent;
    
    public FontChooser fontchooser;
}