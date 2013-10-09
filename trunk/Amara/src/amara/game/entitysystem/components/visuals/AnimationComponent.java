/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.visuals;

/**
 *
 * @author Carl
 */
public class AnimationComponent{

    public AnimationComponent(String name, float loopDuration, boolean isLooped){
        this.name = name;
        this.loopDuration = loopDuration;
        this.isLooped = isLooped;
    }
    private String name;
    private float loopDuration;
    private boolean isLooped;

    public String getName(){
        return name;
    }

    public float getLoopDuration(){
        return loopDuration;
    }

    public boolean isLooped(){
        return isLooped;
    }
}
