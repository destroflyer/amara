/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.panels;

import java.awt.GridLayout;
import com.jme3.system.JmeCanvasContext;
import amara.engine.applications.launcher.startscreen.LoginScreenApplication;
import amara.engine.applications.launcher.startscreen.screens.LoginScreen;

/**
 *
 * @author Carl
 */
public class PanLogin_JME extends PanLogin{
    
    public PanLogin_JME(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
        setLayout(new GridLayout());
        loginScreenApplication = new LoginScreenApplication(this, loginScreen);
        loginScreenApplication.createCanvas();
        JmeCanvasContext jmeCanvasContext = (JmeCanvasContext) loginScreenApplication.getContext();
        jmeCanvasContext.setSystemListener(loginScreenApplication);
        add(jmeCanvasContext.getCanvas());
    }
    private LoginScreen loginScreen;
    private LoginScreenApplication loginScreenApplication;

    @Override
    public void start(){
        loginScreenApplication.startCanvas();
        playBackgroundMusic("/" + loginScreen.getBackgroundMusicPath());
    }

    @Override
    public void showIsLoading(boolean isLoading){
        
    }
}
