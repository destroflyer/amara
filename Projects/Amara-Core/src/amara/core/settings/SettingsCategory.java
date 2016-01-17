/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.settings;

/**
 *
 * @author Carl
 */
public class SettingsCategory{

    public SettingsCategory(String key, String title, SettingsCategory[] subCategories){
        this.key = key;
        this.title = title;
        this.subCategories = subCategories;
        for(SettingsCategory subCategory : subCategories){
            if(subCategory instanceof CategorizedSettingsCategory){
                CategorizedSettingsCategory categorizedSettingsCategory = (CategorizedSettingsCategory) subCategory;
                categorizedSettingsCategory.setCategory(this);
            }
        }
    }

    public SettingsCategory(String key, String title, Setting[] settings){
        this.key = key;
        this.title = title;
        this.settings = settings;
        for(Setting setting : settings){
            if(setting instanceof CategorizedSetting){
                CategorizedSetting categorizedSetting = (CategorizedSetting) setting;
                categorizedSetting.setCategory(this);
            }
        }
    }
    private String key;
    private String title;
    private SettingsCategory[] subCategories;
    private Setting[] settings;

    public String getKey(){
        return key;
    }

    public static String getKey(SettingsCategory category, String key){
        String resultKey = "";
        if((category != null) && (!category.getKey().equals("null"))){
            resultKey += category.getKey() + "_";
        }
        resultKey += key;
        return resultKey;
    }

    public String getTitle(){
        return title;
    }

    public SettingsCategory[] getSubCategories(){
        return subCategories;
    }

    public Setting[] getSettings(){
        return settings;
    }
}
