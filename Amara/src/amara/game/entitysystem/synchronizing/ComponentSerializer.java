/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import amara.Util;
import amara.engine.network.*;

/**
 *
 * @author Carl
 */
public class ComponentSerializer{
    
    private static HashMap<Field, FieldSerializer> fieldSerializers = new HashMap<Field, FieldSerializer>();
    
    public static void registerFieldSerializer(Field[] fields, FieldSerializer fieldSerializer){
        for(Field field : fields){
            registerFieldSerializer(field, fieldSerializer);
        }
    }
    
    public static void registerFieldSerializer(Field field, FieldSerializer fieldSerializer){
        fieldSerializers.put(field, fieldSerializer);
    }
    
    public static void writeClassAndObject(BitOutputStream outputStream, Object component){
        writeClass(outputStream, component.getClass());
        writeObject(outputStream, component.getClass(), component);
    }
    
    public static void writeClass(BitOutputStream outputStream, Class componentClass){
        BitstreamClassManager componentManager = BitstreamClassManager.getInstance();
        int componentClassID = componentManager.getID(componentClass);
        int neededBits = Util.getNeededBitsCount(componentManager.getCount());
        outputStream.writeBits(componentClassID, neededBits);
    }
    
    public static void writeObject(BitOutputStream outputStream, Class type, Object value){
        writeObject(outputStream, type, value, null);
    }
    
    private static void writeObject(BitOutputStream outputStream, Class type, Object value, FieldSerializer fieldSerializer){
        if(type.isArray()){
            int length = Array.getLength(value);
            outputStream.writeInteger(length);
            for(int i=0;i<length;i++){
                Object element = Array.get(value, i);
                if(fieldSerializer != null){
                    fieldSerializer.writeField(outputStream, element);
                }
                else{
                    writeObject(outputStream, type.getComponentType(), element);
                }
            }
        }
        else if(fieldSerializer != null){
            fieldSerializer.writeField(outputStream, value);
        }
        else if((type == boolean.class) || (type == Boolean.class)){
            outputStream.writeBoolean((Boolean) value);
        }
        else if((type == int.class) || (type == Integer.class)){
            outputStream.writeInteger((Integer) value);
        }
        else if((type == long.class) || (type == Long.class)){
            outputStream.writeLong((Long) value);
        }
        else if((type == float.class) || (type == Float.class)){
            outputStream.writeFloat((Float) value);
        }
        else if((type == double.class) || (type == Double.class)){
            outputStream.writeDouble((Double) value);
        }
        else if((type == Object.class) || Modifier.isAbstract(type.getModifiers())){
            writeClassAndObject(outputStream, value);
        }
        else if(type.isEnum()){
            try{
                Class enumClass = Class.forName(type.getName());
                Enum enumValue = Enum.valueOf(enumClass, value.toString());
                outputStream.writeEnum(enumValue);
            }catch(ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
        else if(type == String.class){
            outputStream.writeString_UTF8((String) value, 32);
        }
        else{
            for(Field field : getAllSerializedFields(type)){
                try{
                    Object fieldValue = field.get(value);
                    writeObject(outputStream, field.getType(), fieldValue, fieldSerializers.get(field));
                }catch(IllegalArgumentException ex){
                    ex.printStackTrace();
                }catch(IllegalAccessException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public static Object readClassAndObject(BitInputStream inputStream) throws IOException{
        Class componentClass = readClass(inputStream);
        return readObject(inputStream, componentClass);
    }
    
    public static Class readClass(BitInputStream inputStream) throws IOException{
        BitstreamClassManager componentManager = BitstreamClassManager.getInstance();
        int neededBits = Util.getNeededBitsCount(componentManager.getCount());
        int componentClassID = inputStream.readBits(neededBits);
        return componentManager.getClass(componentClassID);
    }
    
    public static Object readObject(BitInputStream inputStream, Class type) throws IOException{
        return readObject(inputStream, type, null);
    }
    
    private static Object readObject(BitInputStream inputStream, Class type, FieldSerializer fieldSerializer) throws IOException{
        if(type.isArray()){
            int length = inputStream.readInteger();
            Object array = Array.newInstance(type.getComponentType(), length);
            for(int i=0;i<length;i++){
                Object element;
                if(fieldSerializer != null){
                    element = fieldSerializer.readField(inputStream);
                }
                else{
                    element = readObject(inputStream, type.getComponentType());
                }
                Array.set(array, i, element);
            }
            return array;
        }
        else if(fieldSerializer != null){
            return fieldSerializer.readField(inputStream);
        }
        else if((type == boolean.class) || (type == Boolean.class)){
            return inputStream.readBoolean();
        }
        else if((type == int.class) || (type == Integer.class)){
            return inputStream.readInteger();
        }
        else if((type == long.class) || (type == Long.class)){
            return inputStream.readLong();
        }
        else if((type == float.class) || (type == Float.class)){
            return inputStream.readFloat();
        }
        else if((type == double.class) || (type == Double.class)){
            return inputStream.readDouble();
        }
        else if((type == Object.class) || Modifier.isAbstract(type.getModifiers())){
            return readClassAndObject(inputStream);
        }
        else if(type.isEnum()){
            try{
                Class enumClass = Class.forName(type.getName());
                return inputStream.readEnum(enumClass);
            }catch(ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
        else if(type == String.class){
            return inputStream.readString_UTF8(32);
        }
        else{
            try{
                Object object = type.newInstance();
                for(Field field : getAllSerializedFields(type)){
                    try{
                        Object fieldValue = readObject(inputStream, field.getType(), fieldSerializers.get(field));
                        field.set(object, fieldValue);
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }catch(IllegalArgumentException ex){
                        ex.printStackTrace();
                    }catch(IllegalAccessException ex){
                        ex.printStackTrace();
                    }
                }
                return object;
            }catch(InstantiationException ex){
                ex.printStackTrace();
            }catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    private static LinkedList<Field> getAllSerializedFields(Class type){
        return addAllSerializedFields(type, new LinkedList<Field>());
    }
    
    private static LinkedList<Field> addAllSerializedFields(Class type, LinkedList<Field> fields){
        if((type.getSuperclass() != null) && (type.getSuperclass() != Object.class)){
            addAllSerializedFields(type.getSuperclass(), fields);
        }
        for(Field field : type.getDeclaredFields()){
            field.setAccessible(true);
            if(!(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers()))){
                fields.add(field);
            }
        }
        return fields;
    }
}
