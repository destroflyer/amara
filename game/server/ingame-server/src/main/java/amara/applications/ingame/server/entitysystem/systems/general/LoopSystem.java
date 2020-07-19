package amara.applications.ingame.server.entitysystem.systems.general;

import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

import java.util.LinkedList;

public abstract class LoopSystem implements EntitySystem {

    private LinkedList<EntitySystem> entitySystems = new LinkedList<>();

    protected void add(EntitySystem entitySystem) {
        entitySystems.add(entitySystem);
    }

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        while (!isFinished(entityWorld)) {
            for (EntitySystem entitySystem : entitySystems) {
                entitySystem.update(entityWorld, deltaSeconds);
            }
        }
    }

    protected abstract boolean isFinished(EntityWorld entityWorld);
}
