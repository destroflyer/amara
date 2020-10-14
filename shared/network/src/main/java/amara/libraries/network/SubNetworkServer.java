package amara.libraries.network;

import java.util.LinkedList;
import com.jme3.network.Message;

public class SubNetworkServer extends BaseServer {

    SubNetworkServer(NetworkServer networkServer) {
        this.networkServer = networkServer;
    }
    private NetworkServer networkServer;
    private LinkedList<Integer> clientIds = new LinkedList<>();

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
