/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

/**
 *
 * @author Carl
 */
public class CooldownComponent{

    public CooldownComponent(float duration){
        this.duration = duration;
    }    
    private float duration;

    public float getDuration(){
        return duration;
    }
}
