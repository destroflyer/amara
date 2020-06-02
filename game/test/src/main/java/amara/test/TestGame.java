/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.MasterserverServerApplication;
import amara.applications.master.server.launcher.Launcher_Game;
import amara.core.Launcher_Core;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.*;
import amara.libraries.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class TestGame {

    public static void main(String[] args) {
        String authToken = args[0];
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
            networkClient.sendMessage(new Message_CreateLobby());
            networkClient.sendMessage(new Message_SetLobbyData(new LobbyData("arama", new TeamFormat(2, 0))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY, "Bot", new GameSelectionPlayerData(11, null))));
            networkClient.sendMessage(new Message_StartLobbyQueue());
            networkClient.sendMessage(new Message_AcceptGameSelection(true));
            networkClient.sendMessage(new Message_SetGameSelectionPlayerData(new GameSelectionPlayerData(11, null)));
            networkClient.sendMessage(new Message_LockInGameSelection());
        } catch(ServerConnectionException | ServerConnectionTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
