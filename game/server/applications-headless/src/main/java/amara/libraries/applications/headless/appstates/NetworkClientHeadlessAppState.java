/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.headless.appstates;

import amara.libraries.network.NetworkClient;
import amara.libraries.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class NetworkClientHeadlessAppState extends BaseHeadlessAppState{

    public NetworkClientHeadlessAppState(String host, int port) throws ServerConnectionException, ServerConnectionTimeoutException{
        networkClient.connectToServer(host, port);
    }
    private NetworkClient networkClient = new NetworkClient();

    public NetworkClient getNetworkClient(){
        return networkClient;
    }
}
