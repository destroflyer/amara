/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.bounties;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BountyGoldComponent{

    public BountyGoldComponent(){
        
    }
    
    public BountyGoldComponent(float gold){
        this.gold = gold;
    }
    private float gold;

    public float getGold(){
        return gold;
    }
}
