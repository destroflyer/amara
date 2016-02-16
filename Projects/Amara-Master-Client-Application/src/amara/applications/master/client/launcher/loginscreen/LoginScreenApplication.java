package amara.applications.master.client.launcher.loginscreen;

import amara.applications.master.client.launcher.loginscreen.appstates.NiftyAppState_LoginScreen;
import amara.applications.master.client.launcher.loginscreen.screens.LoginScreen;
import amara.applications.master.client.launcher.panels.PanLogin;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;

/**
 * @author Carl
 */
public class LoginScreenApplication extends DisplayApplication{

    public LoginScreenApplication(PanLogin panLogin, LoginScreen loginScreen){
        this.panLogin= panLogin;
        this.loginScreen = loginScreen;
    }
    private PanLogin panLogin;
    private LoginScreen loginScreen;
    private boolean isStarted;
    
    @Override
    public void simpleInitApp(){
        super.simpleInitApp();
        stateManager.attach(new NiftyAppState());
        stateManager.attach(new NiftyAppState_LoginScreen());
        stateManager.attach(new AudioAppState());
        stateManager.attach(new LightAppState());
        stateManager.attach(new WaterAppState());
        stateManager.attach(new PostFilterAppState());
        setDisplayFps(false);
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(false);
        loginScreen.initialize(this);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        super.simpleUpdate(lastTimePerFrame);
        if(!isStarted){
            isStarted = true;
        }
    }

    public PanLogin getPanLogin(){
        return panLogin;
    }

    public LoginScreen getLoginScreen(){
        return loginScreen;
    }

    public boolean isStarted(){
        return isStarted;
    }
}
