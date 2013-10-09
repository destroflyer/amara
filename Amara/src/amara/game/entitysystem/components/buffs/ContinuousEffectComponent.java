/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

/**
 *
 * @author Carl
 */
public class ContinuousEffectComponent{

    public ContinuousEffectComponent(int effectEntityID){
        this.effectEntityID = effectEntityID;
    }
    private int effectEntityID;

    public int getEffectEntityID(){
        return effectEntityID;
    }
}
