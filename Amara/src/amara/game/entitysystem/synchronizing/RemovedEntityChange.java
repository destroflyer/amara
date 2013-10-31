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
public class RemovedEntityChange extends EntityChange{

    public RemovedEntityChange(){
        
    }
    
    public RemovedEntityChange(int entityID){
        super(entityID);
    }
}
