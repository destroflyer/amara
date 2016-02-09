/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class IsSellableComponent{

    public IsSellableComponent(){
        
    }

    public IsSellableComponent(float gold){
        this.gold = gold;
    }
    private float gold;

    public float getGold(){
        return gold;
    }
}
