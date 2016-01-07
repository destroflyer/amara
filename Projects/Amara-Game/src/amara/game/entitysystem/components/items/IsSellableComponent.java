/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class IsSellableComponent{

    public IsSellableComponent(){
        
    }

    public IsSellableComponent(int gold){
        this.gold = gold;
    }
    private int gold;

    public int getGold(){
        return gold;
    }
}
