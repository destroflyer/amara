/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells.indicators;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class CircularIndicatorComponent{

    public CircularIndicatorComponent(){
        
    }

    public CircularIndicatorComponent(float x, float y, float radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float x;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float y;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float radius;

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
    
    public float getRadius(){
        return radius;
    }
}
