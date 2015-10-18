/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 *
 * @author Carl
 */
public class NetworkUtil{
    
    public static byte[] writeToBytes(BitSerializable bitSerializable){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
        bitSerializable.write(bitOutputStream);
        bitOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    public static void readFromBytes(BitSerializable bitSerializable, byte[] bytes){
        BitInputStream bitInputStream = new BitInputStream(new ByteArrayInputStream(bytes));
        try{
            bitSerializable.read(bitInputStream);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
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
