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
public class CastLinearSkillshotSpellCommand extends Command{

    public CastLinearSkillshotSpellCommand(int spellIndex, Vector2f direction){
        this.spellIndex = spellIndex;
        this.direction = direction;
    }
    private int spellIndex;
    private Vector2f direction;

    public int getSpellIndex(){
        return spellIndex;
    }

    public Vector2f getDirection(){
        return direction;
    }
}
