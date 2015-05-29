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
public class SpellsComponent{

    public SpellsComponent(){
        
    }
    
    public SpellsComponent(int... spellsEntities){
        this.spellsEntities = spellsEntities;
    }
    private int[] spellsEntities;

    public int[] getSpellsEntities(){
        return spellsEntities;
    }
}
