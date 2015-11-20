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
public class KeyTitles{
    
    public static final String UNKNOWN_TITLE = "?";
    private static final String FIELD_NAME_PREFIX = "KEY_";
    private static HashMap<Integer, String> titles = new HashMap<Integer, String>();
    
    public static String getTitle(int keyCode){
        String title = titles.get(keyCode);
        if(title == null){
            title = UNKNOWN_TITLE;
            for(Field field : KeyInput.class.getFields()){
                field.setAccessible(true);
                String fieldName = field.getName();
                try{
                    if((field.getType() == int.class) && fieldName.startsWith(FIELD_NAME_PREFIX) && (field.getInt(null) == keyCode)){
                        title = fieldName.substring(FIELD_NAME_PREFIX.length()).toUpperCase();
                        break;
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            titles.put(keyCode, title);
        }
        return title;
    }
    
    public static void main(String[] args){
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
