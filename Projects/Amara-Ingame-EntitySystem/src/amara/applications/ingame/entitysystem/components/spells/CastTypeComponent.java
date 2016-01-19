/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CastTypeComponent{

    public CastTypeComponent(){
        
    }
    
    public CastTypeComponent(CastType castType){
        this.castType = castType;
    }
    public enum CastType{
        SELFCAST,
        SINGLE_TARGET,
        LINEAR_SKILLSHOT,
        POSITIONAL_SKILLSHOT
    }
    private CastType castType;

    public CastType getCastType(){
        return castType;
    }
}
