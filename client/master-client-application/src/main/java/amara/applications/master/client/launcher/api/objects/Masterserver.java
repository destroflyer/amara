/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.api.objects;

/**
 *
 * @author Carl
 */
public class Masterserver{

    public Masterserver(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    private String ip;
    private int port;

    public String getIP(){
        return ip;
    }

    public int getPort(){
        return port;
    }
}
