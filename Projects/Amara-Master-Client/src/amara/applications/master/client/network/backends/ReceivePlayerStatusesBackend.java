/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.appstates.PlayerStatusesAppState;
import amara.applications.master.network.messages.Message_PlayerStatus;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceivePlayerStatusesBackend implements MessageBackend{

    public ReceivePlayerStatusesBackend(PlayerStatusesAppState playerStatusesAppState){
        this.playerStatusesAppState = playerStatusesAppState;
    }
    private PlayerStatusesAppState playerStatusesAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_PlayerStatus){
            Message_PlayerStatus message = (Message_PlayerStatus) receivedMessage;
            playerStatusesAppState.setStatus(message.getPlayerID(), message.getPlayerStatus());
        }
    }
}
