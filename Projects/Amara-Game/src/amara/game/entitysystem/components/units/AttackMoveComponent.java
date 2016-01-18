/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AttackMoveComponent{
    
    public AttackMoveComponent(){
        
    }

    public AttackMoveComponent(int targetEntity){
        this.targetEntity = targetEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
