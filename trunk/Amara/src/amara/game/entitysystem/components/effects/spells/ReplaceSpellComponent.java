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
public class ReplaceSpellComponent{

    public ReplaceSpellComponent(){
        
    }
    
    public ReplaceSpellComponent(int spellIndex, String newSpellTemplate){
        this.spellIndex = spellIndex;
        this.newSpellTemplate = newSpellTemplate;
    }
    private int spellIndex;
    private String newSpellTemplate;

    public int getSpellIndex(){
        return spellIndex;
    }

    public String getNewSpellTemplate(){
        return newSpellTemplate;
    }
}
