/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import javax.swing.JPanel;
import amara.applications.master.client.launcher.*;
import amara.applications.master.network.messages.objects.AuthentificationInformation;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public abstract class PanLogin extends JPanel{
    
    private OggClip backgroundMusicClip;
    
    protected void playBackgroundMusic(String musicFilePath){
        backgroundMusicClip = new OggClip(FileAssets.ROOT + musicFilePath);
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
