/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import java.awt.EventQueue;
import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;
import amara.applications.master.network.messages.Message_LobbyUpdate;
import amara.applications.master.network.messages.objects.Lobby;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class UpdateLobbyBackend implements MessageBackend{

    public UpdateLobbyBackend(PanPlay panPlay){
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_LobbyUpdate){
            Message_LobbyUpdate message = (Message_LobbyUpdate) receivedMessage;
            final Lobby lobby = message.getLobby();
            EventQueue.invokeLater(new Runnable(){

                @Override
                public void run(){
                    panPlay.getPanLobby().update(lobby);
                    panPlay.displayLobbyPanel();
                }
            });
        }
    }
}
