/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.Message_Pong;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.network.*;

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
