/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells.triggers;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellEffectParentComponent{

    public SpellEffectParentComponent(){
        
    }
    
    public SpellEffectParentComponent(int spellEffectEntity){
        this.spellEffectEntity = spellEffectEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEffectEntity;

    public int getSpellEffectEntity(){
        return spellEffectEntity;
    }
}
