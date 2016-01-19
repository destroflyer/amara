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
public class TriggerSpellEffectsComponent{

    public TriggerSpellEffectsComponent(){
        
    }
    
    public TriggerSpellEffectsComponent(int spellEntity){
        this.spellEntity = spellEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEntity;

    public int getSpellEntity(){
        return spellEntity;
    }
}
