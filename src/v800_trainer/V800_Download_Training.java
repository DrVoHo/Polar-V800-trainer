package v800_trainer;

/*
 * V800_Download_Training.java
 *
 * SourceFile is part of Chainwheel

 * Downloads Training Data from V800
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
 * Copyright (C) 2013 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */


import java.io.File;
import java.io.FileOutputStream;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.file.Files;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.usb4java.BufferUtils;

import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 * Dumps a tree of all found USB devices.
 *
 * @author Klaus Reimer <k@ailis.de>
 */
public class V800_Download_Training {

    /**
     * Dumps the specified device and its sub devices.
     *
     * @param device The USB device to dump.
     * @param level The indentation level.
     */
    
    
    public void V800_Download_Training(){
    }
    
 
    
    public boolean start(JCicloTronic JHandle) {
        // Initialize the libusb context

        TronicHandle = JHandle;
        FileSeparator = TronicHandle.SystemProperties.getProperty("file.separator");
        Raw_data = TronicHandle.Properties.getProperty("data.dir") + FileSeparator + "V800_Raw";
        int result = LibUsb.init(null);
        if (result != LibUsb.SUCCESS) {
            //         throw new LibUsbException("Unable to initialize libusb", result);
            JOptionPane.showMessageDialog(null, "Fehler bei Initialisierung USB", "Achtung!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        short vendor = 0x0da4;  //Polar
        short product = 0x0008;

        handle = LibUsb.openDeviceWithVidPid(null, vendor,
                product);
        if (handle == null) {
            JOptionPane.showMessageDialog(null, "Polar lässt sich nicht öffnen", "Achtung!", JOptionPane.ERROR_MESSAGE);
            return false;
//           System.err.println("Test device not found.");
            //           System.exit(1);
        }

        // Claim the ADB interface
        result = LibUsb.claimInterface(handle, INTERFACE);
        if (result != LibUsb.SUCCESS) {
            //       throw new LibUsbException("Unable to claim interface", result);
            JOptionPane.showMessageDialog(null, "Kein Zugriff auf Polar Interface", "Achtung!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void stop() {
        stop_com(handle);   // USB terminieren
    }

    private static boolean is_end(byte[] packet) {
        if ((packet[1] & 0x03) == 1) {
            return false;
        } else {
            return true;
        }
    }

    private static byte[] generate_ack(byte packet_num) {
        byte[] packet = new byte[64];

        packet[0] = 0x01;
        packet[1] = 0x05;
        packet[2] = packet_num;

        return packet;
    }

    private static byte[] generate_request(String request) {
        byte[] packet = new byte[64];
        byte[] b = new byte[0];

        packet[0] = 01;
        packet[1] = (byte) (((byte) request.length() + 8) << 2);
        packet[2] = 0x00;
        packet[3] = (byte) (request.length() + 4);
        packet[4] = 0x00;
        packet[5] = 0x08;
        packet[6] = 0x00;
        packet[7] = 0x12;
        packet[8] = (byte) request.length();
        try {
            b = request.getBytes("utf8");
        } catch (Exception e) {
        }
        for (int i = 0; i < b.length; i++) {
            packet[9 + i] = b[i];
        }

        return packet;
    }

    private static byte[] getbytearray(ByteBuffer buffer) {

        byte[] dummy = new byte[64];

        for (int i = 0; i < 64; i++) {
            dummy[i] = buffer.get(i);
        }
        return dummy;
    }

    private static void stop_com(DeviceHandle handle) {

        // Release the ADB interface
        int result = LibUsb.releaseInterface(handle, INTERFACE);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to release interface", result);
        }

        // Close the device
        LibUsb.close(handle);

        // Deinitialize the libusb context
        LibUsb.exit(null);

    }

    public ArrayList<String> get_sessions(ArrayList<String> sessions) {
         
        DirList = new ArrayList();
       String session_time;

 
                String session;
                ArrayList<String> session_split = new ArrayList();
                ArrayList<String> multi_sports = new ArrayList(), files = new ArrayList(), temp_session_files = new ArrayList(), temp_files = new ArrayList();
                int session_iter, files_iter, multi_sports_iter, multi_sport_cnt;
  
                for (session_iter = 0; session_iter < sessions.size(); session_iter++) {
                    if(TronicHandle.pm!=null)TronicHandle.pm.setNote(sessions.get(session_iter));
                    if(TronicHandle.pm!=null)TronicHandle.pm.setProgress((int) 100.0 * session_iter / sessions.size());
                    session = sessions.get(session_iter);
                    session_split = new ArrayList();
                    Collections.addAll(session_split, session.split("/"));

                    if (session_split.size() == 2) {
                        multi_sports = new ArrayList();
                        multi_sports = get_V800_data("/U/0/" + session_split.get(0) + "/E/" + session_split.get(1) + "/", 0, false);

                        multi_sport_cnt = 0;

                        for (multi_sports_iter = 0; multi_sports_iter < multi_sports.size(); multi_sports_iter++) {
                            if (multi_sports.get(multi_sports_iter).contains("/")) {
                                // DirList.add(session_split.get(0) + session_split.get(1) + "_" + multi_sports.get(multi_sports_iter));
                                files = new ArrayList();
                                files = get_V800_data("/U/0/" + session_split.get(0) + "/E/" + session_split.get(1) + "/" + multi_sports.get(multi_sports_iter) + "/", 0, false);

                                for (files_iter = 0; files_iter < files.size(); files_iter++) {
                                    temp_files = get_V800_data("/U/0/" + session_split.get(0) + "/E/" + session_split.get(1) + "/" + multi_sports.get(multi_sports_iter) + "/" + files.get(files_iter), multi_sport_cnt, false); //QString(tr("%1/%2/E/%3/%4/%5")).arg(tr(V800_ROOT_DIR)).arg(session_split[0]).arg(session_split[1]).arg(multi_sports[multi_sports_iter]).arg(files[files_iter]), multi_sport_cnt);

                                    if (temp_files.size() == 1) {
                                        temp_session_files.add(temp_files.get(0));
                                    }
                                }

                                temp_files = get_V800_data("/U/0/" + session_split.get(0) + "/E/" + session_split.get(1) + "/TSESS.BPB", multi_sport_cnt, false); //.arg(tr(V800_ROOT_DIR)).arg(session_split[0]).arg(session_split[1]), multi_sport_cnt);
                                if (temp_files.size() == 1) {
                                    temp_session_files.add(temp_files.get(0));
                                }
                                temp_files = get_V800_data("/U/0/" + session_split.get(0) + "/E/" + session_split.get(1) + "/PHYSDATA.BPB", multi_sport_cnt, false); //.arg(tr(V800_ROOT_DIR)).arg(session_split[0]).arg(session_split[1]), multi_sport_cnt);

                                if (temp_files.size() == 1) {
                                    temp_session_files.add(temp_files.get(0));
                                }

                                multi_sport_cnt++;
                            }
                        }

                    }
   
            } //Ende sessions iter
 
        return DirList;
    }

    public  ArrayList<String> get_all_sessions() {
        ArrayList<String> dates, times = new ArrayList(), files = new ArrayList();
        sessions = new ArrayList();
        int dates_iter, times_iter, files_iter;
        boolean session_exists = false;

        dates = get_V800_data("/U/0/", 0, false);

        for (dates_iter = 0; dates_iter < dates.size(); dates_iter++) {
            times.clear();
            if (dates.get(dates_iter).contains("/")) {
                times = get_V800_data("/U/0/" + dates.get(dates_iter) + "E/", 0, false);

                for (times_iter = 0; times_iter < times.size(); times_iter++) {
                    files.clear();
                    files = get_V800_data("/U/0/" + dates.get(dates_iter) + "E/" + times.get(times_iter) + "00/", 0, false);

                    for (files_iter = 0; files_iter < files.size(); files_iter++) {
                        if (files.get(files_iter).matches("SAMPLES.GZB") == true) //QString(files[files_iter]).compare(tr("SAMPLES.GZB")) == 0)
                        {
                            session_exists = true;
                            break;
                        }
                    }

                    if (session_exists) {
                        String session_time = dates.get(dates_iter) + times.get(times_iter);
                        sessions.add(session_time);
                        session_exists = false;
                    }
                }
            }
        }
        return sessions;
    }

    private static ArrayList<Byte> add_to_full(byte[] packet, ArrayList<Byte> full, boolean initial_packet, boolean final_packet) {
        ArrayList<Byte> new_full = full;

        int follow = packet[1] & 0x03;
        int temp = ((int) packet[1]) & 0xff;
        byte size = (byte) (temp >>> 2);  //bit 1 und 2 sind Kennung ob finales Packet

        if (initial_packet) {
            // final packets have a trailing 0x00 we don't want
            if (final_packet) {
                size -= 4;
            } else {
                size -= 3;
            }

            for (int i = 5; i < 5 + size; i++) {
                new_full.add(packet[i]);
            }

        } else {
            // final packets have a trailing 0x00 we don't want
            if (final_packet) {
                size -= 2;
            } else {
                size -= 1;
            }

            for (int i = 3; i < 3 + size; i++) {
                new_full.add(packet[i]);
            }

        }

        return new_full;
    }

    private static ArrayList<String> extract_dir_and_files(ArrayList<Byte> full) {
        ArrayList<String> dir_and_files = new ArrayList();
        int full_state = 0, size = 0, loc = 0;

        while (loc < full.size()) {
            switch (full_state) {
                case 0:
                    /* look for 0x0A */
                    if (full.get(loc) == (char) 0x0A) {
                        loc++;
                        full_state = 1;
                    }

                    loc++;
                    break;
                case 1:
                    /* is this the second 0x0A? */
                    if (full.get(loc) == (char) 0x0A) {
                        full_state = 2;
                    } else {
                        full_state = 0;
                    }

                    loc++;
                    break;
                case 2:
                    /* get the size */
                    size = full.get(loc);

                    full_state = 3;
                    loc++;
                    break;
                case 3:
                    /* we need a 0x10 after the string */
                    if (full.get(loc + size) == 0x10) {
                        full_state = 4;
                    } else {
                        full_state = 0;
                    }
                    break;
                case 4:
                    /* now get the full string */
                    String name = "";
                    byte[] temp = new byte[size];
                    for (int i = loc; i < loc + size; i++) {
                        temp[i - loc] = full.get(i);
                    }
                    name = new String(temp);

                    dir_and_files.add(name);

                    full_state = 0;
                    loc += size;
                    break;
            }
        }

        return dir_and_files;
    }

    private static ArrayList<String> get_V800_data(String request, int multi_sport, boolean debug) {

        ArrayList<String> data = new ArrayList();

        ByteBuffer packet = BufferUtils.allocateByteBuffer(64);

        ArrayList<Byte> full = new ArrayList();
        ArrayList<Byte> tempdata = new ArrayList();
        ArrayList<String> session_split = new ArrayList();
        int t = 0;
        int cont = 1, usb_state = 0;
        int retry = 0;
        boolean data_OK = false;
        byte packet_num = 0;
        boolean initial_packet = true;

 
        try {
            while (cont != 0) {
                // usb state machine for reading
                switch (usb_state) {
                    case 0: // send a command to the watch
                        packet = BufferUtils.allocateByteBuffer(64);

                        packet.put(generate_request(request));

                        write(handle, getbytearray(packet));
                        packet_num = 0;
                        usb_state = 1;
                        break;
                    case 1: // we want to read the buffer now
                        packet = BufferUtils.allocateByteBuffer(64);
                        packet = read(handle, 64);

                        // check for end of buffer
                        if (is_end(getbytearray(packet))) {
                            full = add_to_full(getbytearray(packet), full, initial_packet, true);
                            usb_state = 4;
                        } else {
                            full = add_to_full(getbytearray(packet), full, initial_packet, false);
                            usb_state = 2;
                        }

                        // initial packet seems to always have two extra bytes in the front, 0x00 0x00
                        if (initial_packet) {
                            initial_packet = false;
                        }
                        break;
                    case 2: // send an ack packet
                        packet = BufferUtils.allocateByteBuffer(64);
                        packet.put(generate_ack(packet_num));
                        if (packet_num == 0xff) {
                            packet_num = 0x00;
                        } else {
                            packet_num++;
                        }
                        write(handle, getbytearray(packet));

                        usb_state = 1;
                        break;
                    case 4:
                        if (!debug) {
                            data_OK=check_array(full,tempdata);
                            if(!data_OK){
                                if(retry!=0){
                                    System.out.println("Datenlesefehler Wiederholung  " + retry);
                                }
                                retry++;
                                tempdata = (ArrayList<Byte>) full.clone();
                                full = new ArrayList();
                                usb_state = 0;
                                packet_num = 0;
                                initial_packet = true;
                                break;
                            }
                            if (!request.contains(".")) {
                                data = extract_dir_and_files(full);
                            } else if (request.contains("/E/")) {
                                session_split = new ArrayList();
                                Collections.addAll(session_split, request.split("/"));

                                String date, time, file;

                                if (session_split.size() < 7) {
                                    System.out.println("Malformed request!\n");

                                    cont = 0;
                                    break;
                                } else {
                                    date = session_split.get(3);
                                    time = session_split.get(5);
                                    file = session_split.get(session_split.size() - 1);
                                }

                                String tag = date + time;
                    

                                File raw_dir = new File(Raw_data + "/" + tag +"_" + multi_sport);
                                if (Files.notExists(raw_dir.toPath())) {
                                    Files.createDirectory(raw_dir.toPath());
             
                                }
                                DirList.add(raw_dir.toPath().toString());
                                String raw_dest = raw_dir.toString() + "/" + file;//QString(tr("%1/%2")).arg(raw_dir).arg(file));

                              
                                FileOutputStream Ausgabedatei = new FileOutputStream(raw_dest);
                                for (int i = 0; i < full.size(); i++) {
                                    Ausgabedatei.write(full.get(i));
                                }

                                Ausgabedatei.close();

                                data.add(raw_dest);
                            } else {
                                System.out.println("Unknown file type! ->  " + request);//.toUtf8().constData());
                            }
                        } else if (request.contains(".")) {
                            request = request.replace("/", "_");

                    

                            String raw_dest = Raw_data + "/" + request;

                            
                            FileOutputStream Ausgabedatei = new FileOutputStream(raw_dest);
                            for (int i = 0; i < full.size(); i++) {
                                Ausgabedatei.write(full.get(i));
                            }

                            Ausgabedatei.close();

                        }

                        cont = 0;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler in get_V800_data 509 " + e + " " + e.getStackTrace());
        }
        return data;
    }

    
    private static boolean check_array(ArrayList<Byte> full,ArrayList<Byte> tempdata){
       if(full.size()!=tempdata.size()) return false;
       for (int i = 0; i<full.size();i++){
           if (!Objects.equals(full.get(i), tempdata.get(i)))return false;
       }
       return true;
    }
    
    
    /**
     * Writes some data to the device.
     *
     * @param handle The device handle.
     * @param data The data to send to the device.
     */
    public static void write(DeviceHandle handle, byte[] data) {
        ByteBuffer buffer = BufferUtils.allocateByteBuffer(data.length);
        buffer.put(data);
        IntBuffer transferred = BufferUtils.allocateIntBuffer();
        int result = LibUsb.bulkTransfer(handle, OUT_ENDPOINT, buffer,
                transferred, TIMEOUT);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to send data", result);
        }

    }

    /**
     * Reads some data from the device.
     *
     * @param handle The device handle.
     * @param size The number of bytes to read from the device.
     * @return The read data.
     */
    public static ByteBuffer read(DeviceHandle handle, int size) {
        ByteBuffer buffer = BufferUtils.allocateByteBuffer(size).order(
                ByteOrder.LITTLE_ENDIAN);
        IntBuffer transferred = BufferUtils.allocateIntBuffer();
        int result = LibUsb.bulkTransfer(handle, IN_ENDPOINT, buffer,
                transferred, TIMEOUT);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to read data", result);
        }
     
        return buffer;
    }

   

    private static final byte INTERFACE = 0;

    /**
     * The ADB input endpoint of the Polar.
     */
    private static final byte IN_ENDPOINT = (byte) 0x81;

    /**
     * The ADB output endpoint of the Polar.
     */
    private static final byte OUT_ENDPOINT = 0x01;

    /**
     * The communication timeout in milliseconds.
     */
    private static final int TIMEOUT = 5000;

    private static DeviceHandle handle;

    private static ArrayList<String> sessions;
    
    private static ArrayList<String> DirList;
            

    private JCicloTronic TronicHandle;

    private static String Raw_data;
    private String FileSeparator;
}
