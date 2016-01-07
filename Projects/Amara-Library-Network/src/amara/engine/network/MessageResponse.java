/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.util.LinkedList;
import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public class MessageResponse{

    public MessageResponse(int clientID){
        this.clientID = clientID;
    }
    private int clientID;
    private LinkedList<MessageResponse_Entry> entries = new LinkedList<MessageResponse_Entry>();

    public void addBroadcastMessage(Message message){
        entries.add(new MessageResponse_Entry(MessageResponse_Entry.Type.BROADCAST, message));
    }

    public void addAnswerMessage(Message message){
        entries.add(new MessageResponse_Entry(MessageResponse_Entry.Type.ANSWER, message));
    }

    public int getClientID(){
        return clientID;
    }

    public LinkedList<MessageResponse_Entry> getEntries(){
        return entries;
    }
}
