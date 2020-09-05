/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.launcher;

import amara.applications.master.server.MasterserverServerApplication;
import amara.core.Launcher_Core;

/**
 *
 * @author Carl
 */
public class ServerLauncher{
    
    public ServerLauncher(int port){
        masterServer = new MasterserverServerApplication(port);
        masterServer.start();
        //new RegisterMasterserverRequest(masterServer.getPort()).send();
    }
    private MasterserverServerApplication masterServer;
    
    public static void main(String[] args){
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        new ServerLauncher(33900);
    }
}
