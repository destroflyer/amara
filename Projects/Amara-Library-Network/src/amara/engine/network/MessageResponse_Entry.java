/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public class MessageResponse_Entry{

    public MessageResponse_Entry(Type type, Message message){
        this.type = type;
        this.message = message;
    }
    public enum Type{
        BROADCAST,
        ANSWER
    }
    private Type type;
    private Message message;

    public Type getType() {
        return type;
    }

    public Message getMessage(){
        return message;
    }
}
