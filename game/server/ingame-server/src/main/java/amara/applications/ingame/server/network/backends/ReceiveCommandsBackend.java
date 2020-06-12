/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.Message_Command;
import amara.applications.ingame.network.messages.objects.commands.PlayerCommand;
import amara.applications.ingame.server.appstates.ReceiveCommandsAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceiveCommandsBackend implements MessageBackend{

    public ReceiveCommandsBackend(ReceiveCommandsAppState receiveCommandsAppState){
        this.receiveCommandsAppState = receiveCommandsAppState;
    }
    private ReceiveCommandsAppState receiveCommandsAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Command){
            Message_Command message = (Message_Command) receivedMessage;
            receiveCommandsAppState.onCommandReceived(new PlayerCommand(messageResponse.getClientId(), message.getCommand()));
        }
    }
}
