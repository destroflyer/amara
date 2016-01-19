/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.engine.applications.ingame.client.interfaces.MasterserverClientInterface;
import amara.engine.applications.masterserver.client.MasterserverClientUtil;
import amara.engine.network.*;
import amara.applications.master.network.messages.Message_GameCreated;

/**
 *
 * @author Carl
 */
public class GameCreatedBackend implements MessageBackend{

    public GameCreatedBackend(HostInformation masterserverInformation){
        this.masterserverInformation = masterserverInformation;
    }
    private HostInformation masterserverInformation;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameCreated){
            Message_GameCreated message = (Message_GameCreated) receivedMessage;
            HostInformation ingameServerInformation = new HostInformation(masterserverInformation.getHost(), message.getPort());
            IngameClientApplication clientApplication = new IngameClientApplication(ingameServerInformation, message.getAuthentificationKey(), new MasterserverClientInterface() {

                @Override
                public int getPlayerID(){
                    return MasterserverClientUtil.getPlayerID();
                }

                @Override
                public PlayerProfileData getPlayerProfile(int playerID){
                    return MasterserverClientUtil.getPlayerProfile(playerID);
                }
            });
            clientApplication.start();
        }
    }
}
