/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.maps;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class MapObjectiveComponent{

    public MapObjectiveComponent(){
        
    }

    public MapObjectiveComponent(int objectiveEntity){
        this.objectiveEntity = objectiveEntity;
    }
    private int objectiveEntity;

    public int getObjectiveEntity(){
        return objectiveEntity;
    }
}
