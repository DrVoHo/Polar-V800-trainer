package v800_trainer;

 /*
  * JUebersicht.java
 
 * SourceFile is part of Chainwheel

 * creats Chart for yearly or monthly progress
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


  * Created on 13. März 2002, 17:50
  */



import java.awt.*;
import javax.swing.*;

import java.util.GregorianCalendar;
import java.io.FileInputStream;

import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;

import java.text.DateFormat;
import java.text.NumberFormat;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.data.time.Day;
import org.jfree.chart.renderer.xy.XYBarRenderer;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.labels.StandardXYToolTipGenerator;

import org.jfree.chart.renderer.xy.StandardXYBarPainter;


public class JUebersicht {
    
    float HmJahr[][];
    float kmJahr[][];
    float Zeit[][];
  
    
    /** Creates new JUebersicht */
    public JUebersicht(JCicloTronic JTronicHandle) {
        
        int AnzahlJahre = JTronicHandle.Auswahl_Übersicht.getItemCount();
        int i,j;
        
         padding = new RectangleInsets(0,50*(int)JTronicHandle.FontSize/12,
               0, 40*(int)JTronicHandle.FontSize/12);
        
        String dummy;
        String Jahr;
        String Filename;
        Kalender = new GregorianCalendar(1900,1,1);
  
        HmJahr = new float[AnzahlJahre+1][367]; //ein Index mehr für die 12 Monate Übersicht des ersten Jahres
        kmJahr = new float[AnzahlJahre+1][367];
        Zeit = new float[AnzahlJahre+1][367];
    
        DataProperty = new java.util.Properties();
        try{
            for (i=0; i< JTronicHandle.Datentabelle.getRowCount();i++){
                
                Filename =  (String)JTronicHandle.sorter.getValueAt(i,5) + ".cfg";
                
                try{
                    FileInputStream in = new FileInputStream(Filename);
                    DataProperty.load(in);
                    in.close();
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"JUebersicht\nIO-Fehler bei "+ Filename + e,"Achtung!", JOptionPane.ERROR_MESSAGE);
                }
                try{
                    Kalender = new GregorianCalendar(Integer.parseInt(DataProperty.getProperty("Jahr","0")),
                            Integer.parseInt(DataProperty.getProperty("Monat","0"))-1,
                            Integer.parseInt(DataProperty.getProperty("Tag","0"))
                            );
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"JUebersicht\nException GregorianCalender  " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);
                    
                }
 
                for(j=0;j<JTronicHandle.Auswahl_Übersicht.getItemCount();j++){
                    if(JTronicHandle.Auswahl_Übersicht.getItemAt(j).toString().equals(DataProperty.getProperty("Jahr","0"))) {
                        
                        break;
                    }
                }
                 
                HmJahr[j][Kalender.get(Kalender.DAY_OF_YEAR)] += Float.parseFloat(DataProperty.getProperty("Hoehenmeter","0"));
                kmJahr[j][Kalender.get(Kalender.DAY_OF_YEAR)] += Float.parseFloat(DataProperty.getProperty("Strecke","0").replace(',','.'));
                Zeit[j][Kalender.get(Kalender.DAY_OF_YEAR)] += Float.parseFloat(DataProperty.getProperty("Dauer","0"))/3600.0;
                
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"JUebersicht (Dateninitialisierung)\nException +"+e,"Achtung!", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }
    
    
    
    public JPanel Update_Uebersicht(JCicloTronic JTronicHandle){
        
        int i;
        Day xTime[];
        Today = new GregorianCalendar();
        double y1Werte[];
        double y2Werte[];
        double y3Werte[];
        double y4Werte[];
        double y5Werte[];
        double y6Werte[];
        double y7Werte[];
        double y8Werte[];
        double y9Werte[];
        double y10Werte[];
        double SummeZeit = 0;
        double Summekm = 0;
        double Summehm = 0;
        double SummeZeit12Mon = 0;
        double Summekm12Mon = 0;
        double Summehm12Mon = 0;
        int AnzahlJahre =0;
        int Selektiert =0;
        int SelektiertVergl =0;
        int Jahr;
        int Linecount = 0;
        boolean Vergleich = false;
        XYItemRenderer renderer;
        XYBarRenderer rendererb;
        
        JFreeChart chart;
        
     
        AnzahlJahre = JTronicHandle.Auswahl_Übersicht.getItemCount();
        Selektiert = JTronicHandle.Auswahl_Übersicht.getSelectedIndex();
        SelektiertVergl = JTronicHandle.JahrVergleich.getSelectedIndex();
        if (SelektiertVergl == 0 || Selektiert == SelektiertVergl-1) Vergleich = false; else Vergleich = true;
        
        xTime = new Day[366];
        
        y1Werte = new double[366];
        y2Werte = new double[366];
        y3Werte = new double[366];
        y4Werte = new double[366];
        y5Werte = new double[366];
        y6Werte = new double[366];
        y7Werte = new double[366];
        y8Werte = new double[366];
        y9Werte = new double[366];
        y10Werte = new double[366];
        TimeSeries dataset;
       
        dataset = new TimeSeries("dummy");
        
        dataset.add(new Day(1,1,1900),1);
        
        TimeSeriesCollection dataset1 = new TimeSeriesCollection(dataset);
        
        chart = ChartFactory.createTimeSeriesChart(
                "Jahresübersicht "+JTronicHandle.Auswahl_Übersicht.getSelectedItem().toString(),
                "Zeit",
                "",
                dataset1,
                true,
                true,
                true
                );
 
      XYToolTipGenerator ToolTip = new StandardXYToolTipGenerator("{0}: ({1}, {2})",
            (DateFormat) new SimpleDateFormat("dd.MM"),
            NumberFormat.getInstance());                       
        
        
        if (SelektiertVergl!=0) chart.addSubtitle(new TextTitle("Vergleich mit Jahr " + JTronicHandle.JahrVergleich.getSelectedItem().toString()));
        chart.setBackgroundPaint(Color.white);
        
        JTronicHandle.applyChartTheme(chart);
        
        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(false);
        plot.setDomainCrosshairLockedOnData(false);        
        if(!JTronicHandle.jRadioButton_jahresverlauf.isSelected()){
            DateAxis MyAxis = new DateAxis();
            MyAxis = (DateAxis) plot.getDomainAxis();
            MyAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
            MyAxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
     
            plot.setDomainGridlinesVisible(false);
             
        }
        
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getRangeAxis().setFixedDimension(15.0);
 
        plot.getDomainAxis().setTickLabelInsets(new RectangleInsets(2.0, 1.0, 2.0, 1.0));
        
        Jahr = Integer.parseInt(JTronicHandle.Auswahl_Übersicht.getSelectedItem().toString());
        
        for (i=0;i<366;i++){
            SummeZeit12Mon += (double)Zeit[Selektiert+1][i];
            Summekm12Mon += (double)kmJahr[Selektiert+1][i];
            Summehm12Mon += (double)HmJahr[Selektiert+1][i];
            
        }
        
        y4Werte[0] = SummeZeit12Mon;
        y5Werte[0] = Summekm12Mon;
        y6Werte[0] = Summehm12Mon;
        
        xTime[0]= new Day(31,12,Jahr-1);
        for (i=1;i<366;i++){
            try{
                xTime[i] = new Day(xTime[i-1].next().getStart());
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Exception GregorianCalender  " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);}
                
                if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()){
                    SummeZeit += (double)Zeit[Selektiert][i];
                    Summekm += (double)kmJahr[Selektiert][i];
                    Summehm += (double)HmJahr[Selektiert][i];
                    y1Werte[i] = SummeZeit;
                    y2Werte[i] = Summekm;
                    y3Werte[i] = Summehm;
                    
                    y4Werte[i] = y4Werte[i-1] - (double)Zeit[Selektiert+1][i]
                            + (double)Zeit[Selektiert][i];
                    y5Werte[i] = y5Werte[i-1] - (double)kmJahr[Selektiert+1][i]
                            +(double)kmJahr[Selektiert][i];
                    y6Werte[i] = y6Werte[i-1] - (double)HmJahr[Selektiert+1][i]
                            + (double)HmJahr[Selektiert][i];
                    
                } else{
                    Day  n = new Day(1,xTime[i].getMonth(),xTime[i].getYear());
                    try{
                        Kalender = new GregorianCalendar(xTime[i].getYear(),
                                xTime[i].getMonth()-1,
                                1);
                        
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Exception GregorianCalender  " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);
                        
                    }
                    int j=   (int) Kalender.get(Kalender.DAY_OF_YEAR);
                    for (int m=0;m<6;m++){
                        y1Werte[j+m +1] += (double)Zeit[Selektiert][i];
                        y2Werte[j+m+11] += (double)kmJahr[Selektiert][i];
                        y3Werte[j+m+21] += (double)HmJahr[Selektiert][i];
                        
                    }
                }
      
        }
        
        
        dataset = new TimeSeries("Trainingszeit "+JTronicHandle.Auswahl_Übersicht.getSelectedItem().toString());
        for (i=0; i<366;i++){
            dataset.add(xTime[i],y1Werte[i]);
        }
        
        dataset1 = new TimeSeriesCollection(dataset);
        NumberAxis axis = new NumberAxis();
        try{axis = (NumberAxis)plot.getRangeAxis().clone();}catch (Exception e){}
        axis.setLabel("Stunden");
        axis.setAutoRangeIncludesZero(true);
        axis.setLabelPaint(Color.BLACK);
        axis.setTickLabelPaint(Color.BLACK);
        plot.setRangeAxis(0, axis);
        plot.setRangeAxisLocation(0, AxisLocation.BOTTOM_OR_LEFT);
      
        plot.setDataset(Linecount, dataset1);
        plot.mapDatasetToRangeAxis(Linecount, 0);
        
        if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
            renderer = new StandardXYItemRenderer(2,ToolTip);
            renderer.setSeriesPaint(0, getColour(Linecount,255));
 
            plot.setRenderer(Linecount, renderer);
        } else {
            rendererb = new XYBarRenderer();
            rendererb.setSeriesPaint(0, getColour(Linecount,150));
            rendererb.setShadowVisible(false);
            rendererb.setDrawBarOutline(false);
            rendererb.setBarPainter(new StandardXYBarPainter());

            plot.setRenderer(Linecount, rendererb);
        }
        
        
        Linecount++;
        
        dataset = new TimeSeries("Trainingskilometer "+JTronicHandle.Auswahl_Übersicht.getSelectedItem().toString());
        for (i=0; i<366;i++){
            dataset.add(xTime[i],y2Werte[i]);
    
        }
        
        TimeSeriesCollection dataset2 = new TimeSeriesCollection(dataset);
   
        NumberAxis axis2 = new NumberAxis();
        try{axis2 = (NumberAxis)plot.getRangeAxis().clone();} catch (Exception e){};
        axis2.setLabel("Kilometer");
        axis2.setAutoRangeIncludesZero(true);
        axis2.setLabelPaint(Color.BLACK);
        axis2.setTickLabelPaint(Color.BLACK);
         
        plot.setRangeAxis(1, axis2);
        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);
        
        plot.setDataset(Linecount, dataset2);
        plot.mapDatasetToRangeAxis(Linecount, 1);
         
        if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
            renderer = new StandardXYItemRenderer(2,ToolTip);
            renderer.setSeriesPaint(0, getColour(Linecount,255));

            plot.setRenderer(Linecount, renderer);
        } else {
            rendererb = new XYBarRenderer();
            rendererb.setSeriesPaint(0, getColour(Linecount,150));
            rendererb.setShadowVisible(false);
            rendererb.setDrawBarOutline(false);
            rendererb.setBarPainter(new StandardXYBarPainter());

            plot.setRenderer(Linecount, rendererb);
        }
  
        Linecount++;
        
        dataset = new TimeSeries("Trainingshöhenmeter "+JTronicHandle.Auswahl_Übersicht.getSelectedItem().toString());
        for (i=0; i<366;i++){
            dataset.add(xTime[i],y3Werte[i]);
            
        }
        
        TimeSeriesCollection dataset3 = new TimeSeriesCollection(dataset);
       
        NumberAxis axis3 = new NumberAxis("Höhenmeter");
         try{axis3 = (NumberAxis)plot.getRangeAxis().clone();} catch (Exception e){};
        axis3.setLabel("Höhenmeter");
        axis3.setAutoRangeIncludesZero(true);
        axis3.setLabelPaint(Color.BLACK);
        axis3.setTickLabelPaint(Color.BLACK);
        
        plot.setRangeAxis(2, axis3);
        plot.setRangeAxisLocation(2, AxisLocation.BOTTOM_OR_RIGHT);
        
        plot.setDataset(Linecount, dataset3);
        plot.mapDatasetToRangeAxis(Linecount, 2);
        
        if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
            renderer = new StandardXYItemRenderer(2,ToolTip);
            renderer.setSeriesPaint(0, getColour(Linecount,255));

            plot.setRenderer(Linecount, renderer);
        } else {
            rendererb = new XYBarRenderer();
            rendererb.setSeriesPaint(0, getColour(Linecount,150));
            rendererb.setShadowVisible(false);
            rendererb.setDrawBarOutline(false);
            rendererb.setBarPainter(new StandardXYBarPainter());
  
            plot.setRenderer(Linecount, rendererb);
        }
        
        Linecount++;
       
        if(Vergleich == true){
            SummeZeit = 0;
            Summekm = 0;
            Summehm = 0;
            
            SummeZeit12Mon = 0;
            Summekm12Mon = 0;
            Summehm12Mon = 0;
            
            for (i=0;i<366;i++){
                SummeZeit12Mon += (double)Zeit[SelektiertVergl-1][i];
                Summekm12Mon += (double)kmJahr[SelektiertVergl-1][i];
                Summehm12Mon += (double)HmJahr[SelektiertVergl-1][i];
            }
            
            y4Werte[0] = SummeZeit12Mon;
            y5Werte[0] = Summekm12Mon;
            y6Werte[0] = Summehm12Mon;
            
            for (i=1;i<366;i++){
                try{
   
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Exception GregorianCalender  " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);};
                    if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()){
                        SummeZeit += (double)Zeit[SelektiertVergl-1][i];
                        Summekm += (double)kmJahr[SelektiertVergl-1][i];
                        Summehm += (double)HmJahr[SelektiertVergl-1][i];
                        y7Werte[i] = SummeZeit;
                        y8Werte[i] = Summekm;
                        y9Werte[i] = Summehm;
                        
                        y4Werte[i] = y4Werte[i-1] - (double)Zeit[SelektiertVergl-1][i]
                                + (double)Zeit[Selektiert][i];
                        y5Werte[i] = y5Werte[i-1] - (double)kmJahr[SelektiertVergl-1][i]
                                +(double)kmJahr[Selektiert][i];
                        y6Werte[i] = y6Werte[i-1] - (double)HmJahr[SelektiertVergl-1][i]
                                + (double)HmJahr[Selektiert][i];
                    } else{
                        Day  n = new Day(1,xTime[i].getMonth(),xTime[i].getYear());
                        try{
                            Kalender = new GregorianCalendar(xTime[i].getYear(),
                                    xTime[i].getMonth()-1,
                                    1);
                            
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null,"Exception GregorianCalender  " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);
                            
                        }
                        int j=   (int) Kalender.get(Kalender.DAY_OF_YEAR);
                        for (int m=0;m<5;m++){
                            y7Werte[j+m+4] += (double)Zeit[SelektiertVergl-1][i];
                            y8Werte[j+m+14] += (double)kmJahr[SelektiertVergl-1][i];
                            y9Werte[j+m+24] += (double)HmJahr[SelektiertVergl-1][i];
                            
                        }
                        
                    }
                    
            }
            
            if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()){
                dataset = new TimeSeries("Trainingszeit über 12 Monate ");
                for (i=0; i<366;i++){
                    dataset.add(xTime[i],y4Werte[i]);
                }
                
                TimeSeriesCollection dataset4 = new TimeSeriesCollection(dataset);
                
                plot.setDataset(Linecount, dataset4);
                plot.mapDatasetToRangeAxis(Linecount, 0);

                if (JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
                    renderer = new StandardXYItemRenderer(2, ToolTip);
                    renderer.setSeriesPaint(0, getColour(Linecount, 255));

                    plot.setRenderer(Linecount, renderer);
                } else {
                    rendererb = new XYBarRenderer();
                    rendererb.setSeriesPaint(0, getColour(Linecount, 150));
                    rendererb.setShadowVisible(false);
                    rendererb.setBarPainter(new StandardXYBarPainter());

                    plot.setRenderer(Linecount, rendererb);
                }

                Linecount++;
         
                dataset = new TimeSeries("Trainingskilometer über 12 Monate ");
                for (i=0; i<366;i++){
                    dataset.add(xTime[i],y5Werte[i]);
                }
                
                TimeSeriesCollection dataset5 = new TimeSeriesCollection(dataset);
                
                plot.setDataset(Linecount, dataset5);
                plot.mapDatasetToRangeAxis(Linecount, 1);
                
                if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
                    renderer = new StandardXYItemRenderer(2,ToolTip);
                    renderer.setSeriesPaint(0, getColour(Linecount,255));
 
                    plot.setRenderer(Linecount, renderer);
                } else {
                    rendererb = new XYBarRenderer();
                    rendererb.setSeriesPaint(0, getColour(Linecount,150));
            rendererb.setShadowVisible(false);
            rendererb.setGradientPaintTransformer(null);
            rendererb.setBarPainter(new StandardXYBarPainter());

                    plot.setRenderer(Linecount, rendererb);
                }
                 
                Linecount++;
                
                dataset = new TimeSeries("Trainingshöhenmeter über 12 Monate ");
                for (i=0; i<366;i++){
                    dataset.add(xTime[i],y6Werte[i]);
                }
                
                TimeSeriesCollection dataset6 = new TimeSeriesCollection(dataset);
                
                plot.setDataset(Linecount, dataset6);
                plot.mapDatasetToRangeAxis(Linecount, 2);
                
                if (JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
                    renderer = new StandardXYItemRenderer(2, ToolTip);
                    renderer.setSeriesPaint(0, getColour(Linecount, 255));

                    plot.setRenderer(Linecount, renderer);
                } else {
                    rendererb = new XYBarRenderer();
                    rendererb.setSeriesPaint(0, getColour(Linecount, 150));
                    rendererb.setShadowVisible(false);
                    rendererb.setGradientPaintTransformer(null);
                    rendererb.setBarPainter(new StandardXYBarPainter());

                    plot.setRenderer(Linecount, rendererb);
                }
                
            }
            Linecount++;
            
            dataset = new TimeSeries("Trainingszeit " + JTronicHandle.JahrVergleich.getSelectedItem().toString());
            for (i=0; i<366;i++){
                dataset.add(xTime[i],y7Werte[i]);
            }
            
            TimeSeriesCollection dataset7 = new TimeSeriesCollection(dataset);
            
            plot.setDataset(Linecount, dataset7);
            plot.mapDatasetToRangeAxis(Linecount, 0);
            
            if (JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
                renderer = new StandardXYItemRenderer(2, ToolTip);
                renderer.setSeriesPaint(0, getColour(Linecount, 255));

                plot.setRenderer(Linecount, renderer);
            } else {
                rendererb = new XYBarRenderer();
                rendererb.setSeriesPaint(0, getColour(Linecount, 150));
                rendererb.setShadowVisible(false);
                rendererb.setGradientPaintTransformer(null);
                rendererb.setBarPainter(new StandardXYBarPainter());

                plot.setRenderer(Linecount, rendererb);
            }
            Linecount++;

            dataset = new TimeSeries("Trainingskilometer " + JTronicHandle.JahrVergleich.getSelectedItem().toString());
            for (i = 0; i < 366; i++) {
                dataset.add(xTime[i],y8Werte[i]);
            }
            
            TimeSeriesCollection dataset8 = new TimeSeriesCollection(dataset);
            
            plot.setDataset(Linecount, dataset8);
            plot.mapDatasetToRangeAxis(Linecount, 1);
            
            if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
                renderer = new StandardXYItemRenderer(2, ToolTip);
                renderer.setSeriesPaint(0, getColour(Linecount, 255));

                plot.setRenderer(Linecount, renderer);
            } else {
                rendererb = new XYBarRenderer();
                rendererb.setSeriesPaint(0, getColour(Linecount, 150));
                rendererb.setShadowVisible(false);
                rendererb.setGradientPaintTransformer(null);
                rendererb.setBarPainter(new StandardXYBarPainter());

                plot.setRenderer(Linecount, rendererb);
            }
         
            Linecount++;
            
            dataset = new TimeSeries("Trainingshöhenmeter " + JTronicHandle.JahrVergleich.getSelectedItem().toString());
            for (i=0; i<366;i++){
                dataset.add(xTime[i],y9Werte[i]);
            }
            
            TimeSeriesCollection dataset9 = new TimeSeriesCollection(dataset);
            
            plot.setDataset(Linecount, dataset9);
            plot.mapDatasetToRangeAxis(Linecount, 2);
            
            if(JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
                renderer = new StandardXYItemRenderer(2, ToolTip);
                renderer.setSeriesPaint(0, getColour(Linecount, 255));

                plot.setRenderer(Linecount, renderer);
            } else {
                rendererb = new XYBarRenderer();
                rendererb.setSeriesPaint(0, getColour(Linecount, 150));
                rendererb.setShadowVisible(false);
                rendererb.setGradientPaintTransformer(null);
                rendererb.setBarPainter(new StandardXYBarPainter());

                plot.setRenderer(Linecount, rendererb);
            }
            
        }
        
        if(!JTronicHandle.jRadioButton_jahresverlauf.isSelected()){
            
            double max = axis.getRange().getUpperBound();
            for (i= 0; i<12; i++){
                try{
                    Kalender = new GregorianCalendar(Integer.parseInt(DataProperty.getProperty("Jahr","0")),
                            i,
                            1
                            );
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"JUebersicht\nException GregorianCalender  " + e,"Achtung!", JOptionPane.ERROR_MESSAGE);}
                    
                    
                    y10Werte[Kalender.get(Kalender.DAY_OF_YEAR)] = max;
                    y10Werte[Kalender.get(Kalender.DAY_OF_YEAR)+1] = 0;
            }
            dataset = new TimeSeries("");
            
            for (i=0; i<366;i++){
                
                dataset.add(xTime[i],y10Werte[i]);
                
            }
            TimeSeriesCollection dataset10 = new TimeSeriesCollection(dataset);
            plot.setDataset(Linecount+1, dataset10);
            plot.mapDatasetToRangeAxis(Linecount+1, 0);
            
            renderer = new StandardXYItemRenderer(2,ToolTip);
            renderer.setSeriesPaint(0, Color.white);
 
            plot.setRenderer(Linecount+1, renderer);
            plot.getDomainAxis().setAutoRange(false);
            axis.setRange(0, max);

        }

        if (JTronicHandle.jRadioButton_jahresverlauf.isSelected()) {
            axis2.setRange(0, axis2.getRange().getUpperBound() * 1.5);
            axis3.setRange(0, axis3.getRange().getUpperBound() * 2);
            plot.setDomainCrosshairValue((double) new GregorianCalendar(Jahr,
                    Today.get(Today.MONTH),
                    Today.get(Today.DAY_OF_MONTH)).getTimeInMillis());

        }

        chart.setPadding(padding);
        ChartPanel Panel = new ChartPanel(chart);
        Panel.setDismissDelay(100000);

        return Panel;         
        
    }
    
    
    private static Paint getColour(int Farbe, int alpha) {

        Paint Colour = Color.BLACK;
        Farbe = Farbe % 7;
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
    
   
    public java.util.Properties   DataProperty;
    private JCicloTronic JTronicHandle;
    private  GregorianCalendar Kalender;
    private  GregorianCalendar Today;
    private RectangleInsets padding;
}
