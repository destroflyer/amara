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
public class CastSelfcastSpellComponent{

    public CastSelfcastSpellComponent(){
        
    }
    
    public CastSelfcastSpellComponent(int spellEntityID){
        this.spellEntityID = spellEntityID;
    }
    private int spellEntityID;

    public int getSpellEntityID(){
        return spellEntityID;
    }
}
