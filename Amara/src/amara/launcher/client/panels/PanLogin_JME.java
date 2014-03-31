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
        setLayout(new GridLayout());
        loginScreenApplication = new LoginScreenApplication(this, loginScreen);
        loginScreenApplication.createCanvas();
        JmeCanvasContext jmeCanvasContext = (JmeCanvasContext) loginScreenApplication.getContext();
        jmeCanvasContext.setSystemListener(loginScreenApplication);
        add(jmeCanvasContext.getCanvas());
        loginScreenApplication.startCanvas();
        final String backgroundMusicPath = loginScreen.getBackgroundMusicPath();
        if(backgroundMusicPath != null){
            loginScreenApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    playBackgroundMusic("/" + backgroundMusicPath);
                }
            });
        }
    }
    private LoginScreenApplication loginScreenApplication;

    @Override
    public void showIsLoading(boolean isLoading){
        
    }
}
