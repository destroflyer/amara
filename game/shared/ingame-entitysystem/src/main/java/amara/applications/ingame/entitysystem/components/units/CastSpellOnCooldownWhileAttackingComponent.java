/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CastSpellOnCooldownWhileAttackingComponent{

    public CastSpellOnCooldownWhileAttackingComponent(){
        
    }
    
    public CastSpellOnCooldownWhileAttackingComponent(int... spellIndices){
        this.spellIndices = spellIndices;
    }
    private int[] spellIndices;

    public int[] getSpellIndices(){
        return spellIndices;
    }
}
