/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.server.appstates;

import amara.engine.server.network.NetworkServer;
import amara.engine.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class NetworkServerAppState extends ServerBaseAppState{

    public NetworkServerAppState(int port) throws ServerCreationException{
        networkServer.createServer(port);
        System.out.println("Server started.");
    }
    private NetworkServer networkServer = new NetworkServer();

    public NetworkServer getNetworkServer(){
        return networkServer;
    }
}
