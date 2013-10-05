/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.status;

/**
 *
 * @author Carl
 */
public class TimeSinceLastRepeatingEffectComponent{

    public TimeSinceLastRepeatingEffectComponent(float duration){
        this.duration = duration;
    }
    private float duration;

    public float getDuration(){
        return duration;
    }
}
