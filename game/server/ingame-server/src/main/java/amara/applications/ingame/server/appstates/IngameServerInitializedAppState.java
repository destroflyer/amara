/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

/**
 *
 * @author Carl
 */
public class IngameServerInitializedAppState extends ServerBaseAppState{

    public IngameServerInitializedAppState(){
        
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
