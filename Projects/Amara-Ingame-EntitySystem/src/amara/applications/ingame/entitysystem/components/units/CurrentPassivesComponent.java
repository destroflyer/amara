/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class CurrentPassivesComponent{

    public CurrentPassivesComponent(){
        
    }
    
    public CurrentPassivesComponent(int... passiveEntities){
        this.passiveEntities = passiveEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] passiveEntities;

    public int[] getPassiveEntities(){
        return passiveEntities;
    }
}
