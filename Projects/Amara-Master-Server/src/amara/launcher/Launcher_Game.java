/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import amara.engine.network.*;
import amara.game.entitysystem.CustomGameTemplates;

/**
 *
 * @author Carl
 */
public class Launcher_Game{
    
    public static void initialize(){
        MessagesSerializer_Protocol.registerClasses();
        MessagesSerializer_Game.registerClasses();
        CustomGameTemplates.registerLoader();
    }
}
