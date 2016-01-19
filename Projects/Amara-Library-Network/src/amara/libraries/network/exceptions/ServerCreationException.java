/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network.exceptions;

/**
 *
 * @author carl
 */
public class ServerCreationException extends Exception{

    public ServerCreationException(int port){
        super("No server at port '" + port + "' could be created. Please check your router, if this port is opened for TCP/UDP.");
        this.port = port;
    }
    private int port;

    public int getPort(){
        return port;
    }
}
