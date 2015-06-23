/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.util.ArrayList;
import com.jme3.network.*;

/**
 *
 * @author Carl
 */
public abstract class NetworkListener{

    public NetworkListener(){
        
    }
    private ArrayList<MessageBackend> messageBackends = new ArrayList<MessageBackend>();

    protected void onMessageReceived(MessageConnection source, Message message){
        int clientID = getMessageClientID(source);
        MessageResponse messageResponse = new MessageResponse(clientID);
        for(int i=0;i<messageBackends.size();i++){
            MessageBackend messageBackend = messageBackends.get(i);
            messageBackend.onMessageReceived(message, messageResponse);
        }
        sendMessageResponse(messageResponse);
    }
    
    private int getMessageClientID(MessageConnection source){
        if(source instanceof HostedConnection){
            HostedConnection connection = (HostedConnection) source;
            return connection.getId();
        }
        return -1;
    }
    
    public abstract void sendMessageResponse(MessageResponse messageResponse);

    public void addMessageBackend(MessageBackend messageBackend){
        messageBackends.add(messageBackend);
    }

    public void removeMessageBackend(MessageBackend messageBackend){
        messageBackends.remove(messageBackend);
    }
}
