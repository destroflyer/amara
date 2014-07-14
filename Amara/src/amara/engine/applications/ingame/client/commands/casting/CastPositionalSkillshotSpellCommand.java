/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.commands.casting;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.ingame.client.commands.Command;

/**
 *
 * @author Carl
 */
@Serializable
public class CastPositionalSkillshotSpellCommand extends Command{

    public CastPositionalSkillshotSpellCommand(){
        
    }
    
    public CastPositionalSkillshotSpellCommand(SpellIndex spellIndex, Vector2f position){
        this.spellIndex = spellIndex;
        this.position = position;
    }
    private SpellIndex spellIndex;
    private Vector2f position;

    public SpellIndex getSpellIndex(){
        return spellIndex;
    }

    public Vector2f getPosition(){
        return position;
    }
}
