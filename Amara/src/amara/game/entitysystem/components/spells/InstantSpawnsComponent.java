/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

/**
 *
 * @author Carl
 */
public class InstantSpawnsComponent{

    public InstantSpawnsComponent(int[] spawnInformationEntitiesIDs){
        this.spawnInformationEntitiesIDs = spawnInformationEntitiesIDs;
    }
    private int[] spawnInformationEntitiesIDs;

    public int[] getSpawnInformationEntitiesIDs(){
        return spawnInformationEntitiesIDs;
    }
}
