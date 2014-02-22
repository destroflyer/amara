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
public class MissingEntitiesComponent{

    public MissingEntitiesComponent(){
        
    }

    public MissingEntitiesComponent(int[] entities){
        this.entities = entities;
    }
    private int[] entities;

    public int[] getEntities(){
        return entities;
    }
}
