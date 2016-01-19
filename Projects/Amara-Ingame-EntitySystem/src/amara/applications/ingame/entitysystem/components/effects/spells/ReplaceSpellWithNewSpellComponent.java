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
public class ReplaceSpellWithNewSpellComponent{

    public ReplaceSpellWithNewSpellComponent(){
        
    }
    
    public ReplaceSpellWithNewSpellComponent(int spellIndex, String newSpellTemplate){
        this.spellIndex = spellIndex;
        this.newSpellTemplate = newSpellTemplate;
    }
    private int spellIndex;
    @ComponentField(type=ComponentField.Type.TEMPLATE)
    private String newSpellTemplate;

    public int getSpellIndex(){
        return spellIndex;
    }

    public String getNewSpellTemplate(){
        return newSpellTemplate;
    }
}
