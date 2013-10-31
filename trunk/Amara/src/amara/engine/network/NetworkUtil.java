/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 *
 * @author Carl
 */
public class NetworkUtil{
    
    public static boolean isPortAvailable(int port){
        ServerSocket serverSocket = null;
        DatagramSocket datagramSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            datagramSocket = new DatagramSocket(port);
            datagramSocket.setReuseAddress(true);
            return true;
        }catch(Exception ex){
        }finally{
            if(datagramSocket != null){
                datagramSocket.close();
            }
            if(serverSocket != null){
                try{
                    serverSocket.close();
                }catch(Exception ex){
                }
            }
        }
        return false;
    }
}
