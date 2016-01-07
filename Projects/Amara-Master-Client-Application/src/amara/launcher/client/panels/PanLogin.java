/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.panels;

import javax.swing.JPanel;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.launcher.client.*;

/**
 *
 * @author Carl
 */
public abstract class PanLogin extends JPanel{
    
    private OggClip backgroundMusicClip;
    
    protected void playBackgroundMusic(String musicResourcePath){
        backgroundMusicClip = new OggClip(musicResourcePath);
        backgroundMusicClip.setGain(0.75f);
        backgroundMusicClip.play();
    }
    
    public void login(String login, String password){
        MainFrame.getInstance().login(new AuthentificationInformation(login, password));
    }
    
    public abstract void showIsLoading(boolean isLoading);
    
    public void start(){
        
    }
    
    public void close(){
        if(backgroundMusicClip != null){
            backgroundMusicClip.stop();
        }
    }
}
