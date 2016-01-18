/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.core.Launcher_Core;
import amara.launcher.Launcher_Game;
import amara.launcher.client.ClientLauncher;
import amara.launcher.server.ServerLauncherFrame;

/**
 *
 * @author Carl
 */
public class TestClient{
    
    public static void main(String[] args){
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        new ServerLauncherFrame().setVisible(true);
        new ClientLauncher().setVisible(true);
    }
}
