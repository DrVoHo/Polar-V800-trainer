package v800_trainer;

/*
 * ParserFunktion.java
 *
 * SourceFile is part of Chainwheel

 * List of Functions for parsing V800 files for Program Options
 */



/**
 *Origin Based on Bipolar written in QTC++    
Copyright 2014-2015 Paul Colby
 * @author migration to java volker hochholzer
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


//import java.io.FileInputStream;
import java.io.InputStream;

public class ParserFunktion {

    public class DataPair {

        public int bit32;
        public byte bit8;

        public void DataPair(int a, byte b) {
            bit32 = a;
            bit8 = b;
        }
    }

    public static Object parseFloatNumber(InputStream data, int si) {

        int size = (int) (si / 8);
        long result = 0;
        int short_result = 0;
        int readData = 0;
        byte[] array = new byte[1];

        for (int i = 0; i < size; i++) {
            try {
                readData = data.read(array, 0, 1);
            } catch (Exception e) {
                System.out.println("Parser 41" + "Error " + e.getMessage());
            }
            if (readData <= 0) {
                return null;
            }
            if (size == 4) {
                short_result += ((int) array[0] & 0xff) << (8 * i);
            } else {
                result += ((long) array[0] & 0xff) << (8 * i);
            }

        }
        if (size == 4) {
            return Float.intBitsToFloat(short_result);
        } else {
            return Double.longBitsToDouble(result);
        }

    }

    public static Object parseFixedNumber(InputStream data, int si) {

        int size = (int) (si / 8);
        long result = 0;
        int short_result = 0;
        int readData = 0;
        byte[] array = new byte[1];

        for (int i = 0; i < size; i++) {
            try {
                readData = data.read(array, 0, 1);
            } catch (Exception e) {
                System.out.println("Parser 68" + "Error " + e.getMessage());
            };
            if (readData <= 0) {
                return null;
            }
            if (size == 4) {
                short_result += ((int) array[0] & 0xff) << (8 * i);
            } else {
                result += ((long) array[0] & 0xff) << (8 * i);
            }

        }
        if (size == 4) {
            return (long) short_result;
        } else {
            return result;
        }

    }

    public static byte[] returnBytes(InputStream data, int size) {

        byte[] dummy = new byte[size];
        try {
            int read = data.read(dummy, 0, size);
        } catch (Exception e) {
            System.out.println("Parser 115" + "Error " + e.getMessage());
        }
        return dummy;
    }

    public static Object parseSignedVarint(InputStream data) {
        Object variant = parseUnsignedVarint(data);
        if (variant == null) {
            return null;
        }

        long result = (long) variant;
        //result = result >>1;
        int faktor = 0;
        int adder = 0;
        if ((result & 0x1) == 1) {
            faktor = -1;
            adder = -1;
        } else {
            faktor = 1;
            adder = 0;
        }

        return (long) ((result >>> 1) * faktor + adder);

    }

    public static Object parseStandardVarint(InputStream data) {
        Object variant = parseUnsignedVarint(data);
        if (variant == null) {
            return null;
        }
        return (long) variant;  //ursprünglich unsigned long hier aber signed
    }

    public static Object parseUnsignedVarint(InputStream data) {

        long result = 0;
        int readData = 0;
        Byte a;

        int index;
        byte[] array = new byte[1];
        array[0] = 0;

        for (a = (byte) 0xFF, index = 0; (a & (byte) 0x80) != 0; ++index) {  // first bit true means more data
            try {
                readData = data.read(array, 0, 1);
            } catch (Exception e) {
                System.out.println("Parser 143" + " Error " + e.getMessage());
            }
            if (readData <= 0) {   //kein Fehlerfall, wird zum Abbruch eines Enumerators verwendet
//            System.out.println("Parser 146 " + "readData<=0  ");
                return null;
            }
            a = (byte) array[0];

            result += (a & (byte) 0x7F) << (7 * index);
        }
        //  System.out.println("Parser 153  " + "read OK");
        return result;
    }

}
