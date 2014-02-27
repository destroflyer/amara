/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import java.util.logging.Level;
import java.util.logging.Logger;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.applications.masterserver.server.network.messages.Message_StartGame;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.*;
import amara.game.games.PlayerData;

/**
 *
 * @author Carl
 */
public class TestRun{
    
    public static void main(String[] args){
        Logger.getLogger("").setLevel(Level.SEVERE);
        MessagesSerializer.registerClasses();
        //Server
        MasterserverServerApplication masterServer = new MasterserverServerApplication(33900);
        masterServer.start();
        //Client
        HostInformation hostInformation = new HostInformation("localhost", 33900);
        AuthentificationInformation authentificationInformation = new AuthentificationInformation("0", "");
        MasterserverClientApplication masterClient = new MasterserverClientApplication(hostInformation, authentificationInformation);
        masterClient.start();
        //Start game
        NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_StartGame(99, new PlayerData[]{
            new PlayerData(0, "minion"),
            new PlayerData(1, "wizard"),
            new PlayerData(2, "robot")
        }));
    }
}
