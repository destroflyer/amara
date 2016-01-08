/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network;

import java.util.ArrayList;

/**
 *
 * @author Carl
 */
public class PortProvider{

    public PortProvider(int initialPort){
        this.initialPort = initialPort;
    }
    private int initialPort;
    private ArrayList<Integer> ports = new ArrayList<Integer>();
    
    public int requestPort(){
        int port = initialPort;
        while(ports.contains(port)){
            port++;
        }
        ports.add(port);
        return port;
    }
    
    public void removePort(int port){
        ports.remove(port);
    }
}
