/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands.casting;

import com.jme3.network.serializing.Serializable;

@Serializable
public class SpellIndex {

    public SpellIndex() {
        
    }

    public SpellIndex(SpellSet spellSet, int index) {
        this.spellSet = spellSet;
        this.index = index;
    }
    public enum SpellSet{
        SPELLS,
        ITEMS,
        MAP
    }
    private SpellSet spellSet;
    private int index;

    public SpellSet getSpellSet() {
        return spellSet;
    }

    public int getIndex() {
        return index;
    }
}
