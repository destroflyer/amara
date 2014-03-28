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
public class ReplaceSpellWithExistingSpellComponent{

    public ReplaceSpellWithExistingSpellComponent(){
        
    }
    
    public ReplaceSpellWithExistingSpellComponent(int spellIndex, int spellEntity){
        this.spellIndex = spellIndex;
        this.spellEntity = spellEntity;
    }
    private int spellIndex;
    private int spellEntity;

    public int getSpellIndex(){
        return spellIndex;
    }

    public int getSpellEntity(){
        return spellEntity;
    }
}
