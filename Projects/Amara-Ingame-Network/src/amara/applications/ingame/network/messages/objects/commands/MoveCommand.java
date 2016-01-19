/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class MoveCommand extends Command{

    public MoveCommand(){
        
    }
    
    public MoveCommand(Vector2f position){
        this.position = position;
    }
    private Vector2f position;

    public Vector2f getPosition(){
        return position;
    }
}
