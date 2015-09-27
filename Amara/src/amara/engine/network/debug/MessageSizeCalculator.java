/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.debug;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.jme3.network.Message;
import com.jme3.network.serializing.Serializer;

/**
 *
 * @author Carl
 */
public class MessageSizeCalculator{
    
    private static ByteBuffer byteBuffer;
    
    public static long getMessageSize(Message message){
        if(byteBuffer == null){
            byteBuffer = ByteBuffer.allocate(50*1024*1024);
        }
        try{
            byteBuffer.rewind();
            Serializer.writeClassAndObject(byteBuffer, message);
            return byteBuffer.position();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return -1;
    }
}
