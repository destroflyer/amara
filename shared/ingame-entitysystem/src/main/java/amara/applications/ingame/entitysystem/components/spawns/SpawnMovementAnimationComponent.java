/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SpawnMovementAnimationComponent{

    public SpawnMovementAnimationComponent(){
        
    }
    
    public SpawnMovementAnimationComponent(int animationEntity){
        this.animationEntity = animationEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int animationEntity;

    public int getAnimationEntity(){
        return animationEntity;
    }
}