/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.api;

import java.net.URI;
import java.net.URL;

/**
 *
 * @author Carl
 */
public class WebserverInfo{
    
    public static final String CONNECTION_PROTOCOL = "http";
    public static final String HOST = "destroflyer.mania-community.de";
    public static final String PATH = "/amara/";
    public static final String REQUEST_PATH = "request/";
    
    public static URL getWebserverRequestURL(String path, String query){
        return getWebserverURL(REQUEST_PATH + path, query);
    }
    
    public static URL getWebserverURL(String path, String query){
        try{
            return new URI(CONNECTION_PROTOCOL, HOST, PATH + path, query, null).toURL();
        }catch(Exception ex){
            System.out.println("Error while creating webserver URL for path '" + path + "' with query '" + query + "'.");
        }
        return null;
    }
}
