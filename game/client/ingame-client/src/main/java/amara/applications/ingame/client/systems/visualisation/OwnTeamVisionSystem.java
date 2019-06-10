/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class OwnTeamVisionSystem implements EntitySystem {

    public OwnTeamVisionSystem(EntitySceneMap entitySceneMap, PlayerTeamSystem playerTeamSystem) {
        this.entitySceneMap = entitySceneMap;
        this.playerTeamSystem = playerTeamSystem;
    }
    private EntitySceneMap entitySceneMap;
    private PlayerTeamSystem playerTeamSystem;
    private boolean isEnabled = true;
    private boolean updateAll;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if (playerTeamSystem.isInitialized()) {
            if (updateAll) {
                if (isEnabled) {
                    for(int entity : entityWorld.getEntitiesWithAny(IsVisibleForTeamsComponent.class)){
                        updateNode(entityWorld, entity);
                    }
                } else {
                    for(int entity : entityWorld.getEntitiesWithAny(IsVisibleForTeamsComponent.class)){
                        updateNode(entity, true);
                    }
                }
                updateAll = false;
            }
            if (isEnabled) {
                ComponentMapObserver observer = entityWorld.requestObserver(this, IsVisibleForTeamsComponent.class);
                for (int entity : observer.getNew().getEntitiesWithAny(IsVisibleForTeamsComponent.class)) {
                    updateNode(entityWorld, entity);
                }
                for (int entity : observer.getChanged().getEntitiesWithAny(IsVisibleForTeamsComponent.class)) {
                    updateNode(entityWorld, entity);
                }
                for (int entity : observer.getRemoved().getEntitiesWithAny(IsVisibleForTeamsComponent.class)) {
                    updateNode(entity, true);
                }
            }
        }
    }

    public void setEnabled(boolean enabled){
        if (enabled != isEnabled) {
            this.isEnabled = enabled;
            updateAll = true;
        }
    }

    private void updateNode(EntityWorld entityWorld, int entity){
        updateNode(entity, isVisible(entityWorld, entity));
    }

    public boolean isVisible(EntityWorld entityWorld, int entity) {
        if (isEnabled) {
            if (playerTeamSystem.isInitialized()) {
                IsVisibleForTeamsComponent isVisibleForTeamsComponent = entityWorld.getComponent(entity, IsVisibleForTeamsComponent.class);
                if (isVisibleForTeamsComponent != null) {
                    boolean[] isVisibleForTeams = isVisibleForTeamsComponent.isVisibleForTeams();
                    return isVisibleForTeams[playerTeamSystem.getPlayerTeamEntity()];
                }
            }
            return false;
        }
        return true;
    }

    private void updateNode(int entity, boolean isVisible) {
        Node node = entitySceneMap.requestNode(entity);
        node.setCullHint(isVisible?Spatial.CullHint.Inherit:Spatial.CullHint.Always);
    }
}
