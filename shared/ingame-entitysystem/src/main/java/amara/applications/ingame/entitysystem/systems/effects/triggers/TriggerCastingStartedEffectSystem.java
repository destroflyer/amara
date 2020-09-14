package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.input.CastSpellComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingStartedTriggerComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerCastingStartedEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, CastingStartedTriggerComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                for (int casterEntity : entityWorld.getEntitiesWithAny(CastSpellComponent.class)) {
                    if (casterEntity == sourceEntity) {
                        int targetEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getTargetEntity();
                        EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
                    }
                }
            }
        }
    }
}
