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
public class SubNetworkServer extends BaseServer {

    SubNetworkServer(NetworkServer networkServer) {
        this.networkServer = networkServer;
    }
    private NetworkServer networkServer;
    private LinkedList<Integer> clientIds = new LinkedList<>();

    @Override
    public void onClientConnected(int clientId) {
        super.onClientConnected(clientId);
        clientIds.add(clientId);
    }

    @Override
    public void onClientDisconnected(int clientId) {
        super.onClientDisconnected(clientId);
        clientIds.remove(clientId);
    }

    @Override
    public void broadcastMessage(Message message) {
        for (int clientId : clientIds) {
            sendMessageToClient(clientId, message);
        }
    }

    @Override
    public void sendMessageToClient(int clientId, Message message) {
        networkServer.sendMessageToClient(clientId, message);
    }

    @Override
    public void close() {
        networkServer.removeSubServer(this);
    }

    public void add(int clientId) {
        clientIds.add(clientId);
    }

    public void remove(int clientId) {
        clientIds.remove(clientId);
    }

    public boolean contains(int clientId) {
        return clientIds.contains(clientId);
    }
}
