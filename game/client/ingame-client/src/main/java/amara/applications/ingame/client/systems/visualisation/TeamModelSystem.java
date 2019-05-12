/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class TeamModelSystem implements EntitySystem{
    
    public TeamModelSystem(PlayerTeamSystem playerTeamSystem){
        this.playerTeamSystem = playerTeamSystem;
    }
    private PlayerTeamSystem playerTeamSystem;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(playerTeamSystem.isInitialized()){
            ComponentMapObserver observer = entityWorld.requestObserver(this, TeamComponent.class);
            for(int entity : entityWorld.getEntitiesWithAll(TeamModelComponent.class)){
                if(!checkTeamComponent(entityWorld, entity, observer.getNew().getComponent(entity, TeamComponent.class))){
                    if(!checkTeamComponent(entityWorld, entity, observer.getChanged().getComponent(entity, TeamComponent.class))){
                        if(observer.getRemoved().hasComponent(entity, TeamComponent.class)){
                            entityWorld.removeComponent(entity, ModelComponent.class);
                        }
                    }
                }
            }
        }
    }
    
    private boolean checkTeamComponent(EntityWorld entityWorld, int entity, TeamComponent teamComponent){
        if(teamComponent != null){
            String modelSkinPath = entityWorld.getComponent(entity, TeamModelComponent.class).getModelSkinPath();
            boolean isAllied = playerTeamSystem.isAllied(teamComponent);
            modelSkinPath = (modelSkinPath.substring(0, (modelSkinPath.length() - 4)) + "_" + (isAllied?"allied":"enemy") + ".xml");
            entityWorld.setComponent(entity, new ModelComponent(modelSkinPath));
            return true;
        }
        return false;
    }
}
