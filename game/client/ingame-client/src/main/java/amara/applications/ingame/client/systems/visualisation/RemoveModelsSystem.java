/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.entitysystem.components.visuals.ModelComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class RemoveModelsSystem implements EntitySystem {

    public RemoveModelsSystem(EntitySceneMap entitySceneMap) {
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, ModelComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAll(ModelComponent.class)) {
            entitySceneMap.removeNode(entity);
        }
    }
}
