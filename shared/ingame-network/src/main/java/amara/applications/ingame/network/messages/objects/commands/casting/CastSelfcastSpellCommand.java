/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands.casting;

import com.jme3.network.serializing.Serializable;
import amara.applications.ingame.network.messages.objects.commands.Command;

/**
 *
 * @author Carl
 */
@Serializable
public class CastSelfcastSpellCommand extends Command{

    public CastSelfcastSpellCommand(){
        
    }
    
    public CastSelfcastSpellCommand(SpellIndex spellIndex){
        this.spellIndex = spellIndex;
    }
    private SpellIndex spellIndex;

    public SpellIndex getSpellIndex(){
        return spellIndex;
    }
}
