/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.io.*;
import java.util.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Philipp
 */
class ByteBuffer
{
    private ArrayList<Byte> bytes = new ArrayList<Byte>();
    private int position = 0;
    
    private final static String charSetName = "UTF-8";
    
    public final static byte type_default = 0;
    public final static byte type_string = 1;
    public final static byte type_long = 2;
    public final static byte type_int = 3;
    public final static byte type_short = 4;
    public final static byte type_byte = 5;
    public final static byte type_char = 6;
    public final static byte type_double = 7;
    public final static byte type_float = 8;
    public final static byte type_boolean = 9;
    public final static byte type_array = 10;
    public final static byte type_dictionary = 11;
    public final static byte type_ulong = 12;
    public final static byte type_uint = 13;
    public final static byte type_ushort = 14;
    public final static byte type_ubyte = 15;
    
    private static HashMap<Class, Byte> classToByteMap;
    
    public String toHexText()
    {
        String text = "";
        for (int i = 0; i < bytes.size(); i++)
        {
            text += byteToHex(bytes.get(i)) + " ";
        }
        return text;
    }
    
    public static String byteToHex(byte b)
    {
        return FourBitToHex(b >> 4) + FourBitToHex(b);
    }
    
    public static String FourBitToHex(int b)
    {
        int var = b & 0xf;
        switch(var)
        {
            case 10: return "A";
            case 11: return "B";
            case 12: return "C";
            case 13: return "D";
            case 14: return "E";
            case 15: return "F";
            default: return "" + var;
        }
    }
    
    private static void init()
    {
        classToByteMap = new HashMap<Class, Byte>();
        classToByteMap.put(String.class, type_string);
        classToByteMap.put(Integer.class, type_int);
        classToByteMap.put(Long.class, type_long);
        classToByteMap.put(Short.class, type_short);
        classToByteMap.put(Boolean.class, type_boolean);
        classToByteMap.put(Byte.class, type_byte);
        classToByteMap.put(Character.class, type_char);
        classToByteMap.put(HashMap.class, type_dictionary);
        classToByteMap.put(Object[].class, type_array);
        classToByteMap.put(Double.class, type_double);
        classToByteMap.put(Float.class, type_float);
    }
    
    private static HashMap<Class, Byte> getMap()
    {
        if(classToByteMap == null) init();
        return classToByteMap;
    }
    
    private static byte classToByte(Class clazz)
    {
        Byte type = getMap().get(clazz);
        if(type != null) return type;
        return type_default;
    }
    
    // <editor-fold defaultstate="collapsed" desc="read">
    public HashMap<String, Object> readHashMap() throws UnsupportedEncodingException
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        int length = readShort();
        for (int i = 0; i < length; i++)
        {
            map.put(readString(), readObject());
        }
        return map;
    }
    
    public Object readObject() throws UnsupportedEncodingException
    {
        byte type = readByte();
        switch(type)
        {
            case type_string:
                return readString();
            case type_long:
                return readLong();
            case type_int:
                return readInt();
            case type_short:
                return readShort();
            case type_byte:
                return readByte();
            case type_char:
                return readChar();
            case type_boolean:
                return readBoolean();
            case type_array:
                return readArray();
            case type_dictionary:
                return readHashMap();
            case type_double:
                return readDouble();
            case type_float:
                return readFloat();
        }
        throw new NotImplementedException();
    }
    
    public Object[] readArray() throws UnsupportedEncodingException
    {
        Object[] objs = new Object[readShort()];
        for (int i = 0; i < objs.length; i++) {
            objs[i] = readObject();
        }
        return objs;
    }
    
    public String readString() throws UnsupportedEncodingException
    {
        return new String(readBytes(readShort()), charSetName);
    }
    
    public double readDouble()
    {
        return Double.longBitsToDouble(readLong());
    }
    
    public float readFloat()
    {
        return Float.intBitsToFloat(readInt());
    }
    
    public boolean readBoolean()
    {
        return readByte() != 0;
    }
    
    public char readChar()
    {
        return (char)(readByte() | (readByte() << 8));
    }
    
    public long readLong()
    {
        return readInt() | ((long)readInt() << 32);
    }
    
    public int readInt()
    {
        return readByte() | (readByte() << 8) | (readByte() << 16) | (readByte() << 24);
    }
    
    public short readShort()
    {
        return (short)(readByte() | (readByte() << 8));
    }
    
    public byte[] readBytes(int length)
    {
        byte[] txt = new byte[length];
        for (int i = 0; i < length; i++) {
            txt[i] = readByte();
        }
        return txt;
    }
    
    public byte[] readRemainingBytes()
    {
        return readBytes(availableBytes());
    }
    
    public byte readByte()
    {
        return bytes.get(position++);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="write">
    public void writeObject(Object obj) throws UnsupportedEncodingException
    {
        byte type = classToByte(obj.getClass());
        writeByte(type);
        switch(type)
        {
            case type_string:
                writeString((String)obj);
                return;
            case type_long:
                writeLong((Long)obj);
                return;
            case type_int:
                writeInt((Integer)obj);
                return;
            case type_short:
                writeShort((Short)obj);
                return;
            case type_byte:
                writeByte((Byte)obj);
                return;
            case type_char:
                writeChar((Character)obj);
                return;
            case type_boolean:
                writeBoolean((Boolean)obj);
                return;
            case type_array:
                writeArray((Object[])obj);
                return;
            case type_dictionary:
                writeHashMap((HashMap)obj);
                return;
            case type_double:
                writeDouble((Double)obj);
                return;
            case type_float:
                writeFloat((Float)obj);
                return;
        }
        throw new NotImplementedException();
    }
    
    public void writeHashMap(HashMap<String, Object> map) throws UnsupportedEncodingException
    {
        writeShort((short)map.size());
        for(String key:map.keySet())
        {
            writeString(key);
            writeObject(map.get(key));
        }
    }
    
    public void writeArray(Object[] objs) throws UnsupportedEncodingException
    {
        writeShort((short)objs.length);
        for (int i = 0; i < objs.length; i++)
        {
            writeObject(objs[i]);
        }
    }
    
    public void writeChar(char c)
    {
        writeByte((byte)c);
        writeByte((byte)(c >> 8));
    }
    
    public void writeBoolean(boolean bool)
    {
        if(bool) writeByte((byte)1);
        else writeByte((byte)0);
    }
    
    public void writeDouble(double d)
    {
        writeLong(Double.doubleToLongBits(d));
    }
    
    public void writeFloat(float f)
    {
        writeInt(Float.floatToIntBits(f));
    }
    
    public void writeString(String string) throws UnsupportedEncodingException
    {
        byte[] stringBytes = string.getBytes(charSetName);
        writeShort((short)stringBytes.length);
        writeBytes(stringBytes);
    }
    
    public void writeBytes(byte[] byts)
    {
        for (int i = 0; i < byts.length; i++) {
            writeByte(byts[i]);
        }
    }
    
    public void writeByte(byte b)
    {
        bytes.add(b);
    }
    
    public void writeShort(short shrt)
    {
        writeByte((byte)(shrt >> 8));
        writeByte((byte)shrt);
    }
    
    public static byte[] shortToBytes(short s)
    {
        return new byte[] { (byte)s, (byte)(s >> 8) };
    }
    
    public void writeInt(int i)
    {
        writeByte((byte)i);
        writeByte((byte)(i >> 8));
        writeByte((byte)(i >> 16));
        writeByte((byte)(i >> 24));
    }
    
    public void writeLong(long i)
    {
        writeByte((byte)i);
        writeByte((byte)(i >> 8));
        writeByte((byte)(i >> 16));
        writeByte((byte)(i >> 24));
        writeByte((byte)(i >> 32));
        writeByte((byte)(i >> 40));
        writeByte((byte)(i >> 48));
        writeByte((byte)(i >> 56));
    }
    // </editor-fold>
    
    public byte[] toByteData()
    {
        byte[] byteData = new byte[bytes.size()];
        for (int i = 0; i < byteData.length; i++) {
            byteData[i] = bytes.get(i);
        }
        return byteData;
    }
    
    public int availableBytes()
    {
        return bytes.size() - position;
    }
    
    public void cleanup()
    {
        while(position > 0)
        {
            bytes.remove(--position);
        }
    }
}
