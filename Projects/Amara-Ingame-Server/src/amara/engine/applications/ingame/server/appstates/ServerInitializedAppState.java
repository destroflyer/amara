/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

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
            mainApplication.getMasterServer().onGameServerInitialized(mainApplication.getGame());
        }
        frameIndex++;
    }
}
