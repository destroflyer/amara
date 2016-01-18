/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing;

import java.io.IOException;
import amara.engine.network.BitInputStream;
import amara.engine.network.BitOutputStream;

/**
 *
 * @author Carl
 */
public interface FieldSerializer<T>{
    
    public void writeField(BitOutputStream bitOutputStream, T value);
    
    public T readField(BitInputStream bitInputStream) throws IOException;
}
