/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import java.awt.GridLayout;
import com.jme3.system.JmeCanvasContext;
import amara.applications.master.client.launcher.OggClip;
import amara.applications.master.client.launcher.loginscreen.LoginScreenApplication;
import amara.applications.master.client.launcher.loginscreen.screens.JMELoginScreen;
import amara.core.files.FileAssets;

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
        backgroundMusic = new OggClip(FileAssets.ROOT + loginScreen.getBackgroundMusicPath());
    }
    private JMELoginScreen loginScreen;
    private LoginScreenApplication loginScreenApplication;
    private OggClip backgroundMusic;

    @Override
    public void start(){
        super.start();
        if(backgroundMusic != null){
            backgroundMusic.setGain(0.75f);
            backgroundMusic.play();
        }
    }

    @Override
    public void close(){
        super.close();
        if(backgroundMusic != null){
            backgroundMusic.stop();
        }
    }
}
