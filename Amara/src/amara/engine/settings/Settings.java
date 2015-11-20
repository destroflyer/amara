/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.settings;

import java.util.HashMap;
import amara.Util;
import amara.engine.files.FileManager;

/**
 *
 * @author Carl
 */
public class Settings{
    
    public static final String FILE__PATH = "./settings.ini";
    public static final String FILE__COMMENT_LINE_MARKER = "#";
    public static final String FILE__SEPERATOR_KEY_VALUE = "=";
    public static final String FILE__BOOLEAN_TRUE = "true";
    public static final String FILE__BOOLEAN_FALSE = "false";
    private static HashMap<String, String> values = new HashMap<String, String>();
    static{
        DefaultSettings.setDefaults(new IngameSettings());
        reloadFile();
    }
    
    public static void reloadFile(){
        if(FileManager.existsFile(FILE__PATH)){
            String[] lines = FileManager.getFileLines(FILE__PATH);
            for(int i=0;i<lines.length;i++){
                String[] keyValuePair = getKeyValuePair(lines[i]);
                if(keyValuePair != null){
                    set(keyValuePair[0], keyValuePair[1]);
                }
            }
        }
        else{
            saveFile();
        }
    }
    
    private static String[] getKeyValuePair(String line){
        if(!line.startsWith(FILE__COMMENT_LINE_MARKER)){
            int seperatorIndex = line.indexOf(FILE__SEPERATOR_KEY_VALUE);
            if(seperatorIndex > 0){
                String key = line.substring(0, seperatorIndex);
                String value = line.substring(seperatorIndex + 1);
                return new String[]{key, value};
            }
        }
        return null;
    }
    
    public static void set(String key, int value){
        set(key, toString(value));
    }
    
    public static void set(String key, float value){
        set(key, toString(value));
    }
    
    public static void set(String key, boolean value){
        set(key, toString(value));
    }
    
    public static void set(String key, String value){
        values.put(key, value);
    }
    
    public static int getInteger(String key){
        return toInteger(get(key));
    }
    
    public static float getFloat(String key){
        return toFloat(get(key));
    }
    
    public static boolean getBoolean(String key){
        return toBoolean(get(key));
    }
    
    public static String get(String key){
        return values.get(key);
    }
    
    public static boolean toBoolean(String value){
        return FILE__BOOLEAN_TRUE.equals(value);
    }
    
    public static int toInteger(String value){
        return Integer.parseInt(value);
    }
    
    public static float toFloat(String value){
        return Float.parseFloat(value);
    }
    
    public static String toString(boolean value){
        return (value?FILE__BOOLEAN_TRUE:FILE__BOOLEAN_FALSE);
    }
    
    public static String toString(int value){
        return ("" + value);
    }
    
    public static String toString(float value){
        int valueInt = (int) value;
        if(valueInt == value){
            return ("" + valueInt);
        }
        return ("" + Util.round(value, 4));
    }

    public static void saveFile(){
        String fileContent = "#Settings";
        Object[] keySet = values.keySet().toArray();
        for(int i=0;i<keySet.length;i++){
            String key = (String) keySet[i];
            String value = get(key);
            fileContent += "\n" + key + FILE__SEPERATOR_KEY_VALUE + value;
        }
        FileManager.putFileContent(FILE__PATH, fileContent);
    }
}
