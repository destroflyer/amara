/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class LearnSpellCommand extends Command{

    public LearnSpellCommand(){
        
    }

    public LearnSpellCommand(int spellIndex){
        this.spellIndex = spellIndex;
    }
    private int spellIndex;

    public int getSpellIndex(){
        return spellIndex;
    }
}
