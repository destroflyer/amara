/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.shop;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ShopRangeComponent{

    public ShopRangeComponent(){
        
    }
    
    public ShopRangeComponent(float range){
        this.range = range;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float range;

    public float getRange(){
        return range;
    }
}
