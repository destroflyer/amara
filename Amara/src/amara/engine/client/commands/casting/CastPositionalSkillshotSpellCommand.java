/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands.casting;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import amara.engine.client.commands.Command;

/**
 *
 * @author Carl
 */
@Serializable
public class CastPositionalSkillshotSpellCommand extends Command{

    public CastPositionalSkillshotSpellCommand(){
        
    }
    
    public CastPositionalSkillshotSpellCommand(int spellIndex, Vector2f position){
        this.spellIndex = spellIndex;
        this.position = position;
    }
    private int spellIndex;
    private Vector2f position;

    public int getSpellIndex(){
        return spellIndex;
    }

    public Vector2f getPosition(){
        return position;
    }
}
