/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing.fieldserializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import amara.engine.network.BitInputStream;
import amara.engine.network.BitOutputStream;
import amara.game.entitysystem.synchronizing.FieldSerializer;

/**
 *
 * @author Carl
 */
public class FloatingPointNumberTest{
    
    
    public static void main(String[] args){
        double number = 567.82472f;
        int exponentBits = 8;
        for(int mantisseBits=52;mantisseBits>=1;mantisseBits--){
            FieldSerializer serializer = new FieldSerializer_Double(mantisseBits, exponentBits);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
            serializer.writeField(bitOutputStream, number);
            bitOutputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            BitInputStream bitInputStream = new BitInputStream(new ByteArrayInputStream(data));
            try{
                System.out.println(mantisseBits + "\t" + (1 + mantisseBits + exponentBits) + "bit = " + serializer.readField(bitInputStream));
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
