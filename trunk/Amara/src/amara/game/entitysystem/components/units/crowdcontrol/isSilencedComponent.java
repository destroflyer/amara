/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.crowdcontrol;

/**
 *
 * @author Carl
 */
public class IsSilencedComponent{

    public IsSilencedComponent(float remainingDuration){
        this.remainingDuration = remainingDuration;
    }
    private float remainingDuration;

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
