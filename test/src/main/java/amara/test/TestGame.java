/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.network.messages.Message_Login;
import amara.applications.master.server.MasterserverServerApplication;
import amara.applications.master.server.launcher.Launcher_Game;
import amara.core.Launcher_Core;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.HostInformation;
import amara.libraries.network.NetworkClient;
import amara.libraries.network.exceptions.ServerConnectionException;
import amara.libraries.network.exceptions.ServerConnectionTimeoutException;

import java.util.function.Consumer;

/**
 *
 * @author Carl
 */
public class TestGame {

    protected static void startServerAndLogin(String authToken, Consumer<NetworkClient> callback) {
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        // Server
        MasterserverServerApplication masterServer = new MasterserverServerApplication(33900);
        masterServer.start();
        // Client
        try {
            MasterserverClientApplication masterClient = new MasterserverClientApplication(new HostInformation("localhost", masterServer.getPort()));
            masterClient.start();
            NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
            networkClient.sendMessage(new Message_Login(authToken));
            callback.accept(networkClient);
        } catch (ServerConnectionException | ServerConnectionTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
