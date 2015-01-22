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
public class EditCharacterInventoriesBackend implements MessageBackend{

    public EditCharacterInventoriesBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers){
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_EditCharacterInventory){
            Message_EditCharacterInventory message = (Message_EditCharacterInventory) receivedMessage;
            if(message.getInventory().length <= 6){
                int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
                databaseAppState.executeQuery("UPDATE users_characters SET inventory = " + databaseAppState.prepare(message.getInventory()) + " WHERE (userid = " + playerID + ") AND (characterid = " + message.getCharacterID() + ")");
            }
        }
    }
}