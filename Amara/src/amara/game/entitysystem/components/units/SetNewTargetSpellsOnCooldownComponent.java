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
public class SetNewTargetSpellsOnCooldownComponent{

    public SetNewTargetSpellsOnCooldownComponent(){
        
    }
    
    public SetNewTargetSpellsOnCooldownComponent(int[] spellIndices, int[] cooldowns){
        this.spellIndices = spellIndices;
        this.cooldowns = cooldowns;
    }
    private int[] spellIndices;
    private int[] cooldowns;

    public int[] getSpellIndices(){
        return spellIndices;
    }

    public int[] getCooldowns(){
        return cooldowns;
    }
}
