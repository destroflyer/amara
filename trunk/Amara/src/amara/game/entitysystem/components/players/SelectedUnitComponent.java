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
public class SelectedUnitComponent{

    public SelectedUnitComponent(){
        
    }

    public SelectedUnitComponent(int entity){
        this.entity = entity;
    }
    private int entity;

    public int getEntity(){
        return entity;
    }
}
