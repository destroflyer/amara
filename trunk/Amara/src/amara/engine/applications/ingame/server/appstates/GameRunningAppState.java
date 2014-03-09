/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.masterserver.server.appstates.GamesAppState;
import amara.engine.network.NetworkServer;
import amara.engine.network.messages.Message_GameOver;

/**
 *
 * @author Carl
 */
public class GameRunningAppState extends ServerBaseAppState{

    public GameRunningAppState(){
        
    }
    
    public void onGameStarted(){
        
    }
    
    public void onGameOver(){
        GamesAppState gamesAppState = mainApplication.getMasterServer().getStateManager().getState(GamesAppState.class);
        gamesAppState.onGameOver(mainApplication.getGame());
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.broadcastMessage(new Message_GameOver());
        mainApplication.stop();
    }
}
