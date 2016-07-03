/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.app.state.AppStateManager;
import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.*;
import amara.applications.ingame.network.messages.Message_GameInfo;
import amara.applications.ingame.shared.maps.*;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class GameInfoBackend implements MessageBackend{

    public GameInfoBackend(DisplayApplication mainApplication){
        this.mainApplication = mainApplication;
    }
    private DisplayApplication mainApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameInfo){
            final Message_GameInfo message = (Message_GameInfo) receivedMessage;
            final AppStateManager stateManager = mainApplication.getStateManager();
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    mainApplication.getStateManager().getState(LoadingScreenAppState.class).setTitle("Loading map...");
                    new Thread(new Runnable(){

                        @Override
                        public void run(){
                            String mapName = message.getGameSelection().getGameSelectionData().getMapName();
                            System.out.println("Loading map \"" + mapName + "\".");
                            Map map = MapFileHandler.load(mapName);
                            //This has to be created before attaching the LocalEntitySystemAppState, since it initializes the PlayerTeamSystem in the constructor
                            PlayerAppState playerAppState = new PlayerAppState(message.getPlayerEntity());
                            stateManager.attach(new MapAppState(map));
                            stateManager.attach(new MapObstaclesAppState());
                            stateManager.attach(new LocalEntitySystemAppState());
                            stateManager.attach(new SynchronizeEntityWorldAppState());
                            stateManager.attach(playerAppState);
                            stateManager.attach(new ClientInitializedAppState());
                            mainApplication.getStateManager().getState(LoadingScreenAppState.class).setTitle("Waiting for all players...");
                        }
                    }).start();
                }
            });
        }
    }
}
