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
            int playerId = connectedPlayers.getPlayer(messageResponse.getClientId()).getID();
            boolean isAllowed;
            if (message.getSkinId() == 0) {
                isAllowed = true;
            } else {
                isAllowed = (databaseAppState.getQueryResult("SELECT skin_id FROM users_characters_skins WHERE (user_id = " + playerId + ") AND (skin_id = " + message.getSkinId() + ") LIMIT 1").nextInteger_Close() != null);
            }
            if (isAllowed) {
                databaseAppState.executeQuery("DELETE FROM users_characters_active_skins WHERE (user_id = " + playerId + ") AND (character_id = " + message.getCharacterId() + ") LIMIT 1");
                if (message.getSkinId() != 0) {
                    databaseAppState.executeQuery("INSERT INTO users_characters_active_skins (user_id, character_id, skin_id) VALUES (" + playerId + ", '" + message.getCharacterId() + "', '" + message.getSkinId() + "')");
                }
            }
        }
    }
}
