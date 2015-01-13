/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.objectives;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class OrObjectivesComponent{

    public OrObjectivesComponent(){
        
    }

    public OrObjectivesComponent(int... objectiveEntities){
        this.objectiveEntities = objectiveEntities;
    }
    private int[] objectiveEntities;

    public int[] getObjectiveEntities(){
        return objectiveEntities;
    }
}
