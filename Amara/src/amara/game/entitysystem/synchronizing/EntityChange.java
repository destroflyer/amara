/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class EntityChange{

    public EntityChange(){
        
    }

    protected EntityChange(int entityID){
        this.entityID = entityID;
    }
    private int entityID;

    public int getEntityID(){
        return entityID;
    }
}
