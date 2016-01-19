/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import amara.libraries.network.NetworkClient;
import amara.libraries.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class NetworkClientAppState extends BaseDisplayAppState{

    public NetworkClientAppState(String host, int port) throws ServerConnectionException, ServerConnectionTimeoutException{
        networkClient.connectToServer(host, port);
    }
    private NetworkClient networkClient = new NetworkClient();

    public NetworkClient getNetworkClient(){
        return networkClient;
    }
}
