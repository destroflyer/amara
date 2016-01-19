/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import java.util.ArrayList;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.engine.applications.masterserver.server.network.backends.*;
import amara.game.players.ConnectedPlayers;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class LobbiesAppState extends ServerBaseAppState{

    public LobbiesAppState(){
        
    }
    private ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        networkServer.addMessageBackend(new CreateLobbiesBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new SetLobbiesDataBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new SetLobbiesPlayerDataBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new InviteLobbyPlayersBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new LeaveLobbiesBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new KickLobbyPlayersBackend(this, connectedPlayers));
    }
    
    public void createLobby(int ownerID, LobbyData lobbyData){
        Lobby lobby = getLobby(ownerID);
        if(lobby == null){
            lobby = new Lobby(ownerID, lobbyData);
            lobbies.add(lobby);
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void setLobbyData(int ownerID, LobbyData lobbyData){
        Lobby lobby = getLobby(ownerID);
        if((lobby != null) && (ownerID == lobby.getOwnerID())){
            lobby.setLobbyData(lobbyData);
            sendUpdateToLobbyPlayers(lobby);
        }
    }
    
    public void setPlayerData(int playerID, LobbyPlayerData lobbyPlayerData){
        Lobby lobby = getLobby(playerID);
        if(lobby != null){
            lobby.setPlayerData(playerID, lobbyPlayerData);
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
                lobbies.remove(lobby);
                sendMessageToLobbyPlayers(lobby, new Message_LobbyClosed());
            }
            else{
                lobby.removePlayer(playerID);
                sendUpdateToLobbyPlayers(lobby);
            }
        }
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
    
    private void sendMessageToLobbyPlayers(Lobby lobby, Message message){
        for(LobbyPlayer lobbyPlayer : lobby.getPlayers()){
            sendMessageToPlayer(lobbyPlayer.getID(), message);
        }
    }
    
    private void sendMessageToPlayer(int playerID, Message message){
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        int clientID = connectedPlayers.getClientID(playerID);
        networkServer.sendMessageToClient(clientID, message);
    }
}
