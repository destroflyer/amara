package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerInstantEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(InstantTriggerComponent.class, TriggerSourceComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
            }
        }
    }
}
