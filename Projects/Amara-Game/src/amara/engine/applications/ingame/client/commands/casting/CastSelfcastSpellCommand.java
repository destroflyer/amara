/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.commands.casting;

import com.jme3.network.serializing.Serializable;
import amara.engine.applications.ingame.client.commands.Command;

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
