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
public class LinearIndicatorComponent{

    public LinearIndicatorComponent(){
        
    }

    public LinearIndicatorComponent(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float x;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float y;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float width;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float height;

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
}
