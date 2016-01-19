/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_LobbyClosed;
import amara.launcher.client.panels.PanPlay;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ClosedLobbyBackend implements MessageBackend{

    public ClosedLobbyBackend(PanPlay panPlay){
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_LobbyClosed){
            Message_LobbyClosed message = (Message_LobbyClosed) receivedMessage;
            panPlay.displayCreatePanel();
        }
    }
}
