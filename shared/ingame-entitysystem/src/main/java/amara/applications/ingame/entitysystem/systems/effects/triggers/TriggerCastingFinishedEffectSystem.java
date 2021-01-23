package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.IsCastingComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerCastingFinishedEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsCastingComponent.class);
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, CastingFinishedTriggerComponent.class)){
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                IsCastingComponent removedIsCastingComponent = observer.getRemoved().getComponent(sourceEntity, IsCastingComponent.class);
                // Check if the component was removed because of no remaining cast duration (successfully finished) and not by cancelling the cast
                if ((removedIsCastingComponent != null) && (removedIsCastingComponent.getRemainingDuration() <= deltaSeconds)) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }
}
