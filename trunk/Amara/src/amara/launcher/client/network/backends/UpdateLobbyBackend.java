/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.network.backends;

import java.awt.EventQueue;
import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.*;
import amara.launcher.client.panels.PanPlay;

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
                    panPlay.displayLobbyPanel();
                    panPlay.getPanLobby().update(lobby);
                }
            });
        }
    }
}
