/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.settings.types;

import amara.core.input.events.MouseClickEvent;
import amara.core.settings.*;

/**
 *
 * @author Carl
 */
public class MouseButtonType extends SettingType{

    public MouseButtonType(MouseClickEvent.Button defaultButton){
        this.defaultButton = defaultButton;
    }
    private MouseClickEvent.Button defaultButton;
    
    @Override
    public String getDefaultValue(){
        return Settings.toString(defaultButton.ordinal());
    }
}
