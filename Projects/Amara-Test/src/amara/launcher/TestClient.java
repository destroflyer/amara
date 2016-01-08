/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import amara.launcher.client.ClientLauncher;
import amara.launcher.server.ServerLauncherFrame;

/**
 *
 * @author Carl
 */
public class TestClient{
    
    public static void main(String[] args){
        LauncherUtil.initProgramProperties();
        new ServerLauncherFrame().setVisible(true);
        new ClientLauncher().setVisible(true);
    }
}
