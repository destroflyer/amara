/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.status;

/**
 *
 * @author Carl
 */
public class RemainingBuffDurationComponent{

    public RemainingBuffDurationComponent(float remainingDuration){
        this.remainingDuration = remainingDuration;
    }
    private float remainingDuration;

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
