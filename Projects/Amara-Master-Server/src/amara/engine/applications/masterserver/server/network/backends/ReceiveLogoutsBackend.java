/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_Logout;
import amara.game.players.*;
import amara.libraries.network.*;
import amara.libraries.network.messages.Message_ClientDisconnection;

/**
 *
 * @author Carl
 */
public class ReceiveLogoutsBackend implements MessageBackend{

    public ReceiveLogoutsBackend(ConnectedPlayers connectedPlayers){
        this.connectedPlayers = connectedPlayers;
    }
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Logout){
            Message_Logout message = (Message_Logout) receivedMessage;
            logout(messageResponse.getClientID());
        }
        else if(receivedMessage instanceof Message_ClientDisconnection){
            Message_ClientDisconnection message = (Message_ClientDisconnection) receivedMessage;
            logout(messageResponse.getClientID());
        }
    }
    
    private void logout(int clientID){
        Player player = connectedPlayers.getPlayer(clientID);
        if(player != null){
            connectedPlayers.logout(clientID);
            System.out.println("Logout '" + player.getLogin() + "' (#" + player.getID() + ")");
        }
    }
}
