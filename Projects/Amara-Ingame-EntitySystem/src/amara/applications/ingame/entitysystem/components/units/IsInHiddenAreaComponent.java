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
public class IsInHiddenAreaComponent{
    
    public IsInHiddenAreaComponent(){
        
    }

    public IsInHiddenAreaComponent(int hiddenAreaEntity){
        this.hiddenAreaEntity = hiddenAreaEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int hiddenAreaEntity;

    public int getHiddenAreaEntity(){
        return hiddenAreaEntity;
    }
}
