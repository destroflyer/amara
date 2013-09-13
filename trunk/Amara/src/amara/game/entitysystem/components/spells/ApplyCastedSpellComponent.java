/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

/**
 *
 * @author Carl
 */
public class ApplyCastedSpellComponent{

    public ApplyCastedSpellComponent(int casterEntityID, int castedSpellEntityID){
        this.casterEntityID = casterEntityID;
        this.castedSpellEntityID = castedSpellEntityID;
    }
    private int casterEntityID;
    private int castedSpellEntityID;

    public int getCasterEntityID(){
        return casterEntityID;
    }

    public int getCastedSpellEntityID(){
        return castedSpellEntityID;
    }
}
