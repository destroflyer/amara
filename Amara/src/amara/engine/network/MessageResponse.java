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
    private LinkedList<Message> answerMessages = new LinkedList<Message>();
    private LinkedList<Message> broadcastMessages = new LinkedList<Message>();

    public int getClientID(){
        return clientID;
    }

    public LinkedList<Message> getAnswerMessages(){
        return answerMessages;
    }

    public void addAnswerMessage(Message message){
        answerMessages.add(message);
    }

    public LinkedList<Message> getBroadcastMessages(){
        return broadcastMessages;
    }

    public void addBroadcastMessage(Message message){
        broadcastMessages.add(message);
    }
}
