/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class PassivesComponent{

    public PassivesComponent(){
        
    }
    
    public PassivesComponent(int... passiveEntities){
        this.passiveEntities = passiveEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] passiveEntities;

    public int[] getPassiveEntities(){
        return passiveEntities;
    }
}
