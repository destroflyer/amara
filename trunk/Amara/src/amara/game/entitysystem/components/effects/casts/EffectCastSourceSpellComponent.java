/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.casts;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class EffectCastSourceSpellComponent{

    public EffectCastSourceSpellComponent(){
        
    }
    
    public EffectCastSourceSpellComponent(int spellEntity){
        this.spellEntity = spellEntity;
    }
    private int spellEntity;

    public int getSpellEntity(){
        return spellEntity;
    }
}
