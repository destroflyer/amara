/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

/**
 *
 * @author Carl
 */
public class ScreenController_GameOver extends GameScreenController{
    
    public void exit(){
        clientApplication.stop();
    }
}
