/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.api.requests;

import amara.applications.master.client.launcher.api.WebserverRequest;

/**
 *
 * @author Carl
 */
public class APIRequest extends WebserverRequest{
    
    public APIRequest(String query){
        super("api.php", query);
    }
}
