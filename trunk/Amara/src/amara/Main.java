/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.system.JmeContext;
import amara.engine.client.ClientApplication;
import amara.engine.server.ServerApplication;

/**
 *
 * @author Carl
 */
public class Main{
    
    public static void main(String[] args){
        Logger.getLogger("").setLevel(Level.SEVERE);
        ServerApplication serverApplication = new ServerApplication();
        serverApplication.start(JmeContext.Type.Headless);
        ClientApplication clientApplication = new ClientApplication();
        clientApplication.start();
    }
}
