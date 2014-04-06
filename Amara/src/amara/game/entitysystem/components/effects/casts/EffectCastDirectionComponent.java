/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.casts;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class EffectCastDirectionComponent{

    public EffectCastDirectionComponent(){
        
    }
    
    public EffectCastDirectionComponent(Vector2f direction){
        this.direction = direction;
    }
    private Vector2f direction;

    public Vector2f getDirection(){
        return direction;
    }
}
