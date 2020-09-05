/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellPassivesComponent {

    public SpellPassivesComponent(){

    }

    public SpellPassivesComponent(int... passiveEntities){
        this.passiveEntities = passiveEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] passiveEntities;

    public int[] getPassiveEntities(){
        return passiveEntities;
    }
}
