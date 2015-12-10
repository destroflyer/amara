/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.spells;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AddSpellsSpellEffectsComponent{

    public AddSpellsSpellEffectsComponent(){
        
    }
    
    public AddSpellsSpellEffectsComponent(int[] spellEffectEntities, boolean setSourcesToSpells){
        this.spellEffectEntities = spellEffectEntities;
        this.setSourcesToSpells = setSourcesToSpells;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] spellEffectEntities;
    private boolean setSourcesToSpells;

    public int[] getSpellEffectEntities(){
        return spellEffectEntities;
    }

    public boolean isSetSourcesToSpells(){
        return setSourcesToSpells;
    }
}
