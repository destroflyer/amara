/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.network.messages.Message_GameCreated;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.interfaces.MasterserverClientInterface;
import amara.applications.master.client.MasterserverClientApplication;
import amara.libraries.applications.headless.applications.HeadlessAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class GameCreatedBackend implements MessageBackend{

    public GameCreatedBackend(){
        
    }
    private MasterserverClientInterface masterserverClientInterface = new MasterserverClientInterface(){

        @Override
        public <T extends HeadlessAppState> T getState(Class<T> stateClass){
            return MasterserverClientApplication.getInstance().getStateManager().getState(stateClass);
        }

        @Override
        public int getPlayerID(){
            return MasterserverClientUtil.getPlayerID();
        }

        @Override
        public PlayerProfileData getPlayerProfile(int playerID){
            return MasterserverClientUtil.getPlayerProfile(playerID);
        }
    };
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameCreated){
            Message_GameCreated message = (Message_GameCreated) receivedMessage;
            IngameClientApplication ingameClientApplication = new IngameClientApplication(masterserverClientInterface, message.getAuthentificationKey());
            ingameClientApplication.start();
        }
    }
}
