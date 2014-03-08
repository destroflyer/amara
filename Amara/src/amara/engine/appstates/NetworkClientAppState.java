/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import amara.engine.network.NetworkClient;
import amara.engine.network.exceptions.*;

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
