/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CurrentActionEffectCastsComponent{

    public CurrentActionEffectCastsComponent(){
        
    }
    
    public CurrentActionEffectCastsComponent(int... effectCastEntities){
        this.effectCastEntities = effectCastEntities;
    }
    private int[] effectCastEntities;

    public int[] getEffectCastEntities(){
        return effectCastEntities;
    }
}
