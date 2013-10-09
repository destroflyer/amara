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
public class CastSelfcastSpellCommand extends Command{

    public CastSelfcastSpellCommand(int entity, int spellIndex){
        this.entity = entity;
        this.spellIndex = spellIndex;
    }
    private int entity;
    private int spellIndex;

    public int getEntity(){
        return entity;
    }

    public int getSpellIndex(){
        return spellIndex;
    }
}
