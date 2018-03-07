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
            lobby = new Lobby(new LobbyPlayer_Human(ownerID));
            lobbies.add(lobby);
            sendUpdateToLobbyPlayers(lobby);
            return true;
        }
        return false;
    }
    
    public void setLobbyData(int playerID, LobbyData lobbyData){
        Lobby lobby = getLobby(playerID);
        if((lobby != null) && (playerID == lobby.getOwner().getPlayerID())){
            lobby.setLobbyData(lobbyData);
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void addLobbyPlayer(int playerID, int newPlayerID){
        Lobby lobby = getLobby(playerID);
        Lobby newPlayerLobby = getLobby(newPlayerID);
        if((lobby != null) && (newPlayerLobby == null)){
            lobby.addPlayer(new LobbyPlayer_Human(newPlayerID));
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void addLobbyBot(int lobbyPlayerID, LobbyPlayer_Bot lobbyPlayer_Bot){
        Lobby lobby = getLobby(lobbyPlayerID);
        if(lobby != null){
            lobby.addPlayer(lobbyPlayer_Bot);
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void leaveLobby(int playerID){
        Lobby lobby = getLobby(playerID);
        if(lobby != null){
            if(playerID == lobby.getOwner().getPlayerID()){
                removeLobby(lobby);
                sendMessageToLobbyPlayers(lobby, new Message_LobbyClosed());
            }
            else{
                lobby.removeHumanPlayer(playerID);
                sendUpdateToLobbyPlayers(lobby);
            }
        }
    }
    
    public void removeLobby(Lobby lobby){
        lobbies.remove(lobby);
    }
    
    public void kickLobbyPlayer(int ownerID, int lobbyPlayerID){
        Lobby lobby = getLobby(ownerID);
        if(lobby != null){
            LobbyPlayer lobbyPlayer = lobby.removePlayerByLobbyPlayerID(lobbyPlayerID);
            sendUpdateToLobbyPlayers(lobby);
            if(lobbyPlayer instanceof LobbyPlayer_Human){
                LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                sendMessageToPlayer(lobbyPlayer_Human.getPlayerID(), new Message_LobbyClosed());
            }
        }
    }
    
    public Lobby getLobby(int playerID){
        for(Lobby lobby : lobbies){
            if(lobby.containsHumanPlayer(playerID)){
                return lobby;
            }
        }
        return null;
    }
    
    private void sendUpdateToLobbyPlayers(Lobby lobby){
        sendMessageToLobbyPlayers(lobby, new Message_LobbyUpdate(lobby));
    }
    
    public void sendMessageToLobbyPlayers(Lobby lobby, Message message){
        for(LobbyPlayer lobbyPlayer : lobby.getPlayers()){
            if (lobbyPlayer instanceof LobbyPlayer_Human) {
                LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                sendMessageToPlayer(lobbyPlayer_Human.getPlayerID(), message);
            }
        }
    }
    
    public void sendMessageToLobbyOwner(Lobby lobby, Message message){
        sendMessageToPlayer(lobby.getOwner().getPlayerID(), message);
    }
    
    private void sendMessageToPlayer(int playerID, Message message){
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        int clientID = connectedPlayers.getClientID(playerID);
        networkServer.sendMessageToClient(clientID, message);
    }
}
