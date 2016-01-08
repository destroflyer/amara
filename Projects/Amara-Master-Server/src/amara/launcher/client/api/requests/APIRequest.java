/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.api.requests;

import amara.launcher.client.api.WebserverRequest;

/**
 *
 * @author Carl
 */
public class APIRequest extends WebserverRequest{
    
    public APIRequest(String query){
        super("api.php", query);
    }
}
