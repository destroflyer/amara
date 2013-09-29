/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands.casting;

import amara.engine.client.commands.Command;

/**
 *
 * @author Carl
 */
public class CastSingleTargetSpellCommand extends Command{

    public CastSingleTargetSpellCommand(int entity, int spellIndex, int targetEntityID){
        this.entity = entity;
        this.spellIndex = spellIndex;
        this.targetEntityID = targetEntityID;
    }
    private int entity;
    private int spellIndex;
    private int targetEntityID;

    public int getEntity(){
        return entity;
    }

    public int getSpellIndex(){
        return spellIndex;
    }

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
