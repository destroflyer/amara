/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.DatabaseAppState;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;
import amara.game.players.ConnectedPlayers;

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
            databaseAppState.executeQuery("UPDATE users_meta SET value = '" + DatabaseAppState.escape(message.getValue()) + "' WHERE (userid = " + playerID + ") AND (key = '" + DatabaseAppState.escape(message.getKey()) + "')");
            databaseAppState.executeQuery("UPDATE users SET last_modification_date = " + System.currentTimeMillis() + " WHERE id = " + playerID);
        }
    }
}
