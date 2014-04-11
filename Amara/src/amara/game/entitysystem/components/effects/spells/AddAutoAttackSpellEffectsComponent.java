/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AddAutoAttackSpellEffectsComponent{

    public AddAutoAttackSpellEffectsComponent(){
        
    }
    
    public AddAutoAttackSpellEffectsComponent(int... spellEffectEntities){
        this.spellEffectEntities = spellEffectEntities;
    }
    private int[] spellEffectEntities;

    public int[] getSpellEffectEntities(){
        return spellEffectEntities;
    }
}
