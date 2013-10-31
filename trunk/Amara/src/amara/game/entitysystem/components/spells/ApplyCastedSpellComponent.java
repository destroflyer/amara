/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

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
    private int casterEntityID;
    private int castedSpellEntityID;

    public int getCasterEntityID(){
        return casterEntityID;
    }

    public int getCastedSpellEntityID(){
        return castedSpellEntityID;
    }
}
