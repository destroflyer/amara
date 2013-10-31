/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.exceptions;

/**
 *
 * @author carl
 */
public class ServerConnectionTimeoutException extends Exception{

    public ServerConnectionTimeoutException(String host, int port){
        super("Timeout while connecting to \"" + host + "\" at port " + port + ".");
    }
}
