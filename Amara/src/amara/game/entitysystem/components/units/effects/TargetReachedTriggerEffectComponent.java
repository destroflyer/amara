/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.effects;

/**
 *
 * @author Carl
 */
public class TargetReachedTriggerEffectComponent{

    public TargetReachedTriggerEffectComponent(int effectEntityID){
        this.effectEntityID = effectEntityID;
    }
    private int effectEntityID;

    public int getEffectEntityID(){
        return effectEntityID;
    }
}
