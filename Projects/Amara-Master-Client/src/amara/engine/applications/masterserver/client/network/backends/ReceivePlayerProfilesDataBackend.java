/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.engine.applications.masterserver.client.appstates.PlayerProfilesAppState;
import amara.engine.network.*;

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
            if(message.getPlayerProfileData() != null){
                playerProfilesAppState.setProfile(message.getPlayerProfileData());
            }
            playerProfilesAppState.onUpdateFinished(message.getPlayerID());
            playerProfilesAppState.onUpdateFinished(message.getLogin());
        }
        else if(receivedMessage instanceof Message_PlayerProfileDataNotExistant){
            Message_PlayerProfileDataNotExistant message = (Message_PlayerProfileDataNotExistant) receivedMessage;
            if(message.getPlayerID() != 0){
                playerProfilesAppState.setProfileNotExistant(message.getPlayerID());
                playerProfilesAppState.onUpdateFinished(message.getPlayerID());
            }
            else{
                playerProfilesAppState.setProfileNotExistant(message.getLogin());
                playerProfilesAppState.onUpdateFinished(message.getLogin());
            }
        }
    }
}
