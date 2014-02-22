/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import amara.engine.applications.*;
import amara.engine.applications.ingame.server.appstates.NetworkServerAppState;
import amara.engine.applications.masterserver.server.network.PortProvider;
import amara.engine.applications.masterserver.server.network.backends.StartGameBackend;
import amara.engine.applications.masterserver.server.network.messages.Message_GameStarted;
import amara.engine.network.NetworkServer;
import amara.game.games.*;
import amara.game.players.ConnectedPlayers;

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
    
    public void onGameServerInitialized(Game game){
        NetworkServer networkServer = mainApplication.getStateManager().getState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = mainApplication.getStateManager().getState(PlayersAppState.class).getConnectedPlayers();
        for(GamePlayer player : game.getPlayers()){
            int clientID = connectedPlayers.getClientID(player.getID());
            networkServer.sendMessageToClient(clientID, new Message_GameStarted(game.getPort(), player.getAuthentificationKey()));
        }
    }
    
    public void onGameOver(Game game){
        runningGames.removeGame(game);
    }

    public RunningGames getRunningGames(){
        return runningGames;
    }
}
