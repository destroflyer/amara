package amara.applications.ingame.client.systems.visualisation;

import amara.libraries.entitysystem.EntityObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class DetachEntityNodesSystem implements EntitySystem {

    public DetachEntityNodesSystem(EntitySceneMap entitySceneMap) {
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        EntityObserver entityObserver = entityWorld.getOrCreateEntityObserver(this);
        entityObserver.reset();
        for (int entity : entityObserver.RemovedEntities()) {
            entitySceneMap.removeNode(entity);
        }
    }
}
