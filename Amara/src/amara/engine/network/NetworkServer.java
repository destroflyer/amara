/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.util.LinkedList;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import amara.engine.network.exceptions.*;
import amara.engine.network.messages.*;

/**
 *
 * @author Carl
 */
public class NetworkServer extends NetworkListener{
    
    public NetworkServer(){
        
    }
    private Server server;

    public void createServer(int port) throws ServerCreationException{
        try{
            server = Network.createServer(port);
            server.start();
            addMessageListeners();
        }catch(Exception ex){
            throw new ServerCreationException(port);
        }
    }
    
    private void addMessageListeners(){
        server.addConnectionListener(new ConnectionListener(){

            @Override
            public void connectionAdded(Server server, HostedConnection connection){
                System.out.println("Client #" + connection.getId() + " connected.");
                onMessageReceived(connection, new Message_ClientConnection());
            }

            @Override
            public void connectionRemoved(Server server, HostedConnection connection){
                System.out.println("Client #" + connection.getId() + " disconnected.");
                onMessageReceived(connection, new Message_ClientDisconnection());
            }
        });
        server.addMessageListener(new MessageListener<MessageConnection>(){

            @Override
            public void messageReceived(MessageConnection source, Message message){
                onMessageReceived(source, message);
            }
        });
    }
    
    @Override
    public void sendMessageResponse(MessageResponse messageResponse){
        LinkedList<MessageResponse_Entry> messageResponseEntries = messageResponse.getEntries();
        for(MessageResponse_Entry messageResponseEntry : messageResponseEntries){
            switch(messageResponseEntry.getType()){
                case BROADCAST:
                    broadcastMessage(messageResponseEntry.getMessage());
                    break;
                
                case ANSWER:
                    sendMessageToClient(messageResponse.getClientID(), messageResponseEntry.getMessage());
                    break;
            }
        }
    }
    
    public void sendMessageToClient(int clientID, Message message){
        server.broadcast(Filters.equalTo(server.getConnection(clientID)), message);
    }
    
    public void broadcastMessage(Message message){
        server.broadcast(message);
    }

    public void close(){
        server.close();
    }
}
