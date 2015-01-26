/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.api.requests;

/**
 *
 * @author Carl
 */
public class RegisterMasterserverRequest extends APIRequest{
    
    public RegisterMasterserverRequest(int port){
        super("mode=registerMasterserver&port=" + port);
    }
}
