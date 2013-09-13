/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.input;

/**
 *
 * @author Carl
 */
public class CastSingleTargetSpellComponent{

    public CastSingleTargetSpellComponent(int spellEntityID, int targetEntityID){
        this.spellEntityID = spellEntityID;
        this.targetEntityID = targetEntityID;
    }
    private int spellEntityID;
    private int targetEntityID;

    public int getSpellEntityID(){
        return spellEntityID;
    }

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
