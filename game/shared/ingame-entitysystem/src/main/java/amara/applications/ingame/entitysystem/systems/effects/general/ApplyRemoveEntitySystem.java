package amara.applications.ingame.entitysystem.systems.effects.general;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.systems.units.CompleteDeathSystem;
import amara.libraries.entitysystem.*;

public class ApplyRemoveEntitySystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveEntityComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            CompleteDeathSystem.killCompletely(entityWorld, targetEntity);
        }
    }
}
