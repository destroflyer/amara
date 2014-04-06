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
    
    public CastSpellComponent(int spellEntity, int castInformationEntity){
        this.spellEntity = spellEntity;
        this.castInformationEntity = castInformationEntity;
    }
    private int spellEntity;
    private int castInformationEntity;

    public int getSpellEntity(){
        return spellEntity;
    }

    public int getCastInformationEntity(){
        return castInformationEntity;
    }
}
