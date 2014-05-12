/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CampComponent{

    public CampComponent(){
        
    }
    
    public CampComponent(int campEntity){
        this.campEntity = campEntity;
    }
    private int campEntity;

    public int getCampEntity(){
        return campEntity;
    }
}
