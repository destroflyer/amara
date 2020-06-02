/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.network.backends;

import amara.applications.master.network.messages.objects.PlayerProfileData;
import com.jme3.network.Message;
import amara.applications.master.client.appstates.PlayerProfilesAppState;
import amara.applications.master.network.messages.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceivePlayerProfilesDataBackend implements MessageBackend {

    public ReceivePlayerProfilesDataBackend(PlayerProfilesAppState playerProfilesAppState) {
        this.playerProfilesAppState = playerProfilesAppState;
    }
    private PlayerProfilesAppState playerProfilesAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_PlayerProfileData) {
            Message_PlayerProfileData message = (Message_PlayerProfileData) receivedMessage;
            PlayerProfileData playerProfileData = message.getPlayerProfileData();
            playerProfilesAppState.saveProfile(playerProfileData);
        } else if (receivedMessage instanceof Message_PlayerProfileDataNotExistent) {
            Message_PlayerProfileDataNotExistent message = (Message_PlayerProfileDataNotExistent) receivedMessage;
            playerProfilesAppState.onProfileNotExistent(message.getLogin());
        }
    }
}
