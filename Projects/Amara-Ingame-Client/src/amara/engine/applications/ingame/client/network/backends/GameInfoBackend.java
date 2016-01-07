/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.app.state.AppStateManager;
import com.jme3.network.Message;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.applications.ingame.client.gui.ScreenController_LoadingScreen;
import amara.engine.appstates.*;
import amara.engine.network.*;
import amara.engine.network.messages.Message_GameInfo;
import amara.game.maps.*;

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
                    stateManager.getState(NiftyAppState.class).getScreenController(ScreenController_LoadingScreen.class).setTitle("Loading map...");
                    new Thread(new Runnable(){

                        @Override
                        public void run(){
                            System.out.println("Loading map \"" + message.getMapName() + "\".");
                            Map map = MapFileHandler.load(message.getMapName());
                            stateManager.attach(new MapAppState(map));
                            stateManager.attach(new MapObstaclesAppState());
                            stateManager.attach(new LocalEntitySystemAppState());
                            stateManager.attach(new PlayerAppState(message.getPlayerEntity()));
                            stateManager.attach(new ClientChatAppState());
                            stateManager.attach(new SendPlayerCommandsAppState());
                            stateManager.attach(new FreeCameraAppState());
                            stateManager.attach(new ClientInitializedAppState());
                            stateManager.getState(NiftyAppState.class).getScreenController(ScreenController_LoadingScreen.class).setTitle("Waiting for all players...");
                        }
                    }).start();
                }
            });
        }
    }
}
