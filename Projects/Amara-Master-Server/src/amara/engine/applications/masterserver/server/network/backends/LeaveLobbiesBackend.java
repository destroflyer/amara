/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_LeaveLobby;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.network.*;
import amara.engine.network.messages.Message_ClientDisconnection;
import amara.game.players.*;

/**
 *
 * @author Carl
 */
public class LeaveLobbiesBackend implements MessageBackend{

    public LeaveLobbiesBackend(LobbiesAppState lobbiesAppState, ConnectedPlayers connectedPlayers){
        this.lobbiesAppState = lobbiesAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private LobbiesAppState lobbiesAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_LeaveLobby){
            Message_LeaveLobby message = (Message_LeaveLobby) receivedMessage;
            leaveLobby(messageResponse.getClientID());
        }
        else if(receivedMessage instanceof Message_ClientDisconnection){
            Message_ClientDisconnection message = (Message_ClientDisconnection) receivedMessage;
            leaveLobby(messageResponse.getClientID());
        }
    }
    
    private void leaveLobby(int clientID){
        Player player = connectedPlayers.getPlayer(clientID);
        if(player != null){
            lobbiesAppState.leaveLobby(player.getID());
        }
    }
}
