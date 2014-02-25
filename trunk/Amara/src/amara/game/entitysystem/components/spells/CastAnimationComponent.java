/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CastAnimationComponent{

    public CastAnimationComponent(){
        
    }
    
    public CastAnimationComponent(int animationEntity){
        this.animationEntity = animationEntity;
    }
    private int animationEntity;

    public int getAnimationEntity(){
        return animationEntity;
    }
}
