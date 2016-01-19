/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.information;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PlayerTeamSystem implements EntitySystem{
    
    public PlayerTeamSystem(int playerEntity){
        this.playerEntity = playerEntity;
    }
    private int playerEntity;
    private int playerTeamEntity = -1;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            TeamComponent teamComponent = entityWorld.getComponent(selectedUnitComponent.getEntity(), TeamComponent.class);
            if(teamComponent != null){
                playerTeamEntity = teamComponent.getTeamEntity();
            }
        }
    }
    
    public boolean isAllied(EntityWorld entityWorld, int entity){
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        if(teamComponent != null){
            return (teamComponent.getTeamEntity() == playerTeamEntity);
        }
        return false;
    }

    public int getTeamEntity(){
        return playerTeamEntity;
    }
}
