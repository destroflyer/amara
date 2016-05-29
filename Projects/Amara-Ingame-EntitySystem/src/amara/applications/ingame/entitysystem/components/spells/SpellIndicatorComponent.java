/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellIndicatorComponent{

    public SpellIndicatorComponent(){
        
    }

    public SpellIndicatorComponent(int indicatorEntity){
        this.indicatorEntity = indicatorEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int indicatorEntity;

    public int getIndicatorEntity(){
        return indicatorEntity;
    }
}
