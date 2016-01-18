/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.DisplayApplication;
import amara.engine.network.*;
import amara.engine.network.messages.entitysystem.Message_EntityChanges;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.synchronizing.*;

/**
 *
 * @author Carl
 */
public class EntitySynchronizeBackend implements MessageBackend{

    public EntitySynchronizeBackend(DisplayApplication displayApplication, EntityWorld entityWorld){
        this.displayApplication = displayApplication;
        this.entityWorld = entityWorld;
    }
    private DisplayApplication displayApplication;
    private EntityWorld entityWorld;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_EntityChanges){
            Message_EntityChanges message = (Message_EntityChanges) receivedMessage;
            final EntityChanges entityChanges = new EntityChanges();
            NetworkUtil.readFromBytes(entityChanges, message.getData());
            displayApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    for(EntityChange entityChange : entityChanges.getChanges()){
                        if(entityChange instanceof RemovedEntityChange){
                            RemovedEntityChange removedEntityChange = (RemovedEntityChange) entityChange;
                            entityWorld.removeEntity(removedEntityChange.getEntity());
                        }
                        else if(entityChange instanceof NewComponentChange){
                            NewComponentChange newComponentChange = (NewComponentChange) entityChange;
                            entityWorld.setComponent(newComponentChange.getEntity(), newComponentChange.getComponent());
                        }
                        else if(entityChange instanceof RemovedComponentChange){
                            RemovedComponentChange removedComponentChange = (RemovedComponentChange) entityChange;
                            entityWorld.removeComponent(removedComponentChange.getEntity(), removedComponentChange.getComponentClass());
                        }
                    }
                }
            });
        }
    }
}
