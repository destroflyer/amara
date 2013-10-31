/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.server.network.backends;

import amara.Queue;
import com.jme3.network.Message;
import amara.engine.network.*;
import amara.engine.network.messages.Message_Command;
import amara.game.entitysystem.systems.commands.PlayerCommand;

/**
 *
 * @author Carl
 */
public class ReceiveCommandsBackend implements MessageBackend{

    public ReceiveCommandsBackend(Queue<PlayerCommand> playerCommandsQueue){
        this.playerCommandsQueue = playerCommandsQueue;
    }
    private Queue<PlayerCommand> playerCommandsQueue;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Command){
            Message_Command message = (Message_Command) receivedMessage;
            playerCommandsQueue.add(new PlayerCommand(messageResponse.getClientID(), message.getCommand()));
        }
    }

    public Queue<PlayerCommand> getPlayerCommandsQueue(){
        return playerCommandsQueue;
    }
}
