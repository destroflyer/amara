/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.network.backends.ReceivePongsBackend;
import amara.applications.ingame.network.messages.Message_Ping;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PingAppState extends BaseDisplayAppState<IngameClientApplication>{

    public PingAppState(){
        
    }
    private static final float UPDATE_INTERVAL = 1;
    private float timeSinceLastUpdate;
    private long pingTimestamp = -1;
    private int ping = -1;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePongsBackend(this));
        sendPing();
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        timeSinceLastUpdate += lastTimePerFrame;
        if(timeSinceLastUpdate > UPDATE_INTERVAL){
            sendPing();
        }
    }
    
    private void sendPing(){
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_Ping(System.currentTimeMillis()));
        timeSinceLastUpdate = 0;
    }

    public void onPong(long pingTimestamp){
        if(pingTimestamp > this.pingTimestamp){
            this.pingTimestamp = pingTimestamp;
            ping = (int) (System.currentTimeMillis() - pingTimestamp);
            ScreenController_HUD screenController_HUD = getAppState(NiftyAppState.class).getScreenController(ScreenController_HUD.class);
            if(screenController_HUD.isVisible()){
                screenController_HUD.setPing(ping);
            }
        }
    }

    public int getPing(){
        return ping;
    }
}
