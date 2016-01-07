/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class TeamComponent{

    public TeamComponent(){
        
    }
    
    public TeamComponent(int teamEntity){
        this.teamEntity = teamEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int teamEntity;

    public int getTeamEntity(){
        return teamEntity;
    }
}
