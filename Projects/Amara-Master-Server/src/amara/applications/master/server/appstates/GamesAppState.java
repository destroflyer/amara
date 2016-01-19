/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.shared.games.GamePlayer;
import amara.applications.ingame.shared.games.Game;
import amara.applications.master.network.messages.Message_GameCreated;
import amara.applications.master.server.games.RunningGames;
import amara.applications.master.server.network.PortProvider;
import amara.applications.master.server.network.backends.StartGameBackend;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class GamesAppState extends ServerBaseAppState{

    public GamesAppState(PortProvider portProvider){
        runningGames = new RunningGames(portProvider);
    }
    private RunningGames runningGames;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new StartGameBackend(mainApplication));
    }
    
    public void onGameStartRequested(Game game){
        runningGames.registerGame(game);
    }
    
    public void onGameServerInitialized(Game game){
        NetworkServer masterNetworkServer = mainApplication.getStateManager().getState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = mainApplication.getStateManager().getState(PlayersAppState.class).getConnectedPlayers();
        for(GamePlayer player : game.getPlayers()){
            int clientID = connectedPlayers.getClientID(player.getLobbyPlayer().getID());
            masterNetworkServer.sendMessageToClient(clientID, new Message_GameCreated(game.getPort(), player.getAuthentificationKey()));
        }
    }
    
    public void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception){
        LogsAppState logsAppState = mainApplication.getStateManager().getState(LogsAppState.class);
        logsAppState.writeLog(LogsAppState.Type.Crash, "The game server crashed. (" + LogsAppState.printStackTrace(exception) + ")");
        if(ingameServerApplication != null){
            closeGame(ingameServerApplication);
            NetworkServer ingameNetworkServer = ingameServerApplication.getStateManager().getState(NetworkServerAppState.class).getNetworkServer();
            ingameNetworkServer.broadcastMessage(new Message_GameCrashed(exception.getClass().getSimpleName()));
        }
        System.out.println("Game crashed.");
    }
    
    public void onGameOver(IngameServerApplication ingameServerApplication){
        closeGame(ingameServerApplication);
        NetworkServer ingameNetworkServer = ingameServerApplication.getStateManager().getState(NetworkServerAppState.class).getNetworkServer();
        ingameNetworkServer.broadcastMessage(new Message_GameOver());
        //Wait to make sure the game over message is sent before shutting down the process
        try{
            Thread.sleep(3000);
        }catch(InterruptedException ex){
        }
        ingameNetworkServer.close();
    }
    
    private void closeGame(IngameServerApplication ingameServerApplication){
        runningGames.removeGame(ingameServerApplication.getGame());
        ingameServerApplication.stop();
    }

    public RunningGames getRunningGames(){
        return runningGames;
    }
}
