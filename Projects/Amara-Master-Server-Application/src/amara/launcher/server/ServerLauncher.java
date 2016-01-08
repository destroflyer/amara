/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.server;

import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.launcher.LauncherUtil;
import amara.launcher.client.api.requests.RegisterMasterserverRequest;

/**
 *
 * @author Carl
 */
public class ServerLauncher{
    
    public ServerLauncher(int port){
        masterServer = new MasterserverServerApplication(port);
        masterServer.start();
        new RegisterMasterserverRequest(masterServer.getPort()).send();
    }
    private MasterserverServerApplication masterServer;
    
    public static void main(String[] args){
        LauncherUtil.initProgramProperties();
        new ServerLauncher(33900);
    }
}
