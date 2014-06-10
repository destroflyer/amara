/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.input;

import com.jme3.network.serializing.Serializable;

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
    private int spellEntity;
    private int targetEntity;

    public int getSpellEntity(){
        return spellEntity;
    }

    public int getTargetEntity(){
        return targetEntity;
    }
}
