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
public class OwnTeamVisionSystem implements EntitySystem{
    
    public OwnTeamVisionSystem(EntitySceneMap entitySceneMap, PlayerTeamSystem playerTeamSystem){
        this.entitySceneMap = entitySceneMap;
        this.playerTeamSystem = playerTeamSystem;
    }
    private EntitySceneMap entitySceneMap;
    private PlayerTeamSystem playerTeamSystem;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(playerTeamSystem.isInitialized()){
            ComponentMapObserver observer = entityWorld.requestObserver(this, IsVisibleForTeamsComponent.class);
            for(int entity : observer.getNew().getEntitiesWithAll(IsVisibleForTeamsComponent.class)){
                updateNode(entityWorld, entity);
            }
            for(int entity : observer.getChanged().getEntitiesWithAll(IsVisibleForTeamsComponent.class)){
                updateNode(entityWorld, entity);
            }
            for(int entity : observer.getRemoved().getEntitiesWithAll(IsVisibleForTeamsComponent.class)){
                updateNode(entity, true);
            }
        }
    }
    
    private void updateNode(EntityWorld entityWorld, int entity){
        updateNode(entity, isVisible(entityWorld, entity));
    }
    
    public boolean isVisible(EntityWorld entityWorld, int entity){
        boolean[] isVisibleForTeams = entityWorld.getComponent(entity, IsVisibleForTeamsComponent.class).isVisibleForTeams();
        return isVisibleForTeams[playerTeamSystem.getPlayerTeamEntity()];
    }
    
    private void updateNode(int entity, boolean isVisible){
        Node node = entitySceneMap.requestNode(entity);
        node.setCullHint(isVisible?Spatial.CullHint.Inherit:Spatial.CullHint.Always);
    }
}
