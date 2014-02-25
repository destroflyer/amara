/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.animations;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class WalkAnimationComponent{

    public WalkAnimationComponent(){
        
    }
    
    public WalkAnimationComponent(int animationEntity){
        this.animationEntity = animationEntity;
    }
    private int animationEntity;

    public int getAnimationEntity(){
        return animationEntity;
    }
}
