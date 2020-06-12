/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.Collection;
import java.util.LinkedList;
import com.jme3.network.Message;
import amara.libraries.network.messages.Message_ClientConnection;
import amara.libraries.network.messages.Message_ClientDisconnection;

/**
 *
 * @author Carl
 */
public abstract class BaseServer extends NetworkListener {
    
    public void onClientConnected(int clientID) {
        onMessageReceived(clientID, new Message_ClientConnection());
    }
    
    public void onClientDisconnected(int clientID) {
        onMessageReceived(clientID, new Message_ClientDisconnection());
    }
    
    @Override
    public void sendMessageResponse(MessageResponse messageResponse) {
        LinkedList<MessageResponse_Entry> messageResponseEntries = messageResponse.getEntries();
        for (MessageResponse_Entry messageResponseEntry : messageResponseEntries) {
            switch (messageResponseEntry.getType()) {
                case BROADCAST:
                    broadcastMessage(messageResponseEntry.getMessage());
                    break;
                
                case ANSWER:
                    sendMessageToClient(messageResponse.getClientId(), messageResponseEntry.getMessage());
                    break;
            }
        }
    }
    
    public abstract void broadcastMessage(Message message);

    public void sendMessageToClients(Collection<Integer> clientIds, Message message) {
        for (int clientID : clientIds) {
            sendMessageToClient(clientID, message);
        }
    }
    
    public abstract void sendMessageToClient(int clientId, Message message);
    
    public abstract void close();
}
