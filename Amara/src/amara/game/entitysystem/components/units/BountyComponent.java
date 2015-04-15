/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BountyComponent{

    public BountyComponent(){
        
    }
    
    public BountyComponent(int gold){
        this.gold = gold;
    }
    private int gold;

    public int getGold(){
        return gold;
    }
}
