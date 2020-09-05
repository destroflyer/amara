/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.settings.types;

import amara.core.settings.*;

/**
 *
 * @author Carl
 */
public class BooleanType extends SettingType{

    public BooleanType(boolean defaultValue){
        this.defaultValue = defaultValue;
    }
    private boolean defaultValue;

    @Override
    public String getDefaultValue(){
        return Settings.toString(defaultValue);
    }
}
