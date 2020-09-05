/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.input;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class CastSpellComponent{

    public CastSpellComponent(){
        
    }
    
    public CastSpellComponent(int spellEntity, int targetEntity){
        this.spellEntity = spellEntity;
        this.targetEntity = targetEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEntity;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;

    public int getSpellEntity(){
        return spellEntity;
    }

    public int getTargetEntity(){
        return targetEntity;
    }
}
