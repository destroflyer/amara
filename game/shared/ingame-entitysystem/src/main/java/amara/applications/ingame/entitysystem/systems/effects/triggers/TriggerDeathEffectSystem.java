package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

public class TriggerDeathEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, DeathTriggerComponent.class)){
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                if (observer.getRemoved().hasEntity(sourceEntity)) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }
}
