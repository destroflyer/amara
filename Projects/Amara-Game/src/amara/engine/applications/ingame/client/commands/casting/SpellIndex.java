/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.commands.casting;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellIndex{

    public SpellIndex(){
        
    }

    
    public SpellIndex(SpellSet spellSet, int index){
        this.spellSet = spellSet;
        this.index = index;
    }
    public enum SpellSet{
        SPELLS,
        ITEMS
    }
    private SpellSet spellSet;
    private int index;

    public SpellSet getSpellSet(){
        return spellSet;
    }

    public int getIndex(){
        return index;
    }
}
