/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.spawns;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpawnComponent{

    public SpawnComponent(){
        
    }
    
    public SpawnComponent(int... spawnInformationEntities){
        this.spawnInformationEntites = spawnInformationEntities;
    }
    private int[] spawnInformationEntites;

    public int[] getSpawnInformationEntites(){
        return spawnInformationEntites;
    }
}
