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
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
        if(playerCharacterComponent != null){
            TeamComponent teamComponent = entityWorld.getComponent(playerCharacterComponent.getEntity(), TeamComponent.class);
            if(teamComponent != null){
                playerTeamEntity = teamComponent.getTeamEntity();
            }
        }
    }
    
    public boolean isInitialized(){
        return (playerTeamEntity != -1);
    }

    public int getPlayerTeamEntity(){
        return playerTeamEntity;
    }
    
    public boolean isAllied(EntityWorld entityWorld, int entity){
        return isAllied(entityWorld.getComponent(entity, TeamComponent.class));
    }
    
    public boolean isAllied(TeamComponent teamComponent){
        if(teamComponent != null){
            return (teamComponent.getTeamEntity() == playerTeamEntity);
        }
        return false;
    }
}
