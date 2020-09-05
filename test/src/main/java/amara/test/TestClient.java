/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.master.client.launcher.MainFrame;
import amara.core.Launcher_Core;
import amara.applications.master.server.launcher.Launcher_Game;
import amara.applications.master.server.launcher.ServerLauncherFrame;

/**
 *
 * @author Carl
 */
public class TestClient {

    public static void main(String[] args) {
        String authToken = args[0];
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        new ServerLauncherFrame().setVisible(true);
        new MainFrame(authToken).setVisible(true);
    }
}
