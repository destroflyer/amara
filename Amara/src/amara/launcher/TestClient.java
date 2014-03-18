/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import amara.launcher.client.ClientLauncher;
import amara.launcher.server.ServerLauncher;

/**
 *
 * @author Carl
 */
public class TestClient{
    
    public static void main(String[] args){
        FrameUtil.initProgramProperties();
        new ServerLauncher().setVisible(true);
        new ClientLauncher().setVisible(true);
    }
}
