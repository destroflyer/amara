/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

/**
 *
 * @author Carl
 */
public class RepeatingEffectComponent{

    public RepeatingEffectComponent(int effectEntityID, float interval){
        this.effectEntityID = effectEntityID;
        this.interval = interval;
    }
    private int effectEntityID;
    private float interval;

    public int getEffectEntityID(){
        return effectEntityID;
    }

    public float getInterval(){
        return interval;
    }
}
