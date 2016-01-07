/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import java.io.IOException;
import amara.engine.network.*;

/**
 *
 * @author Carl
 */
public class NewComponentChange extends EntityChange{

    public NewComponentChange(){
        
    }
    
    public NewComponentChange(int entity, Object component){
        super(entity);
        this.component = component;
    }
    private Object component;

    public Object getComponent(){
        return component;
    }

    @Override
    public void write(BitOutputStream outputStream){
        super.write(outputStream);
        ComponentSerializer.writeClassAndObject(outputStream, component);
    }

    @Override
    public void read(BitInputStream inputStream) throws IOException{
        super.read(inputStream);
        component = ComponentSerializer.readClassAndObject(inputStream);
    }
}
