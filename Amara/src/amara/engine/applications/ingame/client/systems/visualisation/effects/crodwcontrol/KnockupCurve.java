/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.effects.crodwcontrol;

import com.jme3.math.FastMath;
import amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent;

/**
 *
 * @author Carl
 */
public class KnockupCurve{

    public KnockupCurve(Integer entity, float duration, float height, float offset){
        this.entity = entity;
        this.duration = duration;
        this.height = height;
        this.offset = offset;
        this.remainingDuration = duration;
    }
    private Integer entity;
    private float duration;
    private float height;
    private float offset;
    private float remainingDuration;
    private IsKnockupedComponent isKnockupedComponent;
    
    public float getCurrentHeight(){
        float progress = getProgress();
        return (height - FastMath.pow(FastMath.sqrt(height) * 2 * (progress - 0.5f), 2)) + ((1 - progress) * offset);
    }

    public float getProgress(){
        return (1 - (remainingDuration / duration));
    }
    
    public void onTimePassed(float deltaSeconds){
        remainingDuration -= deltaSeconds;
    }
    
    public boolean isFinished(){
        return (remainingDuration <= 0);
    }

    public int getEntity(){
        return entity;
    }

    public void setIsKnockupedComponent(IsKnockupedComponent isKnockupedComponent){
        this.isKnockupedComponent = isKnockupedComponent;
    }

    public IsKnockupedComponent getIsKnockupedComponent(){
        return isKnockupedComponent;
    }
}
