/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.network.backends;

import java.util.Iterator;
import java.util.LinkedList;
import com.jme3.network.Message;
import amara.applications.ingame.network.messages.Message_ClientInitialized;
import amara.game.entitysystem.systems.network.SendEntityChangesSystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.network.*;

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
            Message[] entityChangeMessages = SendEntityChangesSystem.getEntityChangesMessages(entityChanges);
            for(Message entityChangeMessage : entityChangeMessages){
                messageResponse.addAnswerMessage(entityChangeMessage);
            }
        }
    }
}
