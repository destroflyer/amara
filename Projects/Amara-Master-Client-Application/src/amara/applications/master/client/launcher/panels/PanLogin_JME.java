/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import java.awt.GridLayout;
import com.jme3.system.JmeCanvasContext;
import amara.applications.master.client.launcher.loginscreen.LoginScreenApplication;
import amara.applications.master.client.launcher.loginscreen.screens.JMELoginScreen;

/**
 *
 * @author Carl
 */
public class PanLogin_JME extends PanLogin{
    
    public PanLogin_JME(JMELoginScreen loginScreen){
        this.loginScreen = loginScreen;
        setLayout(new GridLayout());
        loginScreenApplication = new LoginScreenApplication(this, loginScreen);
        loginScreenApplication.createCanvas();
        JmeCanvasContext jmeCanvasContext = (JmeCanvasContext) loginScreenApplication.getContext();
        jmeCanvasContext.setSystemListener(loginScreenApplication);
        add(jmeCanvasContext.getCanvas());
        loginScreenApplication.startCanvas();
        playBackgroundMusic(loginScreen.getBackgroundMusicPath());
    }
    private JMELoginScreen loginScreen;
    private LoginScreenApplication loginScreenApplication;

    @Override
    public void showIsLoading(boolean isLoading){
        
    }
}
