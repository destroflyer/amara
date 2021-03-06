/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class RemoveSpellEffectsComponent{

    public RemoveSpellEffectsComponent(){
        
    }
    
    public RemoveSpellEffectsComponent(int... spellEffectEntities){
        this.spellEffectEntities = spellEffectEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] spellEffectEntities;

    public int[] getSpellEffectEntities(){
        return spellEffectEntities;
    }
}
