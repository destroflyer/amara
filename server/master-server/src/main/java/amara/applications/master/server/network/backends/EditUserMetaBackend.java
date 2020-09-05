/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_EditUserMeta;
import amara.applications.master.server.appstates.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class EditUserMetaBackend implements MessageBackend {

    public EditUserMetaBackend(DatabaseAppState databaseAppState, PlayersAppState playersAppState) {
        this.databaseAppState = databaseAppState;
        this.playersAppState = playersAppState;
    }
    private DatabaseAppState databaseAppState;
    private PlayersAppState playersAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_EditUserMeta) {
            Message_EditUserMeta message = (Message_EditUserMeta) receivedMessage;
            int playerID = playersAppState.getConnectedPlayers().getPlayer(messageResponse.getClientId()).getID();
            boolean isDefaultValue = (message.getValue().equals(playersAppState.getUserDefaultMeta().get(message.getName())));
            String oldValue = databaseAppState.getQueryResult("SELECT value FROM users_meta WHERE (user_id = " + playerID + ") AND (name = '" + databaseAppState.escape(message.getName()) + "')").nextString_Close();
            String whereClause = ("(user_id = " + playerID + ") AND (name = '" + databaseAppState.escape(message.getName()) + "')");
            if (isDefaultValue) {
                if (oldValue != null) {
                    databaseAppState.executeQuery("DELETE FROM users_meta WHERE " + whereClause);
                }
            } else {
                if (oldValue == null) {
                    databaseAppState.executeQuery("INSERT INTO users_meta (user_id, name, value) VALUES (" + playerID + ", '" + databaseAppState.escape(message.getName()) + "', '" + databaseAppState.escape(message.getValue()) + "')");
                } else {
                    databaseAppState.executeQuery("UPDATE users_meta SET value = '" + databaseAppState.escape(message.getValue()) + "' WHERE " + whereClause);
                }
            }
        }
    }
}
