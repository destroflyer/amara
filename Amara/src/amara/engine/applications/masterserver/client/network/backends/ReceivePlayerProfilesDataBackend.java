/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.client.appstates.PlayerProfilesAppState;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;

/**
 *
 * @author Carl
 */
public class ReceivePlayerProfilesDataBackend implements MessageBackend{

    public ReceivePlayerProfilesDataBackend(PlayerProfilesAppState playerProfilesAppState){
        this.playerProfilesAppState = playerProfilesAppState;
    }
    private PlayerProfilesAppState playerProfilesAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_PlayerProfileData){
            Message_PlayerProfileData message = (Message_PlayerProfileData) receivedMessage;
            playerProfilesAppState.onUpdateFinished(message.getLogin());
            if(message.getPlayerProfileData() != null){
                playerProfilesAppState.setProfile(message.getLogin(), message.getPlayerProfileData());
            }
        }
        else if(receivedMessage instanceof Message_PlayerProfileDataNotExistant){
            Message_PlayerProfileDataNotExistant message = (Message_PlayerProfileDataNotExistant) receivedMessage;
            playerProfilesAppState.onUpdateFinished(message.getLogin());
            playerProfilesAppState.setProfileNotExistant(message.getLogin());
        }
    }
}
