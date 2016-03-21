/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import javax.swing.JPanel;
import amara.applications.master.client.launcher.*;
import amara.applications.master.network.messages.objects.AuthentificationInformation;

/**
 *
 * @author Carl
 */
public abstract class PanLogin extends JPanel{
    
    public enum LoginState{
        INPUT,
        AUTHENTIFICATION,
        RECEIVING_DATA
    }
    protected LoginState loginState;
    
    public void start(){
        setLoginState(LoginState.INPUT);
    }

    public void setLoginState(LoginState loginState){
        this.loginState = loginState;
    }
    
    public void close(){
        
    }
    
    public void login(String login, String password){
        MainFrame.getInstance().login(new AuthentificationInformation(login, password));
    }
}
