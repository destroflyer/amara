/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.PingAppState;
import amara.applications.ingame.network.messages.Message_Pong;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceivePongsBackend implements MessageBackend{

    public ReceivePongsBackend(PingAppState pingAppState){
        this.pingAppState = pingAppState;
    }
    private PingAppState pingAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Pong){
            Message_Pong message = (Message_Pong) receivedMessage;
            pingAppState.onPong(message.getTimestamp());
        }
    }
}
