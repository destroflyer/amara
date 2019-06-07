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
public class TeamModelSystem implements EntitySystem {
    
    public TeamModelSystem(PlayerTeamSystem playerTeamSystem) {
        this.playerTeamSystem = playerTeamSystem;
    }
    private PlayerTeamSystem playerTeamSystem;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        if (playerTeamSystem.isInitialized()) {
            ComponentMapObserver observer = entityWorld.requestObserver(this, TeamComponent.class, TeamModelComponent.class);
            for (int entity : observer.getNew().getEntitiesWithAny(TeamComponent.class, TeamModelComponent.class)) {
                updateModel(entityWorld, entity);
            }
            for (int entity : observer.getChanged().getEntitiesWithAny(TeamComponent.class, TeamModelComponent.class)) {
                updateModel(entityWorld, entity);
            }
            for (int entity : observer.getRemoved().getEntitiesWithAny(TeamComponent.class, TeamModelComponent.class)) {
                updateModel(entityWorld, entity);
            }
            for (int entity : observer.getRemoved().getEntitiesWithAll(TeamComponent.class)) {
                if (!entityWorld.hasComponent(entity, TeamModelComponent.class)) {
                    entityWorld.removeComponent(entity, ModelComponent.class);
                }
            }
            for (int entity : observer.getRemoved().getEntitiesWithAll(TeamModelComponent.class)) {
                entityWorld.removeComponent(entity, ModelComponent.class);
            }
        }
    }

    private void updateModel(EntityWorld entityWorld, int entity) {
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        TeamModelComponent teamModelComponent = entityWorld.getComponent(entity, TeamModelComponent.class);
        if ((teamComponent != null) && (teamModelComponent != null)) {
            String modelSkinPath = teamModelComponent.getModelSkinPath();
            boolean isAllied = playerTeamSystem.isAllied(teamComponent);
            modelSkinPath = (modelSkinPath.substring(0, (modelSkinPath.length() - 4)) + "_" + (isAllied ? "allied" : "enemy") + ".xml");
            entityWorld.setComponent(entity, new ModelComponent(modelSkinPath));
        }
    }
}
