/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.*;

/**
 *
 * @author Carl
 */
public class TestGame{
    
    public static void main(String[] args){
        FrameUtil.initProgramProperties();
        //Server
        MasterserverServerApplication masterServer = new MasterserverServerApplication(33900);
        masterServer.start();
        //Client
        HostInformation hostInformation = new HostInformation("localhost", 33900);
        AuthentificationInformation authentificationInformation = new AuthentificationInformation("destroflyer", "test");
        MasterserverClientApplication masterClient = new MasterserverClientApplication(hostInformation, authentificationInformation);
        masterClient.start();
        //Start game
        NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_CreateLobby(new LobbyData("testmap")));
        networkClient.sendMessage(new Message_SetLobbyPlayerData(new LobbyPlayerData("minion")));
        networkClient.sendMessage(new Message_StartGame());
    }
}
