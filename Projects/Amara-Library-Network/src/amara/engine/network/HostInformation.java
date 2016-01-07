/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

/**
 *
 * @author Carl
 */
public class HostInformation{

    public HostInformation(String host, int port){
        this.host = host;
        this.port = port;
    }
    private String host;
    private int port;

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }
}
