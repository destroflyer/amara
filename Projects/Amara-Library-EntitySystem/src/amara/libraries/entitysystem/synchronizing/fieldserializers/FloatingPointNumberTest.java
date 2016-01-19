/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing.fieldserializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import amara.libraries.entitysystem.synchronizing.FieldSerializer;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class FloatingPointNumberTest{
    
    
    public static void main(String[] args){
        double number = 567.82472;
        int exponentBits = 8;
        for(int mantisseBits=23;mantisseBits>=1;mantisseBits--){
            FieldSerializer serializer = new FieldSerializer_DoubleAsFloat(mantisseBits, exponentBits);
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
