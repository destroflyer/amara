/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.Launcher_Core;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;
import amara.launcher.Launcher_Game;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;

/**
 *
 * @author Carl
 */
public class TestGame{
    
    public static void main(String[] args){
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        //Server
        MasterserverServerApplication masterServer = new MasterserverServerApplication(33900);
        masterServer.start();
        //Client
        HostInformation hostInformation = new HostInformation("localhost", masterServer.getPort());
        try{
            MasterserverClientApplication masterClient = new MasterserverClientApplication(hostInformation);
            masterClient.start();
            NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
            networkClient.sendMessage(new Message_Login(new AuthentificationInformation("destroflyer", "test")));
            networkClient.sendMessage(new Message_CreateLobby(new LobbyData("testmap")));
            networkClient.sendMessage(new Message_SetLobbyPlayerData(new LobbyPlayerData(11)));
            networkClient.sendMessage(new Message_StartGame());
        }catch(ServerConnectionException ex){
            ex.printStackTrace();
        }catch(ServerConnectionTimeoutException ex){
            ex.printStackTrace();
        }
    }
}
