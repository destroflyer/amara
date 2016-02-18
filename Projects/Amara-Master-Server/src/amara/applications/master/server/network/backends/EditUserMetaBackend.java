/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_EditUserMeta;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class EditUserMetaBackend implements MessageBackend{

    public EditUserMetaBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers){
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_EditUserMeta){
            Message_EditUserMeta message = (Message_EditUserMeta) receivedMessage;
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            databaseAppState.executeQuery("UPDATE users_meta SET value = '" + databaseAppState.escape(message.getValue()) + "' WHERE (userid = " + playerID + ") AND (key = '" + databaseAppState.escape(message.getKey()) + "')");
            databaseAppState.executeQuery("UPDATE users SET last_modification_date = " + System.currentTimeMillis() + " WHERE id = " + playerID);
        }
    }
}
