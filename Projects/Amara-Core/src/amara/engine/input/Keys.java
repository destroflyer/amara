/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.input;

import java.lang.reflect.Field;
import java.util.HashMap;
import com.jme3.input.KeyInput;

/**
 *
 * @author Carl
 */
public class Keys{
    
    public static final String UNKNOWN_TITLE = "?";
    private static final String FIELD_NAME_PREFIX = "KEY_";
    private static HashMap<Integer, String> titles = new HashMap<Integer, String>();
    public static int[] KEY_CODES;
    static{
        for(Field field : KeyInput.class.getFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            if((field.getType() == int.class) && fieldName.startsWith(FIELD_NAME_PREFIX)){
                try{
                    int keyCode = field.getInt(null);
                    String title = fieldName.substring(FIELD_NAME_PREFIX.length()).toUpperCase();
                    titles.put(keyCode, title);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        KEY_CODES = new int[titles.keySet().size()];
        int i = 0;
        for(int keyCode : titles.keySet()){
            KEY_CODES[i] = keyCode;
            i++;
        }
    }
    
    public static String getTitle(int keyCode){
        String title = titles.get(keyCode);
        return ((title != null)?title:UNKNOWN_TITLE);
    }
    
    public static void main(String[] args){
        System.out.println("Keys: " + KEY_CODES.length);
        System.out.println(getTitle(KeyInput.KEY_A));
        System.out.println(getTitle(KeyInput.KEY_B));
        System.out.println(getTitle(KeyInput.KEY_C));
        System.out.println(getTitle(KeyInput.KEY_ESCAPE));
        System.out.println(getTitle(KeyInput.KEY_SPACE));
        System.out.println(getTitle(KeyInput.KEY_1));
        System.out.println(getTitle(KeyInput.KEY_2));
        System.out.println(getTitle(KeyInput.KEY_UP));
        System.out.println(getTitle(KeyInput.KEY_RIGHT));
        System.out.println(getTitle(-1));
    }
}
