/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import amara.engine.gui.GameScreenController;

/**
 *
 * @author Carl
 */
public class ScreenController_LoadingScreen extends GameScreenController{

    public ScreenController_LoadingScreen(){
        super("start");
    }
    
    public void setTitle(String title){
        getButton("title").setText(title);
    }
}
