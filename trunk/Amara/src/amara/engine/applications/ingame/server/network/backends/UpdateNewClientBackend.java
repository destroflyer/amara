/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.network.backends;

import java.util.Iterator;
import java.util.LinkedList;
import com.jme3.network.Message;
import amara.engine.network.*;
import amara.engine.network.messages.Message_ClientInitialized;
import amara.engine.network.messages.entitysystem.Message_EntityChanges;
import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.synchronizing.*;

/**
 *
 * @author Carl
 */
public class UpdateNewClientBackend implements MessageBackend{

    public UpdateNewClientBackend(EntityWorld entityWorld){
        this.entityWorld = entityWorld;
    }
    private EntityWorld entityWorld;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_ClientInitialized){
            Message_ClientInitialized message = (Message_ClientInitialized) receivedMessage;
            LinkedList<EntityChange> entityChanges = new LinkedList<EntityChange>();
            Iterator<Integer> entitiesIterator = entityWorld.getEntitiesWithAll().iterator();
            while(entitiesIterator.hasNext()){
                int entity = entitiesIterator.next();
                Iterator<Object> componentsIterator = entityWorld.getComponents(entity).iterator();
                while(componentsIterator.hasNext()){
                    Object component = componentsIterator.next();
                    entityChanges.add(new NewComponentChange(entity, component));
                }
            }
            messageResponse.addAnswerMessage(new Message_EntityChanges(entityChanges.toArray(new EntityChange[0])));
        }
    }
}
