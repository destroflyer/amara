/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.masterserver.server.appstates.GamesAppState;

/**
 *
 * @author Carl
 */
public class ServerInitializedAppState extends ServerBaseAppState{

    public ServerInitializedAppState(){
        
    }
    private int frameIndex;
    
    @Override
    public void update(float lastTimePerFrame){
        if(frameIndex == 1){
            GamesAppState gamesAppState = mainApplication.getMasterServer().getStateManager().getState(GamesAppState.class);
            gamesAppState.onGameServerInitialized(mainApplication.getGame());
        }
        frameIndex++;
    }
}
