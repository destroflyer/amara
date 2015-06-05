/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.camps;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CampSpawnInformationComponent{

    public CampSpawnInformationComponent(){
        
    }

    public CampSpawnInformationComponent(int... spawnInformationEntities){
        this.spawnInformationEntites = spawnInformationEntities;
    }
    private int[] spawnInformationEntites;

    public int[] getSpawnInformationEntites(){
        return spawnInformationEntites;
    }
}
