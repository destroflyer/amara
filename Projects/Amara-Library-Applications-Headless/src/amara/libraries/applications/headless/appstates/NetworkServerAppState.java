/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.headless.appstates;

import amara.libraries.network.NetworkServer;
import amara.libraries.network.exceptions.ServerCreationException;

/**
 *
 * @author Carl
 */
public class NetworkServerAppState extends BaseHeadlessAppState{

    public NetworkServerAppState(int port) throws ServerCreationException{
        networkServer.createServer(port);
        System.out.println("Server started.");
    }
    private NetworkServer networkServer = new NetworkServer();

    public NetworkServer getNetworkServer(){
        return networkServer;
    }
}
