/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CastSpellOnCooldownWhileAttackingComponent{

    public CastSpellOnCooldownWhileAttackingComponent(){
        
    }
    
    public CastSpellOnCooldownWhileAttackingComponent(int spellIndex){
        this.spellIndex = spellIndex;
    }
    private int spellIndex;

    public int getSpellIndex(){
        return spellIndex;
    }
}
