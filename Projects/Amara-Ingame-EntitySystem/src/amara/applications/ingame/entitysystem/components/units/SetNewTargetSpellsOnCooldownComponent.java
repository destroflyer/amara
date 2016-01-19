/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SetNewTargetSpellsOnCooldownComponent{

    public SetNewTargetSpellsOnCooldownComponent(){
        
    }
    
    public SetNewTargetSpellsOnCooldownComponent(int[] spellIndices, float[] cooldowns){
        this.spellIndices = spellIndices;
        this.cooldowns = cooldowns;
    }
    private int[] spellIndices;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float[] cooldowns;

    public int[] getSpellIndices(){
        return spellIndices;
    }

    public float[] getCooldowns(){
        return cooldowns;
    }
}
