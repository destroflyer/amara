/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.headless.appstates;

import amara.engine.network.NetworkClient;
import amara.engine.network.exceptions.*;

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
