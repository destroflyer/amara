/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.settings;

/**
 *
 * @author Carl
 */
public class Setting{

    public Setting(String key, String title, SettingType type){
        this.key = key;
        this.title = title;
        this.type = type;
    }
    private String key;
    private String title;
    private SettingType type;

    public String getKey(){
        return key;
    }

    public String getTitle(){
        return title;
    }

    public SettingType getType(){
        return type;
    }
}
