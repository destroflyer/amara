/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.appstates.SynchronizeEntityWorldAppState;
import amara.applications.ingame.network.messages.*;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class EntitySynchronizeBackend implements MessageBackend {

    public EntitySynchronizeBackend(SynchronizeEntityWorldAppState synchronizeEntityWorldAppState, PlayerAppState playerAppState) {
        this.synchronizeEntityWorldAppState = synchronizeEntityWorldAppState;
        this.playerAppState = playerAppState;
    }
    private SynchronizeEntityWorldAppState synchronizeEntityWorldAppState;
    private PlayerAppState playerAppState;
    private EntityChanges entityChanges = new EntityChanges();

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_EntityChanges) {
            Message_EntityChanges message = (Message_EntityChanges) receivedMessage;
            NetworkUtil.readFromBytes(entityChanges, message.getData());
            synchronizeEntityWorldAppState.enqueueEntityChanges(entityChanges);
        } else if (receivedMessage instanceof Message_InitialEntityWorldSent) {
            Message_InitialEntityWorldSent message = (Message_InitialEntityWorldSent) receivedMessage;
            playerAppState.setPlayerEntity(message.getPlayerEntity());
            synchronizeEntityWorldAppState.onInitialEntityWorldReceived();
        }
    }
}
