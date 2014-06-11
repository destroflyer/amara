/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.network.*;
import amara.engine.network.messages.*;

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
