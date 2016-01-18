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
public class FieldSerializer_DoubleAsFloat implements FieldSerializer<Double>{

    public FieldSerializer_DoubleAsFloat(int mantissaBits, int exponentBits){
        floatSerializer = new FieldSerializer_Float(mantissaBits, exponentBits);
    }
    private FieldSerializer_Float floatSerializer;

    @Override
    public void writeField(BitOutputStream bitOutputStream, Double value){
        floatSerializer.writeField(bitOutputStream, value.floatValue());
    }

    @Override
    public Double readField(BitInputStream bitInputStream) throws IOException{
        return floatSerializer.readField(bitInputStream).doubleValue();
    }
}
