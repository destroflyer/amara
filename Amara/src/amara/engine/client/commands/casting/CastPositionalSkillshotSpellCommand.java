/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands.casting;

import com.jme3.math.Vector2f;
import amara.engine.client.commands.Command;

/**
 *
 * @author Carl
 */
public class CastPositionalSkillshotSpellCommand extends Command{

    public CastPositionalSkillshotSpellCommand(int entity, int spellIndex, Vector2f position){
        this.entity = entity;
        this.spellIndex = spellIndex;
        this.position = position;
    }
    private int entity;
    private int spellIndex;
    private Vector2f position;

    public int getEntity(){
        return entity;
    }

    public int getSpellIndex(){
        return spellIndex;
    }

    public Vector2f getPosition(){
        return position;
    }
}
