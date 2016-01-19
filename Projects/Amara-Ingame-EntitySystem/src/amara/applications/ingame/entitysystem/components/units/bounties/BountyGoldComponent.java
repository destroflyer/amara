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
    
    public BountyGoldComponent(int gold){
        this.gold = gold;
    }
    private int gold;

    public int getGold(){
        return gold;
    }
}
