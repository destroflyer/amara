/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CollisionTriggerEffectComponent{

    public CollisionTriggerEffectComponent(){
        
    }
    
    public CollisionTriggerEffectComponent(int effectEntityID){
        this.effectEntityID = effectEntityID;
    }
    private int effectEntityID;

    public int getEffectEntityID(){
        return effectEntityID;
    }
}
