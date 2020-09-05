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
public class CastSingleTargetSpellCommand extends Command{

    public CastSingleTargetSpellCommand(){
        
    }
    
    public CastSingleTargetSpellCommand(SpellIndex spellIndex, int targetEntityID){
        this.spellIndex = spellIndex;
        this.targetEntityID = targetEntityID;
    }
    private SpellIndex spellIndex;
    private int targetEntityID;

    public SpellIndex getSpellIndex(){
        return spellIndex;
    }

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
