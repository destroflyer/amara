package amara.applications.ingame.entitysystem.systems.general;

import amara.applications.ingame.entitysystem.components.units.LifetimeComponent;
import amara.applications.ingame.entitysystem.systems.units.CompleteDeathSystem;
import amara.libraries.entitysystem.*;

public class CountdownLifetimeSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(LifetimeComponent.class)) {
            LifetimeComponent lifetimeComponent = entityWorld.getComponent(entity, LifetimeComponent.class);
            float duration = (lifetimeComponent.getRemainingDuration() - deltaSeconds);
            if (duration > 0) {
                entityWorld.setComponent(entity, new LifetimeComponent(duration));
            } else {
                CompleteDeathSystem.killCompletely(entityWorld, entity);
            }
        }
    }
}
