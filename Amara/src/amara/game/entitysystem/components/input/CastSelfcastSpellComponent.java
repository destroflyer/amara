/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.input;

/**
 *
 * @author Carl
 */
public class CastSelfcastSpellComponent{

    public CastSelfcastSpellComponent(int spellEntityID){
        this.spellEntityID = spellEntityID;
    }
    private int spellEntityID;

    public int getSpellEntityID(){
        return spellEntityID;
    }
}
