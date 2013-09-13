/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

/**
 *
 * @author Carl
 */
public class SpellsComponent{

    public SpellsComponent(int[] spellsEntitiesIDs){
        this.spellsEntitiesIDs = spellsEntitiesIDs;
    }
    private int[] spellsEntitiesIDs;

    public int[] getSpellsEntitiesIDs(){
        return spellsEntitiesIDs;
    }
}
