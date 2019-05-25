/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.effecttriggers.targets;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TeamTargetComponent {

    public TeamTargetComponent(){

    }

    public TeamTargetComponent(int teamEntity){
        this.teamEntity = teamEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int teamEntity;

    public int getTeamEntity() {
        return teamEntity;
    }
}
