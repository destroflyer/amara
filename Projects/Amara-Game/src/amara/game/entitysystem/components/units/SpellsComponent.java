/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellsComponent{

    public SpellsComponent(){
        
    }
    
    public SpellsComponent(int... spellsEntities){
        this.spellsEntities = spellsEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] spellsEntities;

    public int[] getSpellsEntities(){
        return spellsEntities;
    }
}
