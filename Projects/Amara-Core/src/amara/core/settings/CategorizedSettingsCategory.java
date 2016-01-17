/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.settings;

/**
 *
 * @author Carl
 */
public class CategorizedSettingsCategory extends SettingsCategory{

    public CategorizedSettingsCategory(String key, String title, Setting[] settings){
        super(key, title, settings);
    }

    public CategorizedSettingsCategory(String key, String title, SettingsCategory[] subCategories){
        super(key, title, subCategories);
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
