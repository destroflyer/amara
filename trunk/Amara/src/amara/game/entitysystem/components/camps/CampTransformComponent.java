/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.camps;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CampTransformComponent{

    public CampTransformComponent(){
        
    }

    public CampTransformComponent(Vector2f position, Vector2f direction){
        this.position = position;
        this.direction = direction;
    }
    private Vector2f position;
    private Vector2f direction;

    public Vector2f getPosition(){
        return position;
    }

    public Vector2f getDirection(){
        return direction;
    }
}
