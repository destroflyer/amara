/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceivePingsBackend implements MessageBackend{

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Ping){
            Message_Ping message = (Message_Ping) receivedMessage;
            messageResponse.addAnswerMessage(new Message_Pong(message.getTimestamp()));
        }
    }
}
