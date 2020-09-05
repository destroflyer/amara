/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanLobby;
import amara.applications.master.network.messages.Message_LobbyQueueStatus;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class LobbyQueueStatusBackend implements MessageBackend{

    public LobbyQueueStatusBackend(PanLobby panLobby){
        this.panLobby = panLobby;
    }
    private PanLobby panLobby;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_LobbyQueueStatus){
            Message_LobbyQueueStatus message = (Message_LobbyQueueStatus) receivedMessage;
            panLobby.setIsQueueing(message.isQueueing());
        }
    }
}
