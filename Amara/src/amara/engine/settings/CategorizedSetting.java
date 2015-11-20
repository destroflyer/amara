/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.settings;

/**
 *
 * @author Carl
 */
public class CategorizedSetting extends Setting{

    public CategorizedSetting(String key, String title, SettingType type){
        super(key, title, type);
    }
    private SettingsCategory category;

    @Override
    public String getKey(){
        return SettingsCategory.getKey(category, super.getKey());
    }

    public void setCategory(SettingsCategory category){
        this.category = category;
    }

    public SettingsCategory getCategory(){
        return category;
    }
}
