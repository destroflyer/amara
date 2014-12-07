/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.launcher.startscreen.gui;

import amara.engine.applications.launcher.startscreen.LoginScreenApplication;
import amara.engine.applications.launcher.startscreen.screens.LoginScreen;
import amara.engine.gui.GameScreenController;
import amara.launcher.client.panels.PanLogin;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;

/**
 *
 * @author Carl
 */
public class ScreenController_LoginScreen extends GameScreenController{

    public ScreenController_LoginScreen(){
        super("start");
    }
    
    private KeyInputHandler loginKeyInputHandler = new KeyInputHandler(){
        
        @Override
        public boolean keyEvent(NiftyInputEvent inputEvent){
            if(inputEvent != null){
                switch(inputEvent){
                    case SubmitText:
                        login();
                        return true;
                }
            }
            return false;
        }
    };

    @Override
    public void onStartup(){
        super.onStartup();
        LoginScreen loginScreen = ((LoginScreenApplication) mainApplication).getLoginScreen();
        getImageRenderer("box_background").setImage(createImage(loginScreen.getLoginBoxImagePath()));
        getTextRenderer("label_login").setColor(loginScreen.getLoginBoxTextColor());
        getTextRenderer("label_password").setColor(loginScreen.getLoginBoxTextColor());
        getElementByID("login").addInputHandler(loginKeyInputHandler);
        getElementByID("password").addInputHandler(loginKeyInputHandler);
    }

    public void login(){
        String login = getTextField("login").getRealText();
        String password = getTextField("password").getRealText();
        PanLogin panLogin = ((LoginScreenApplication) mainApplication).getPanLogin();
        panLogin.login(login, password);
    }
}
