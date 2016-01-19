/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.launcher;

import amara.applications.ingame.entitysystem.CustomGameTemplates;
import amara.applications.ingame.entitysystem.templates.CustomSerializer_Ingame;
import amara.applications.ingame.network.MessagesSerializer_Ingame;
import amara.applications.master.network.MessagesSerializer_Master;

/**
 *
 * @author Carl
 */
public class Launcher_Game{
    
    public static void initialize(){
        MessagesSerializer_Master.registerClasses();
        MessagesSerializer_Ingame.registerClasses();
        CustomSerializer_Ingame.registerClasses();
        CustomGameTemplates.registerLoader();
    }
}
