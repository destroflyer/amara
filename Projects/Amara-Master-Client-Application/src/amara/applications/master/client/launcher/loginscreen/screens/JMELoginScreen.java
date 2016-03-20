/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.loginscreen.screens;

import amara.applications.master.client.launcher.loginscreen.LoginScreenApplication;
import de.lessvoid.nifty.tools.Color;

/**
 *
 * @author Carl
 */
public abstract class JMELoginScreen{
    
    protected String backgroundMusicPath;
    protected String loginBoxImagePath = "Interface/client/login/box.png";
    protected Color loginBoxTextColor = Color.WHITE;
    
    public abstract void initialize(LoginScreenApplication application);

    public String getBackgroundMusicPath(){
        return backgroundMusicPath;
    }

    public String getLoginBoxImagePath(){
        return loginBoxImagePath;
    }

    public Color getLoginBoxTextColor(){
        return loginBoxTextColor;
    }
}
