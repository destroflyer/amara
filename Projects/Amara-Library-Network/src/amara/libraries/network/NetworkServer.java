/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.HashMap;
import java.util.LinkedList;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import amara.libraries.network.exceptions.ServerCreationException;

/**
 *
 * @author Carl
 */
public class NetworkServer extends BaseServer{
    
    public NetworkServer(){
        
    }
    private Server server;
    private HashMap<Integer, LinkedList<SubNetworkServer>> subServers = new HashMap<Integer, LinkedList<SubNetworkServer>>();

    public void createServer(int port) throws ServerCreationException{
        try{
            server = new ExtendedDefaultServer(port);
            addMessageListeners();
            server.start();
        }catch(Exception ex){
            throw new ServerCreationException(port);
        }
    }
    
    private void addMessageListeners(){
        server.addConnectionListener(new ConnectionListener(){

            @Override
            public void connectionAdded(Server server, HostedConnection connection){
                onClientConnected(connection.getId());
            }

            @Override
            public void connectionRemoved(Server server, HostedConnection connection){
                onClientDisconnected(connection.getId());
            }
        });
        server.addMessageListener(new MessageListener<HostedConnection>(){

            @Override
            public void messageReceived(HostedConnection source, Message message){
                onMessageReceived(source.getId(), message);
            }
        });
    }

    @Override
    public void onClientConnected(int clientID){
        subServers.put(clientID, new LinkedList<SubNetworkServer>());
        super.onClientConnected(clientID);
        System.out.println("Client #" + clientID + " connected.");
    }

    @Override
    public void onClientDisconnected(int clientID){
        super.onClientDisconnected(clientID);
        subServers.remove(clientID);
        System.out.println("Client #" + clientID + " disconnected.");
    }

    @Override
    public void onMessageReceived(int sourceID, Message message){
        super.onMessageReceived(sourceID, message);
        LinkedList<SubNetworkServer> clientSubServers = subServers.get(sourceID);
        for(SubNetworkServer subServer : clientSubServers){
            subServer.onMessageReceived(sourceID, message);
        }
    }
    
    public SubNetworkServer addSubServer(int... clientIDs){
        SubNetworkServer subServer = new SubNetworkServer(this);
        for(int clientID : clientIDs){
            LinkedList<SubNetworkServer> clientSubServers = subServers.get(clientID);
            clientSubServers.add(subServer);
            subServer.onClientConnected(clientID);
        }
        return subServer;
    }
    
    public void removeSubServer(SubNetworkServer subServer){
        for(int clientID : subServer.getClientIDs()){
            LinkedList<SubNetworkServer> clientSubServers = subServers.get(clientID);
            if(clientSubServers != null){
                clientSubServers.remove(subServer);
            }
        }
    }
    
    @Override
    public void broadcastMessage(Message message){
        server.broadcast(message);
    }
    
    @Override
    public void sendMessageToClient(int clientID, Message message){
        server.broadcast(Filters.equalTo(server.getConnection(clientID)), message);
    }

    @Override
    public void close(){
        server.close();
        System.out.println("Server closed.");
    }
}
