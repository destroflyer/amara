/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.ingame.network.messages.objects.commands.Command;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_Command extends AbstractMessage{
    
    public Message_Command(){
        
    }
    
    public Message_Command(Command command){
        this.command = command;
    }
    private Command command;

    public Command getCommand(){
        return command;
    }
}
