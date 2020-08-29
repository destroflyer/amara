package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.BuffTargetsAmountTriggerComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerBuffTargetsAmountEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, BuffTargetsAmountTriggerComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                BuffTargetsAmountTriggerComponent buffTargetsAmountTriggerComponent = entityWorld.getComponent(effectTriggerEntity, BuffTargetsAmountTriggerComponent.class);
                int buffTargetsAmount = getBuffTargetsAmount(entityWorld, buffTargetsAmountTriggerComponent.getBuffEntity());
                if (buffTargetsAmount >= buffTargetsAmountTriggerComponent.getAmount()) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }

    private int getBuffTargetsAmount(EntityWorld entityWorld, int buffEntity) {
        int amount = 0;
        for (int buffStatusEntity : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)) {
            int currentBuffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
            if (currentBuffEntity == buffEntity) {
                amount++;
            }
        }
        return amount;
    }
}
