/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network.exceptions;

/**
 *
 * @author carl
 */
public class ServerConnectionException extends Exception{
    
    public ServerConnectionException(String host, int port){
        super("Connection to the server \"" + host + "\" at port \"" + port + "\" failed.");
    }
}
