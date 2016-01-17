/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.encoding;

import java.nio.ByteBuffer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Carl
 */
public class AES_Encoder extends Encoder{

    public AES_Encoder(long keyPart1, long keyPart2){
        byte[] keyBytes = ByteBuffer.allocate(16).putLong(keyPart1).putLong(keyPart2).array();
        secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM_NAME);
    }
    private static final String ALGORITHM_NAME = "AES";
    private SecretKeySpec secretKeySpec;
    
    @Override
    public String encode(String text){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            BASE64Encoder myEncoder = new BASE64Encoder();
            return myEncoder.encode(encrypted);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String decode(String text){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            BASE64Decoder myDecoder = new BASE64Decoder();
            byte[] encrypted = myDecoder.decodeBuffer(text);
            byte[] textBytes = cipher.doFinal(encrypted);
            return new String(textBytes);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args){
        AES_Encoder encoder = new AES_Encoder(42, 999);
        String text = "this is a test";
        System.out.println("TEXT: " + text);
        String encodedText = encoder.encode(text);
        System.out.println("ENCODE: " + encodedText);
        System.out.println("DECODE: " + encoder.decode(encodedText));
    }
}
