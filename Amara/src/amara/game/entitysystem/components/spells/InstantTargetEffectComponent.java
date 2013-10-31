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
public class InstantTargetEffectComponent{

    public InstantTargetEffectComponent(){
        
    }
    
    public InstantTargetEffectComponent(int effectEntityID){
        this.effectEntityID = effectEntityID;
    }
    private int effectEntityID;

    public int getEffectEntityID(){
        return effectEntityID;
    }
}
