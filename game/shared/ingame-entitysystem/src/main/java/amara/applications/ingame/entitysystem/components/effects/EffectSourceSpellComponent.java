/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class EffectSourceSpellComponent {

    public EffectSourceSpellComponent(){

    }

    public EffectSourceSpellComponent(int spellEntity){
        this.spellEntity = spellEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEntity;

    public int getSpellEntity(){
        return spellEntity;
    }
}
