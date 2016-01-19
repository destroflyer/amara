/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import amara.applications.ingame.network.MessagesSerializer_Ingame;
import amara.applications.master.network.MessagesSerializer_Master;
import amara.game.entitysystem.CustomGameTemplates;
import amara.game.entitysystem.templates.CustomSerializer_Ingame;

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
