/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import java.util.LinkedList;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class LobbiesAppState extends ServerBaseAppState{

    public LobbiesAppState(){
        
    }
    private LinkedList<Lobby> lobbies = new LinkedList<Lobby>();
    
    public boolean createLobby(int ownerID){
        Lobby lobby = getLobby(ownerID);
        if(lobby == null){
            lobby = new Lobby(ownerID);
            lobbies.add(lobby);
            sendUpdateToLobbyPlayers(lobby);
            return true;
        }
        return false;
    }
    
    public void setLobbyData(int playerID, LobbyData lobbyData){
        Lobby lobby = getLobby(playerID);
        if((lobby != null) && (playerID == lobby.getOwnerID())){
            lobby.setLobbyData(lobbyData);
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void addLobbyPlayer(int lobbyPlayerID, int newPlayerID){
        Lobby lobby = getLobby(lobbyPlayerID);
        Lobby newPlayerLobby = getLobby(newPlayerID);
        if((lobby != null) && (newPlayerLobby == null)){
            lobby.addPlayer(newPlayerID);
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void leaveLobby(int playerID){
        Lobby lobby = getLobby(playerID);
        if(lobby != null){
            if(playerID == lobby.getOwnerID()){
                removeLobby(lobby);
                sendMessageToLobbyPlayers(lobby, new Message_LobbyClosed());
            }
            else{
                lobby.removePlayer(playerID);
                sendUpdateToLobbyPlayers(lobby);
            }
        }
    }
    
    public void removeLobby(Lobby lobby){
        lobbies.remove(lobby);
    }
    
    public void kickLobbyPlayer(int ownerID, int playerID){
        Lobby lobby = getLobby(ownerID);
        if(lobby != null){
            lobby.removePlayer(playerID);
            sendUpdateToLobbyPlayers(lobby);
            sendMessageToPlayer(playerID, new Message_LobbyClosed());
        }
    }
    
    public Lobby getLobby(int playerID){
        for(Lobby lobby : lobbies){
            if(lobby.containsPlayer(playerID)){
                return lobby;
            }
        }
        return null;
    }
    
    private void sendUpdateToLobbyPlayers(Lobby lobby){
        sendMessageToLobbyPlayers(lobby, new Message_LobbyUpdate(lobby));
    }
    
    public void sendMessageToLobbyPlayers(Lobby lobby, Message message){
        for(int playerID : lobby.getPlayers()){
            sendMessageToPlayer(playerID, message);
        }
    }
    
    private void sendMessageToPlayer(int playerID, Message message){
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        int clientID = connectedPlayers.getClientID(playerID);
        networkServer.sendMessageToClient(clientID, message);
    }
}
