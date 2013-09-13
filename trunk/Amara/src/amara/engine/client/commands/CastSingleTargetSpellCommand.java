/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands;

/**
 *
 * @author Carl
 */
public class CastSingleTargetSpellCommand extends Command{

    public CastSingleTargetSpellCommand(int spellIndex, int targetEntityID){
        this.spellIndex = spellIndex;
        this.targetEntityID = targetEntityID;
    }
    private int spellIndex;
    private int targetEntityID;

    public int getSpellIndex(){
        return spellIndex;
    }

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
