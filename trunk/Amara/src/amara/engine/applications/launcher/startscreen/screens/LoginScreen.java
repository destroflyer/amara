/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.launcher.startscreen.screens;

import amara.engine.applications.launcher.startscreen.LoginScreenApplication;
import de.lessvoid.nifty.tools.Color;

/**
 *
 * @author Carl
 */
public abstract class LoginScreen{
    
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
