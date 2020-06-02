/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_EditActiveCharacterSkin;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class EditActiveCharacterSkinsBackend implements MessageBackend {

    public EditActiveCharacterSkinsBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers) {
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_EditActiveCharacterSkin) {
            Message_EditActiveCharacterSkin message = (Message_EditActiveCharacterSkin) receivedMessage;
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            boolean isAllowed;
            if (message.getSkinID() == 0) {
                isAllowed = true;
            } else {
                int id = databaseAppState.getQueryResult("SELECT id FROM users_characters_skins WHERE (user_id = " + playerID + ") AND (skin_id = " + message.getSkinID() + ") LIMIT 1").nextInteger_Close();
                isAllowed = (id != 0);
            }
            if (isAllowed) {
                databaseAppState.executeQuery("UPDATE users_characters SET skin_id = " + message.getSkinID() + " WHERE (user_id = " + playerID + ") AND (character_id = " + message.getCharacterID() + ")");
            }
        }
    }
}
