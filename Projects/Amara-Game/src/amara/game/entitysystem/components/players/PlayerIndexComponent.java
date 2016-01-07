/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.players;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayerIndexComponent{

    public PlayerIndexComponent(){
        
    }

    public PlayerIndexComponent(int index){
        this.index = index;
    }
    private int index;

    public int getIndex(){
        return index;
    }
}
