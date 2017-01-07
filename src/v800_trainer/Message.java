package v800_trainer;

/*
 * Message.java
 *
 * SourceFile is part of Chainwheel

 * Paart of Files for V800 Read and Decode
 */



/**
 *
 * @author // migration to java volker hochholzer
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
Based on Bipolar written in QTC++    
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

import java.io.InputStream;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;



public class Message
{

    public Message(HashMap<String,FieldInfo> fieldInfo, String pSeparator)
   
{
    pathSeparator = pSeparator;
    FieldInfoMap = fieldInfo;
 
}
    
    
    public HashMap <String, FieldInfo> FieldInfoMap;
 
    public String pathSeparator;
    
    public  HashMap parsedFields;
    
  

    public static class FieldInfo {

        public String fieldName;
 
        public Types.ScalarType scalarType;
        FieldInfo(Object fName, Object sType) {
            fieldName = (String)fName;
            scalarType = (Types.ScalarType) sType;
        }
   
    }
    
   public class DataPair{
       public DataPair(){bit32=0;bit8=0;}; 
       public DataPair(int a,byte b){bit32=a; bit8 = b;};

       public int bit32;
       public byte bit8;
      
   }
    
    


HashMap parse(byte[] data, String tagPathPrefix){
    
    ByteArrayInputStream data2 = new ByteArrayInputStream(data);
 //   System.out.println("Message  107  return data Steam  "+   tagPathPrefix);
    return parse(data2,tagPathPrefix);

}


HashMap parse(InputStream data, String tagPathPrefix) {
        //   System.out.println("Message  115  parse entry  "+   tagPathPrefix);
        HashMap parsedFields = new HashMap();
        int dataRead = 0;
        try {
            dataRead = data.available();
        } catch (Exception e) {
            System.out.println("Message 119  data empty " + e.getMessage());
        }
        DataPair tagAndType;
        while (dataRead != 0) {
            // Fetch the next field's tag index and wire type.
            tagAndType = parseTagAndType(data);
            if (tagAndType.bit32 == 0) {

                try {
                    data.close();
                } catch (Exception e) {
                    System.out.println("Message 128" + " Error tagAndType = 0   data.close");
                }
                System.out.println("Message 130" + " Error tagAndType = 0 ");

                return parsedFields;
            }

            // If this is a (deprecated) "end group", return the parsed group.
            if (tagAndType.bit8 == Types.WireType.EndGroup.getValue()) {  //Types::EndGroup = 4
                try {
                    data.close();
                } catch (Exception e) {
                    System.out.println("Message 138" + " Error - EndGroup   data.close " + e.getMessage());
                }
                //           System.out.println("Message 140" + " Return OK - EndGroup");
                return parsedFields;
            }

            // Get the (optional) field name and type hint for this field.
            String tagPath = tagPathPrefix + tagAndType.bit32;

            FieldInfo fieldInfo = FieldInfoMap.get(tagPath); // Note intentional fallback to default-constructed.
            if (fieldInfo == null) {  //regulärer Vorgang um tagPath zurückzusetzen
                //           System.out.println("Message 150   fieldInfo.fieldName = null "+tagPath);
                fieldInfo = new FieldInfo("" + tagAndType.bit32, Types.ScalarType.Unknown);
            }
            if (fieldInfo.fieldName.isEmpty()) {
                System.out.println("Message 154   fieldInfo.fieldName = empty");
                fieldInfo.fieldName = "" + tagAndType.bit32;
            }

            // Parse the field value.
            Object value = parseValue(data, tagAndType.bit8, fieldInfo.scalarType, tagPath);
            if (value == null) {
                try {
                    data.close();
                } catch (Exception e) {
                    System.out.println("Message 155" + " value = null" + "data.close error   " + e.getMessage());
                }
                System.out.println("Message 154" + " value = null");
                return new HashMap();
            }
            //      System.out.println("Message 169" + " value " + value.toString());

            // Add the parsed value(s) to the parsed fields map.
            List<Object> list = new ArrayList<Object>();
            list = (List) parsedFields.get(fieldInfo.fieldName);
            if (list == null) {
                list = new ArrayList();
            }

            if (value instanceof List) {
                for (int i = 0; i < ((List) value).size(); i++) {
                    list.add(((List) value).get(i));
                }
            } else {
                list.add(value);
            }

            parsedFields.put(fieldInfo.fieldName, list);
            try {
                dataRead = data.available();
            } catch (Exception e) {
            }

        }
        try {
            data.close();
        } catch (Exception e) {
            System.out.println("Message 192  data.close error " + e.getMessage());
        }
//    System.out.println("Message 194 " + "Ende parsedFields size: " + parsedFields.size());
        return parsedFields;

    }

public DataPair parseTagAndType(InputStream data)
{
    long tagAndType = (long)ParserFunktion.parseUnsignedVarint(data);
    DataPair ret = new DataPair();
    if(tagAndType!=-1){ret = new DataPair((int)(tagAndType >>> 3), (byte)(tagAndType & (long)0x07)); 
      //  System.out.println("Message 204  Return parseTagAndType tag "+ret.bit32 +" type  "+ret.bit8);
        return ret;
    }
        else {
        System.out.println("Message 208  Return parseTagAndType fail");
        return ret;
    } 
}

private Object parseValue(InputStream data, byte wireType,
            Types.ScalarType scalarType,
            String tPath) {
        String tagPath = tPath;
// A small sanity check. In this case, the wireType will take precedence.

        if ((scalarType != Types.ScalarType.Unknown)
                && (wireType != Types.WireType.LengthDelimeted.getValue())
                && (wireType != Types.getWireType(scalarType))) {
            System.out.println("Message 217" + "wireType + Scalar passt nicht   " + wireType + " = " + Types.getWireType(scalarType) + "soll:  " + scalarType);

        }

        if (wireType == 0) {//Types.WireType.Varint.getValue()
            //case Types.WireType.ParserFunktion.getValue(): // int32, int64, uint32, uint64, sint32, sint64, bool, enum.
            //     System.out.println("Message 223  " + "wireType   Varint " + scalarType);
            switch (scalarType) {
                case Int32:
                    return ParserFunktion.parseStandardVarint(data);
                case Int64:
                    return ParserFunktion.parseStandardVarint(data);
                case Uint32:
                    return ParserFunktion.parseUnsignedVarint(data);
                case Uint64:
                    return ParserFunktion.parseUnsignedVarint(data);
                case Sint32:
                    return ParserFunktion.parseSignedVarint(data);
                case Sint64:
                    return ParserFunktion.parseSignedVarint(data);
                case Bool:
                    return ParserFunktion.parseStandardVarint(data);
                case Enumerator:
                    return ParserFunktion.parseStandardVarint(data);
                default: {
                    //                System.out.println("Message 240  Return wireType Varint scalarType default");
                    return ParserFunktion.parseStandardVarint(data);
                }
            }
        } else if (wireType == 1) { // fixed64, sfixed64, double.  Types.WireType.SixtyFourBit.getValue()
            //    System.out.println("Message 245  " + "wireType  64b " + scalarType);
            switch (scalarType) {
                case Fixed64:
                    return ParserFunktion.parseFixedNumber(data, 64);//unsigned long
                case Sfixed64:
                    return ParserFunktion.parseFixedNumber(data, 64); //signed long??
                case Double:
                    return ParserFunktion.parseFloatNumber(data, 64);  //double
                default: {
                    //System.out.println("Message 251  Return wireType 64bit  scalarType default");
                    return ParserFunktion.returnBytes(data, 8);
                } // The raw 8-byte sequence.
            }
        } else if (wireType == 2) {//Types.WireType.LengthDelimeted.getValue()
            // string, bytes, embedded messages, packed repeated fields.
            //      System.out.println("Message 259" + "wireType  LengthDelimited " + scalarType +"");
            return parseLengthDelimitedValue(data, scalarType, tagPath);
        } // kann sein, dass noch nachgelegt werden muss StartGroup könnte eine Liste zurückliefern; alle anderen Funktionen liefern aber String
        else if (wireType == 3) // deprecated.  Types.WireType.StartGroup.getValue()
        {
            System.out.println("Message 265 wireType = 3 (StartGroup) - depreciated");
        } //            return parse(data, tagPath + pathSeparator);
        else if (wireType == 4) // deprecated.  Types.WireType.EndGroup.getValue()
        {
            System.out.println("Message 268 wireType = 4 (EndGroup) - depreciated");
        } //            return QVariant(); // Caller will need to end the group started previously.
        else if (wireType == 5) { // fixed32, sfixed32, float.   Types.WireType.ThirtyTwoBit.getValue()
            //       System.out.println("Message 273  " + "wireType   " + scalarType);
            switch (scalarType) {
                case Fixed32:
                    return ParserFunktion.parseFixedNumber(data, 32);
                case Sfixed32:
                    return ParserFunktion.parseFixedNumber(data, 32);
                case Float:
                    return ParserFunktion.parseFloatNumber(data, 32);
                default: {
//            System.out.println("Message 279  Return wireType 32 scalarType default");
                    byte[] dummy = new byte[4];
                    try {
                        int temp = data.read(dummy, 0, 4);
                    } catch (Exception e) {
                        System.out.println("Message 276" + "Error " + e.getMessage());
                    }
                    return dummy;
                } // The raw 4-byte sequence.
            }

        }
        System.out.println("Message 289  return nix");
        return null;

    }


    
public Object parseLengthDelimitedValue(InputStream data,
            Types.ScalarType scalarType,
            String tPath) {
        String tagPath = tPath;
        byte[] value = readLengthDelimitedValue(data);
        if (value.length == 0) {//isValid()) {
            //Fehlermeldung ausgeben qWarning() << "Failed to read prefix-delimited value.";
            System.out.println("Message 304    error length = 0");
            return null;
        }
        if (scalarType == Types.ScalarType.Bytes || scalarType == Types.ScalarType.Unknown) {
            //           System.out.println("Message 308" + "scalarType = " + scalarType);
            return value;

        }
        // Assume strings are UTF-8, which works fine for Polar data. If other
        // encodings are used, the called should use ScalerType Bytes (or Unknown)
        // and convert to QString upon return. This is also consistent with the
        // `protoc --decode_raw` output.
        if (scalarType == Types.ScalarType.String) {
            String dummy = "";
            try {
                dummy = new String(value, "UTF-8");
            } catch (Exception e) {
                System.out.println("Message 321" + "Error " + e.getMessage());
                return null;
            }
            //                 System.out.println("Message 324" + "scalarType = String " + dummy);
            return dummy;

        }

        // Parse embedded messages recursively.
        if (scalarType == Types.ScalarType.EmbeddedMessage) {

            // rekursives Bearbeiten EbbededMessage = Unterstruktur lesen
            //           System.out.println("Message 333" + "scalarType = embeddedMessage Länge: " + value.length);
            return parse(value, tagPath + pathSeparator);

        }

        // Parse packed repeated values into a list.
        List<Object> list = new ArrayList<Object>();

        ByteArrayInputStream buffer = new ByteArrayInputStream(value);

        //   System.out.println("Message 344" + "default " + scalarType);
        Object item;
        do {
            item = null;
            item = parseValue(buffer, Types.getWireType(scalarType), scalarType,
                    tagPath + pathSeparator);

            if (item != null) {
                list.add(item);
            }

        } while (item != null);

        //          System.out.println("Message 358  länge Liste " + list.size());
        return list;
    }

    public byte[] readLengthDelimitedValue(InputStream data) {
        // Note: We're assuming length-delimited values use unsigned varints for lengths.
        // I haven't found any Protocl Buffers documentation to support / dispute this.
        int readData = 0;
        Object length = ParserFunktion.parseUnsignedVarint(data);
        if (length == null) {
            System.out.println("Message 369  Error read data ");
            return new byte[0];
        }
        int arraylenght = new Integer(length.toString());
        byte[] dummy = new byte[arraylenght];
        try {
            readData = data.read(dummy, 0, arraylenght);
        } catch (Exception e) {
            System.out.println("Message 375 " + "Error " + e.getMessage());
        }

        if (readData != arraylenght) {
            System.out.println("Message 378  Error zu wenig Daten gelesen");
            return new byte[0];
        } else { //System.out.println("Message 376  Return LenghDelimited  "+length);
            return dummy;
        }

    }


}

