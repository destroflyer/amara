/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing;

import java.io.IOException;
import amara.engine.network.*;

/**
 *
 * @author Carl
 */
public class EntityChange implements BitSerializable{

    public EntityChange(){
        
    }

    protected EntityChange(int entity){
        this.entity = entity;
    }
    private int entity;

    public int getEntity(){
        return entity;
    }

    @Override
    public void write(BitOutputStream outputStream){
        outputStream.writeInteger(entity);
    }

    @Override
    public void read(BitInputStream inputStream) throws IOException{
        entity = inputStream.readInteger();
    }
}
