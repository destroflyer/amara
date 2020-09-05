package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerStacksReachedEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, StacksReachedTriggerComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int buffEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                for (int buffStatusEntity : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class, StacksComponent.class)) {
                    ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                    if (activeBuffComponent.getBuffEntity() == buffEntity) {
                        int currentStacks = entityWorld.getComponent(buffStatusEntity, StacksComponent.class).getStacks();
                        int minimumStacks = entityWorld.getComponent(effectTriggerEntity, StacksReachedTriggerComponent.class).getStacks();
                        if (currentStacks >= minimumStacks) {
                            EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, activeBuffComponent.getTargetEntity());
                        }
                    }
                }
            }
        }
    }
}
