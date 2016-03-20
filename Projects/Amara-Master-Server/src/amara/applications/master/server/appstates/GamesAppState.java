/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.shared.games.*;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.games.RunningGames;
import amara.applications.master.server.network.PortProvider;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.NetworkServer;
import amara.libraries.network.SubNetworkServer;

/**
 *
 * @author Carl
 */
public class GamesAppState extends ServerBaseAppState{

    public GamesAppState(PortProvider portProvider){
        runningGames = new RunningGames(portProvider);
    }
    private RunningGames runningGames;
    
    public void sendGameSelectionAcceptRequest(GameSelection gameSelection){
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        for(GameSelectionPlayer[] team : gameSelection.getTeams()){
            for(GameSelectionPlayer player : team){
                int clientID = connectedPlayers.getClientID(player.getID());
                networkServer.sendMessageToClient(clientID, new Message_GameSelectionAcceptRequest());
            }
        }
    }
    
    public void startGame(GameSelection gameSelection){
        LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
        for(Lobby lobby : gameSelection.getLobbies()){
            lobbiesAppState.removeLobby(lobby);
        }
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        int[] clientIDs = getClientIDs(gameSelection);
        SubNetworkServer subNetworkServer = networkServer.addSubServer(clientIDs);
        System.out.println("Ingame server started.");
        Game game = new Game(gameSelection);
        IngameServerApplication ingameServerApplication = null;
        try{
            ingameServerApplication = new IngameServerApplication(mainApplication, subNetworkServer, game);
            ingameServerApplication.start();
            runningGames.registerGame(game);
        }catch(Exception ex){
            onGameCrashed(ingameServerApplication, ex);
        }
    }
    
    private int[] getClientIDs(GameSelection gameSelection){
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        int[] clientIDs = new int[gameSelection.getPlayersCount()];
        int i = 0;
        for(GameSelectionPlayer[] team : gameSelection.getTeams()){
            for(GameSelectionPlayer player : team){
                clientIDs[i] = connectedPlayers.getClientID(player.getID());
                i++;
            }
        }
        return clientIDs;
    }
    
    public void onGameServerInitialized(Game game){
        NetworkServer networkServer = mainApplication.getStateManager().getState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = mainApplication.getStateManager().getState(PlayersAppState.class).getConnectedPlayers();
        for(GamePlayer[] team : game.getTeams()){
            for(GamePlayer player : team){
                int clientID = connectedPlayers.getClientID(player.getGameSelectionPlayer().getID());
                networkServer.sendMessageToClient(clientID, new Message_GameCreated(player.getAuthentificationKey()));
            }
        }
    }
    
    public void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception){
        LogsAppState logsAppState = mainApplication.getStateManager().getState(LogsAppState.class);
        logsAppState.writeLog(LogsAppState.Type.Crash, "The game server crashed. (" + LogsAppState.printStackTrace(exception) + ")");
        if(ingameServerApplication != null){
            closeGame(ingameServerApplication);
            SubNetworkServer subNetworkServer = ingameServerApplication.getStateManager().getState(SubNetworkServerAppState.class).getSubNetworkServer();
            subNetworkServer.broadcastMessage(new Message_GameCrashed(exception.getClass().getSimpleName()));
            safelyCloseSubNetworkServer(subNetworkServer);
        }
        System.out.println("Game crashed.");
    }
    
    public void onGameOver(IngameServerApplication ingameServerApplication){
        closeGame(ingameServerApplication);
        SubNetworkServer subNetworkServer = ingameServerApplication.getStateManager().getState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.broadcastMessage(new Message_GameOver());
        safelyCloseSubNetworkServer(subNetworkServer);
    }
    
    private void safelyCloseSubNetworkServer(SubNetworkServer subNetworkServer){
        //Wait to make sure all messages (e.g. game over) are sent
        try{
            Thread.sleep(3000);
        }catch(InterruptedException ex){
        }
        subNetworkServer.close();
        System.out.println("Ingame server closed.");
    }
    
    private void closeGame(IngameServerApplication ingameServerApplication){
        runningGames.removeGame(ingameServerApplication.getGame());
        ingameServerApplication.stop();
    }

    public RunningGames getRunningGames(){
        return runningGames;
    }
}
