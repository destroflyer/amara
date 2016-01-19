/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.movements;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class MovementTargetComponent{

    public MovementTargetComponent(){
        
    }

    public MovementTargetComponent(int targetEntity){
        this.targetEntity = targetEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
