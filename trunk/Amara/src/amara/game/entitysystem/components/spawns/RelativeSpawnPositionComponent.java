/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class RelativeSpawnPositionComponent{
    
    public RelativeSpawnPositionComponent(Vector2f position){
        this.position = position;
    }
    private Vector2f position;

    public Vector2f getPosition(){
        return position;
    }
}