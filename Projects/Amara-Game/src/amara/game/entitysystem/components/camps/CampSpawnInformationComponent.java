/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.camps;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] spawnInformationEntites;

    public int[] getSpawnInformationEntites(){
        return spawnInformationEntites;
    }
}
