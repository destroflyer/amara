/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ApplyCastedSpellComponent{

    public ApplyCastedSpellComponent(){
        
    }
    
    public ApplyCastedSpellComponent(int casterEntityID, int castedSpellEntityID){
        this.casterEntityID = casterEntityID;
        this.castedSpellEntityID = castedSpellEntityID;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int casterEntityID;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int castedSpellEntityID;

    public int getCasterEntityID(){
        return casterEntityID;
    }

    public int getCastedSpellEntityID(){
        return castedSpellEntityID;
    }
}
