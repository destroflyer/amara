/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AddGoldComponent{

    public AddGoldComponent(){
        
    }

    public AddGoldComponent(int gold){
        this.gold = gold;
    }
    private int gold;

    public int getGold(){
        return gold;
    }
}
