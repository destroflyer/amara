/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.LinkedList;
import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public class SubNetworkServer extends BaseServer{

    public SubNetworkServer(NetworkServer networkServer){
        this.networkServer = networkServer;
    }
    private NetworkServer networkServer;
    private LinkedList<Integer> clientIDs = new LinkedList<Integer>();

    @Override
    public void onClientConnected(int clientID){
        super.onClientConnected(clientID);
        clientIDs.add(clientID);
    }

    @Override
    public void onClientDisconnected(int clientID){
        super.onClientDisconnected(clientID);
        clientIDs.remove(clientID);
    }

    @Override
    public void broadcastMessage(Message message){
        for(int clientID : clientIDs){
            sendMessageToClient(clientID, message);
        }
    }
    
    @Override
    public void sendMessageToClient(int clientID, Message message){
        networkServer.sendMessageToClient(clientID, message);
    }

    @Override
    public void close(){
        networkServer.removeSubServer(this);
    }

    public LinkedList<Integer> getClientIDs(){
        return clientIDs;
    }
}
