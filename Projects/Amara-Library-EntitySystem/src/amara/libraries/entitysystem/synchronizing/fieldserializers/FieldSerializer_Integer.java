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
public class FieldSerializer_Integer implements FieldSerializer<Integer>{

    public FieldSerializer_Integer(int bits){
        this.bits = bits;
    }
    private int bits;

    @Override
    public void writeField(BitOutputStream bitOutputStream, Integer value){
        bitOutputStream.writeBits(value, bits);
    }

    @Override
    public Integer readField(BitInputStream bitInputStream) throws IOException{
        return bitInputStream.readBits(bits);
    }
}
