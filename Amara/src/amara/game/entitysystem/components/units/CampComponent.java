/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class CampComponent{

    public CampComponent(){
        
    }

    public CampComponent(int campEntity, Vector2f position, Vector2f direction){
        this.campEntity = campEntity;
        this.position = position;
        this.direction = direction;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int campEntity;
    private Vector2f position;
    private Vector2f direction;

    public Vector2f getPosition(){
        return position;
    }

    public Vector2f getDirection(){
        return direction;
    }

    public int getCampEntity(){
        return campEntity;
    }
}
