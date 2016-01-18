/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing.fieldserializers;

import java.io.IOException;
import amara.engine.network.BitInputStream;
import amara.engine.network.BitOutputStream;
import amara.libraries.entitysystem.synchronizing.FieldSerializer;

/**
 *
 * @author Carl
 */
public class FieldSerializer_Float implements FieldSerializer<Float>{

    public FieldSerializer_Float(int mantissaBits, int exponentBits){
        this.mantissaBits = mantissaBits;
        this.exponentBits = exponentBits;
    }
    private static final int BITS_COUNT_TOTAL = 32;
    private static final int BITS_COUNT_MANTISSA = 23;
    private static final int BITS_COUNT_EXPONENT = 8;
    private int mantissaBits;
    private int exponentBits;

    @Override
    public void writeField(BitOutputStream bitOutputStream, Float value){
        int intBits = Float.floatToIntBits(value);
        int sign = (intBits >>> (BITS_COUNT_TOTAL - 1));
        int mantissa = ((intBits << (1 + BITS_COUNT_EXPONENT)) >>> (1 + BITS_COUNT_EXPONENT + (BITS_COUNT_MANTISSA - mantissaBits)));
        int exponent = ((intBits << 1) >>> (1 + (BITS_COUNT_EXPONENT - exponentBits) + BITS_COUNT_MANTISSA));
        bitOutputStream.writeBits(sign, 1);
        bitOutputStream.writeBits(exponent, exponentBits);
        bitOutputStream.writeBits(mantissa, mantissaBits);
    }

    @Override
    public Float readField(BitInputStream bitInputStream) throws IOException{
        int sign = bitInputStream.readBits(1);
        int exponent = bitInputStream.readBits(exponentBits);
        int mantissa = bitInputStream.readBits(mantissaBits);
        int intBits = ((sign << (BITS_COUNT_TOTAL - 1)) | (exponent << (BITS_COUNT_MANTISSA + (BITS_COUNT_EXPONENT - exponentBits))) | (mantissa << (BITS_COUNT_MANTISSA - mantissaBits)));
        return Float.intBitsToFloat(intBits);
    }
}
