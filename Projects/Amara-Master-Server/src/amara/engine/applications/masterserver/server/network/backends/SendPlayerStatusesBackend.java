/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.PlayerStatus;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendPlayerStatusesBackend implements MessageBackend{

    public SendPlayerStatusesBackend(PlayersAppState playersAppState){
        this.playersAppState = playersAppState;
    }
    private PlayersAppState playersAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetPlayerStatus){
            Message_GetPlayerStatus message = (Message_GetPlayerStatus) receivedMessage;
            PlayerStatus playerStatus = playersAppState.getPlayerStatus(message.getPlayerID());
            messageResponse.addAnswerMessage(new Message_PlayerStatus(message.getPlayerID(), playerStatus));
        }
    }
}
