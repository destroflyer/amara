/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.synchronizing;

import java.io.IOException;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class RemovedComponentChange extends EntityChange{

    public RemovedComponentChange(){
        
    }
    
    public RemovedComponentChange(int entity, Class componentClass){
        super(entity);
        this.componentClass = componentClass;
    }
    private Class componentClass;

    public Class getComponentClass(){
        return componentClass;
    }

    @Override
    public void write(BitOutputStream outputStream){
        super.write(outputStream);
        ComponentSerializer.writeClass(outputStream, componentClass);
    }

    @Override
    public void read(BitInputStream inputStream) throws IOException{
        super.read(inputStream);
        componentClass = ComponentSerializer.readClass(inputStream);
    }
}
