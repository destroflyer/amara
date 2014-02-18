/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.engine.network.*;
import amara.engine.network.messages.entitysystem.Message_EntityChanges;
import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.synchronizing.*;

/**
 *
 * @author Carl
 */
public class EntitySynchronizeBackend implements MessageBackend{

    public EntitySynchronizeBackend(EntityWorld entityWorld){
        this.entityWorld = entityWorld;
    }
    private EntityWorld entityWorld;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_EntityChanges){
            Message_EntityChanges message = (Message_EntityChanges) receivedMessage;
            EntityChange[] entityChanges = message.getEntityChanges();
            for(int i=0;i<entityChanges.length;i++){
                EntityChange entityChange = entityChanges[i];
                if(entityChange instanceof RemovedEntityChange){
                    RemovedEntityChange removedEntityChange = (RemovedEntityChange) entityChange;
                    entityWorld.removeEntity(removedEntityChange.getEntityID());
                }
                else if(entityChange instanceof NewComponentChange){
                    NewComponentChange newComponentChange = (NewComponentChange) entityChange;
                    entityWorld.setComponent(newComponentChange.getEntityID(), newComponentChange.getComponent());
                }
                else if(entityChange instanceof RemovedComponentChange){
                    RemovedComponentChange removedComponentChange = (RemovedComponentChange) entityChange;
                    try{
                        Class componentClass = Class.forName(removedComponentChange.getComponentClassName());
                        entityWorld.removeComponent(removedComponentChange.getEntityID(), componentClass);
                    }catch(ClassNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
