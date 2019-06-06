/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import java.util.LinkedList;
import com.jme3.network.Message;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.applications.ingame.network.messages.*;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendInitialEntityWorldBackend implements MessageBackend {

    public SendInitialEntityWorldBackend(EntityWorld entityWorld) {
        this.entityWorld = entityWorld;
    }
    private EntityWorld entityWorld;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_ClientInitialized) {
            LinkedList<EntityChange> entityChanges = new LinkedList<>();
            for (Integer entity : entityWorld.getEntitiesWithAll()) {
                for (Object component : entityWorld.getComponents(entity)) {
                    entityChanges.add(new NewComponentChange(entity, component));
                }
            }
            Message[] entityChangeMessages = SendEntityChangesSystem.getEntityChangesMessages(entityChanges);
            for (Message entityChangeMessage : entityChangeMessages) {
                messageResponse.addAnswerMessage(entityChangeMessage);
            }
            messageResponse.addAnswerMessage(new Message_InitialEntityWorldSent());
        }
    }
}
