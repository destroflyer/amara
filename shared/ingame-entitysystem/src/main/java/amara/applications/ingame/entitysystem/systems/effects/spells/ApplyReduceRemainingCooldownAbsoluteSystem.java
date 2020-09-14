package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.spells.ReduceRemainingCooldownAbsoluteComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyReduceRemainingCooldownAbsoluteSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReduceRemainingCooldownAbsoluteComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(targetEntity, RemainingCooldownComponent.class);
            if (remainingCooldownComponent != null) {
                float currentDuration = remainingCooldownComponent.getDuration();
                float newDuration = entityWorld.getComponent(effectImpactEntity, ReduceRemainingCooldownAbsoluteComponent.class).getDuration();
                if (newDuration < currentDuration) {
                    entityWorld.setComponent(targetEntity, new RemainingCooldownComponent(newDuration));
                }
            }
        }
    }
}
