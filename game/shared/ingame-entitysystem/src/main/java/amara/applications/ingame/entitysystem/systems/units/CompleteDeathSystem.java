package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyRemoveBuffsSystem;
import amara.libraries.entitysystem.*;

public class CompleteDeathSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)) {
            if (!entityWorld.hasComponent(entity, IsRespawnableComponent.class)) {
                UnitUtil.cancelAction(entityWorld, entity);
                ApplyRemoveBuffsSystem.removeAllBuffs(entityWorld, entity);
                entityWorld.removeEntity(entity);
            }
        }
    }
}
