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
    
    private MP3Player backgroundMusicPlayer = new MP3Player();
    
    protected void playBackgroundMusic(String musicResourcePath){
        backgroundMusicPlayer.play(musicResourcePath);
    }
    
    public void login(String login, String password){
        MainFrame.getInstance().login(new AuthentificationInformation(login, password));
    }
    
    public abstract void showIsLoading(boolean isLoading);
    
    public void close(){
        backgroundMusicPlayer.stop();
    }
}
