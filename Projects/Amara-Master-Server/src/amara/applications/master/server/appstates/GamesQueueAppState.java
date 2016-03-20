/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import java.util.ArrayList;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.games.PendingGameSelection;
import amara.applications.master.server.network.backends.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class GamesQueueAppState extends ServerBaseAppState{

    public GamesQueueAppState(){
        
    }
    private static final float ACCEPT_TIMER = 10;
    private ArrayList<GameSelection> queueingGameSelections = new ArrayList<GameSelection>();
    private ArrayList<PendingGameSelection> pendingGameSelections = new ArrayList<PendingGameSelection>();
    private ArrayList<GameSelection> runningGameSelections = new ArrayList<GameSelection>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        networkServer.addMessageBackend(new CreateLobbiesBackend(lobbiesAppState, connectedPlayers));
        networkServer.addMessageBackend(new SetLobbiesDataBackend(lobbiesAppState, connectedPlayers));
        networkServer.addMessageBackend(new InviteLobbyPlayersBackend(lobbiesAppState, connectedPlayers));
        networkServer.addMessageBackend(new LeaveLobbiesBackend(lobbiesAppState, connectedPlayers));
        networkServer.addMessageBackend(new KickLobbyPlayersBackend(lobbiesAppState, connectedPlayers));
        networkServer.addMessageBackend(new StartLobbyQueuesBackend(lobbiesAppState, this, connectedPlayers));
        networkServer.addMessageBackend(new CancelLobbyQueuesBackend(lobbiesAppState, this, connectedPlayers));
        networkServer.addMessageBackend(new AcceptGameSelectionsBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new SetGameSelectionsPlayerDataBackend(this, connectedPlayers));
        networkServer.addMessageBackend(new LockInGameSelectionsBackend(this, connectedPlayers));
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        for(int i=0;i<pendingGameSelections.size();i++){
            PendingGameSelection pendingGameSelection = pendingGameSelections.get(i);
            GameSelection gameSelection = pendingGameSelection.getGameSelection();
            pendingGameSelection.onTimePassed(lastTimePerFrame);
            if(pendingGameSelection.getRemainingAcceptTime() <= 0){
                pendingGameSelections.remove(i);
                i--;
                LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
                boolean requeueLobby;
                for(Lobby lobby : gameSelection.getLobbies()){
                    requeueLobby = true;
                    for(int playerID : lobby.getPlayers()){
                        if(!pendingGameSelection.hasAccepted(playerID)){
                            requeueLobby = false;
                            break;
                        }
                    }
                    if(requeueLobby){
                        startLobbyQueue(lobby);
                    }
                    else{
                        lobbiesAppState.sendMessageToLobbyPlayers(lobby, new Message_LobbyQueueStatus(false));
                    }
                }
            }
        }
        GamesAppState gamesAppState = getAppState(GamesAppState.class);
        for(int i=0;i<runningGameSelections.size();i++){
            GameSelection gameSelection = runningGameSelections.get(i);
            if(gameSelection.areTeamsLockedIn()){
                runningGameSelections.remove(i);
                i--;
                gamesAppState.startGame(gameSelection);
            }
        }
    }
    
    public void startLobbyQueue(Lobby lobby){
        boolean createGameSelection = true;
        for(GameSelection gameSelection : queueingGameSelections){
            if(gameSelection.getLobbies().contains(lobby)){
                return;
            }
            else if(gameSelection.isMatchingLobby(lobby)){
                if(tryAddingLobby(gameSelection, lobby) != -1){
                    createGameSelection = false;
                    break;
                }
            }
        }
        if(createGameSelection){
            LobbyData lobbyData = lobby.getLobbyData();
            GameSelection gameSelection = new GameSelection(new GameSelectionData(lobbyData.getMapName(), lobbyData.getTeamFormat()));
            queueingGameSelections.add(gameSelection);
            tryAddingLobby(gameSelection, lobby);
        }
        LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
        lobbiesAppState.sendMessageToLobbyPlayers(lobby, new Message_LobbyQueueStatus(true));
    }
    
    private int tryAddingLobby(GameSelection gameSelection, Lobby lobby){
        int[] remainingTeamSpots = gameSelection.getRemainingTeamSpots();
        for(int i=0;i<remainingTeamSpots.length;i++){
            if(remainingTeamSpots[i] >= lobby.getPlayers().size()){
                gameSelection.addLobby(i, lobby);
                if(gameSelection.areTeamsFilled()){
                    queueingGameSelections.remove(gameSelection);
                    pendingGameSelections.add(new PendingGameSelection(gameSelection, ACCEPT_TIMER));
                    sendMessageToGameSelectionPlayers(gameSelection, new Message_GameSelectionAcceptRequest());
                }
                return i;
            }
        }
        return -1;
    }
    
    public void cancelLobbyQueue(Lobby lobby){
        GameSelection affectedGameSelection = null;
        for(GameSelection gameSelection : queueingGameSelections){
            if(gameSelection.getLobbies().contains(lobby)){
                affectedGameSelection = gameSelection;
                break;
            }
        }
        if(affectedGameSelection != null){
            queueingGameSelections.remove(affectedGameSelection);
            for(Lobby lobbyToReadd : affectedGameSelection.getLobbies()){
                if(lobbyToReadd != lobby){
                    startLobbyQueue(lobbyToReadd);
                }
            }
            LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
            lobbiesAppState.sendMessageToLobbyPlayers(lobby, new Message_LobbyQueueStatus(false));
        }
    }
    
    public void acceptGameSelection(int playerID, boolean isAccepted){
        for(int i=0;i<pendingGameSelections.size();i++){
            PendingGameSelection pendingGameSelection = pendingGameSelections.get(i);
            GameSelection gameSelection = pendingGameSelection.getGameSelection();
            if(gameSelection.containsPlayer(playerID)){
                if(isAccepted){
                    pendingGameSelection.accept(playerID);
                    if(pendingGameSelection.isAccepted()){
                        pendingGameSelections.remove(i);
                        i--;
                        runningGameSelections.add(gameSelection);
                        sendMessageToGameSelectionPlayers(gameSelection, new Message_GameSelectionUpdate(gameSelection));
                    }
                }
                else{
                    pendingGameSelections.remove(i);
                    LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
                    for(Lobby lobby : gameSelection.getLobbies()){
                        if(lobby.containsPlayer(playerID)){
                            lobbiesAppState.sendMessageToLobbyPlayers(lobby, new Message_LobbyQueueStatus(false));
                        }
                        else{
                            startLobbyQueue(lobby);
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public void setGameSelectionPlayerData(int playerID, GameSelectionPlayerData gameSelectionPlayerData){
        for(GameSelection gameSelection : runningGameSelections){
            GameSelectionPlayer player = gameSelection.getPlayer(playerID);
            if(player != null){
                player.setPlayerData(gameSelectionPlayerData);
                sendMessageToGameSelectionPlayers(gameSelection, new Message_GameSelectionUpdate(gameSelection));
                break;
            }
        }
    }
    
    public void lockInGameSelection(int playerID){
        for(GameSelection gameSelection : runningGameSelections){
            GameSelectionPlayer player = gameSelection.getPlayer(playerID);
            if(player != null){
                player.lockIn();
                break;
            }
        }
    }
    
    private void sendMessageToGameSelectionPlayers(GameSelection gameSelection, Message message){
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        for(GameSelectionPlayer[] team : gameSelection.getTeams()){
            for(GameSelectionPlayer player : team){
                int clientID = connectedPlayers.getClientID(player.getID());
                networkServer.sendMessageToClient(clientID, message);
            }
        }
    }
}
