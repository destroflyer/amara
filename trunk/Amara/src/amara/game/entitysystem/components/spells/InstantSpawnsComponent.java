/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class InstantSpawnsComponent{

    public InstantSpawnsComponent(){
        
    }
    
    public InstantSpawnsComponent(int[] spawnInformationEntitiesIDs){
        this.spawnInformationEntitiesIDs = spawnInformationEntitiesIDs;
    }
    private int[] spawnInformationEntitiesIDs;

    public int[] getSpawnInformationEntitiesIDs(){
        return spawnInformationEntitiesIDs;
    }
}
