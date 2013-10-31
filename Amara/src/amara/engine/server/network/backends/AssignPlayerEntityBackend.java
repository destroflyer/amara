/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.server.network.backends;

import com.jme3.network.Message;
import amara.engine.network.*;
import amara.engine.network.messages.Message_ClientInitialized;
import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.players.ClientComponent;

/**
 *
 * @author Carl
 */
public class AssignPlayerEntityBackend implements MessageBackend{

    public AssignPlayerEntityBackend(EntityWorld entityWorld){
        this.entityWorld = entityWorld;
    }
    private EntityWorld entityWorld;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_ClientInitialized){
            Message_ClientInitialized message = (Message_ClientInitialized) receivedMessage;
            //TODO: Temporarly just assign the client #0 the entity #0 etc. (for testing purposes)
            int playerEntityID = messageResponse.getClientID();
            entityWorld.setComponent(playerEntityID, new ClientComponent(messageResponse.getClientID()));
        }
    }
}
