/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.encoding;

import java.nio.ByteBuffer;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Carl
 */
public class AES_Encoder extends Encoder{

    public AES_Encoder(long keyPart1, long keyPart2, long ivPart1, long ivPart2){
        byte[] keyBytes = ByteBuffer.allocate(16).putLong(keyPart1).putLong(keyPart2).array();
        byte[] ivBytes = ByteBuffer.allocate(16).putLong(ivPart1).putLong(ivPart2).array();
        secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM_NAME);
        ivParameterSpec = new IvParameterSpec(ivBytes);
        try{
            cipher = Cipher.getInstance(ALGORITHM_NAME + ALGORITHM_MODE);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static final String ALGORITHM_NAME = "AES";
    private static final String ALGORITHM_MODE = "/CBC/PKCS5Padding";
    private SecretKeySpec secretKeySpec;
    private IvParameterSpec ivParameterSpec;
    private Cipher cipher;
    
    @Override
    public String encode(String text){
        try{
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String decode(String text){
        try{
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = Base64.getDecoder().decode(text);
            byte[] textBytes = cipher.doFinal(encrypted);
            return new String(textBytes);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args){
        AES_Encoder encoder = new AES_Encoder(42, 999, 0, 0);
        String text = "this is a test";
        System.out.println("TEXT: " + text);
        String encodedText = encoder.encode(text);
        System.out.println("ENCODE: " + encodedText);
        System.out.println("DECODE: " + encoder.decode(encodedText));
    }
}
