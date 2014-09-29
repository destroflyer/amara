/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;

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
    private int animationEntity;

    public int getAnimationEntity(){
        return animationEntity;
    }
}