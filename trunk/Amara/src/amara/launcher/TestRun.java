/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.system.JmeContext;
import amara.engine.applications.ingame.client.appstates.NetworkClientAppState;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.applications.masterserver.server.network.messages.Message_StartGame;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.*;

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
        masterServer.start(JmeContext.Type.Headless);
        //Client
        HostInformation hostInformation = new HostInformation("localhost", 33900);
        AuthentificationInformation authentificationInformation = new AuthentificationInformation("0", "");
        final MasterserverClientApplication masterClient = new MasterserverClientApplication(hostInformation, authentificationInformation);
        masterClient.start(JmeContext.Type.Headless);
        //Start game
        masterClient.enqueueTask(new Runnable(){

            @Override
            public void run(){
                NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientAppState.class).getNetworkClient();
                networkClient.sendMessage(new Message_StartGame(99, new int[]{0, 1, 2}));
            }
        });
    }
}
