/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.loginscreen.gui;

import amara.applications.master.client.launcher.loginscreen.LoginScreenApplication;
import amara.applications.master.client.launcher.loginscreen.screens.LoginScreen;
import amara.applications.master.client.launcher.panels.PanLogin;
import amara.libraries.applications.display.gui.GameScreenController;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
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
            if(inputEvent == NiftyStandardInputEvent.SubmitText){
                login();
                return true;
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
