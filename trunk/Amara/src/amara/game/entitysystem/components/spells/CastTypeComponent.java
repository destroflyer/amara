/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

/**
 *
 * @author Carl
 */
public class CastTypeComponent{

    public CastTypeComponent(CastType castType){
        this.castType = castType;
    }
    public enum CastType{
        SINGLE_TARGET,
        SKILLSHOT
    }
    private CastType castType;

    public CastType getCastType(){
        return castType;
    }
}
