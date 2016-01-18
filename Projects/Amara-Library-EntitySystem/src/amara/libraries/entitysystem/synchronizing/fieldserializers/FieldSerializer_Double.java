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
public class FieldSerializer_Double implements FieldSerializer<Double>{

    public FieldSerializer_Double(int mantissaBits, int exponentBits){
        this.mantissaBits = mantissaBits;
        this.exponentBits = exponentBits;
    }
    private static final int BITS_COUNT_TOTAL = 64;
    private static final int BITS_COUNT_MANTISSA = 52;
    private static final int BITS_COUNT_EXPONENT = 11;
    private int mantissaBits;
    private int exponentBits;

    @Override
    public void writeField(BitOutputStream bitOutputStream, Double value){
        long longBits = Double.doubleToLongBits(value);
        long sign = (longBits >>> (BITS_COUNT_TOTAL - 1));
        long mantissa = ((longBits << (1 + BITS_COUNT_EXPONENT)) >>> (1 + BITS_COUNT_EXPONENT + (BITS_COUNT_MANTISSA - mantissaBits)));
        long exponent = ((longBits << 1) >>> (1 + (BITS_COUNT_EXPONENT - exponentBits) + BITS_COUNT_MANTISSA));
        bitOutputStream.writeLongBits(sign, 1);
        bitOutputStream.writeLongBits(exponent, exponentBits);
        bitOutputStream.writeLongBits(mantissa, mantissaBits);
    }

    @Override
    public Double readField(BitInputStream bitInputStream) throws IOException{
        long sign = bitInputStream.readLongBits(1);
        long exponent = bitInputStream.readLongBits(exponentBits);
        long mantissa = bitInputStream.readLongBits(mantissaBits);
        long longBits = ((sign << (BITS_COUNT_TOTAL - 1)) | (exponent << (BITS_COUNT_MANTISSA + (BITS_COUNT_EXPONENT - exponentBits))) | (mantissa << (BITS_COUNT_MANTISSA - mantissaBits)));
        return Double.longBitsToDouble(longBits);
    }
}
