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
public class KeyType extends SettingType{

    public KeyType(int defaultKeyType){
        this.defaultKeyType = defaultKeyType;
    }
    private int defaultKeyType;
    
    @Override
    public String getDefaultValue(){
        return Settings.toString(defaultKeyType);
    }
}
