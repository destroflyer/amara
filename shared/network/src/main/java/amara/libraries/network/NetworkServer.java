/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.LinkedList;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Server;
import amara.libraries.network.exceptions.ServerCreationException;

/**
 *
 * @author Carl
 */
public class NetworkServer extends BaseServer {

    private Server server;
    private LinkedList<SubNetworkServer> subServers = new LinkedList<>();

    public void createServer(int port) throws ServerCreationException {
        try {
            server = new ExtendedDefaultServer(port);
            addMessageListeners();
            server.start();
        } catch (Exception ex) {
            throw new ServerCreationException(port);
        }
    }

    private void addMessageListeners() {
        server.addConnectionListener(new ConnectionListener() {

            @Override
            public void connectionAdded(Server server, HostedConnection connection) {
                onClientConnected(connection.getId());
            }

            @Override
            public void connectionRemoved(Server server, HostedConnection connection) {
                onClientDisconnected(connection.getId());
            }
        });
        server.addMessageListener((source, message) -> onMessageReceived(source.getId(), message));
    }

    @Override
    public void onClientConnected(int clientId) {
        super.onClientConnected(clientId);
        System.out.println("Client #" + clientId + " connected.");
    }

    @Override
    public void onClientDisconnected(int clientId) {
        super.onClientDisconnected(clientId);
        System.out.println("Client #" + clientId + " disconnected.");
    }

    public SubNetworkServer createSubServer() {
        SubNetworkServer subNetworkServer = new SubNetworkServer(this);
        subServers.add(subNetworkServer);
        return subNetworkServer;
    }

    public void removeSubServer(SubNetworkServer subNetworkServer) {
        subServers.remove(subNetworkServer);
    }

    @Override
    public void onMessageReceived(int sourceId, Message message) {
        super.onMessageReceived(sourceId, message);
        for (SubNetworkServer subServer : subServers) {
            if (subServer.contains(sourceId)) {
                subServer.onMessageReceived(sourceId, message);
            }
        }
    }

    @Override
    public void broadcastMessage(Message message) {
        server.broadcast(message);
    }

    @Override
    public void sendMessageToClient(int clientId, Message message) {
        server.broadcast(Filters.equalTo(server.getConnection(clientId)), message);
    }

    @Override
    public void close() {
        server.close();
        System.out.println("Server closed.");
    }
}
