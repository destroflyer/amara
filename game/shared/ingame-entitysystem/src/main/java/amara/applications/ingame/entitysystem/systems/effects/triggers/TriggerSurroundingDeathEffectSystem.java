package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.SurroundingDeathTriggerComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerSurroundingDeathEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, SurroundingDeathTriggerComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)){
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                PositionComponent sourcePositionComponent = entityWorld.getComponent(sourceEntity, PositionComponent.class);
                for (int targetEntity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)) {
                    PositionComponent targetPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);
                    if ((sourcePositionComponent != null) && (targetPositionComponent != null)) {
                        float distanceSquared = targetPositionComponent.getPosition().distanceSquared(sourcePositionComponent.getPosition());
                        float maximumDistance = entityWorld.getComponent(effectTriggerEntity, SurroundingDeathTriggerComponent.class).getMaximumDistance();
                        if (distanceSquared < (maximumDistance * maximumDistance)) {
                            EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
                        }
                    }
                }
            }
        }
    }
}
