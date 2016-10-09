/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class PopupComponent{

    public PopupComponent(){
        
    }

    public PopupComponent(int popupEntity){
        this.popupEntity = popupEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int popupEntity;

    public int getPopupEntity(){
        return popupEntity;
    }
}
