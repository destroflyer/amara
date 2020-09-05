package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.NoBuffTargetsTriggerComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerNoBuffTargetsEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, NoBuffTargetsTriggerComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int[] buffEntities = entityWorld.getComponent(effectTriggerEntity, NoBuffTargetsTriggerComponent.class).getBuffEntities();
                if (areNoBuffTargetsExisting(entityWorld, buffEntities)) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }

    private boolean areNoBuffTargetsExisting(EntityWorld entityWorld, int[] buffEntities) {
        for (int buffStatusEntity : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)) {
            int exitingTargetBuffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
            for (int buffEntity : buffEntities) {
                if (exitingTargetBuffEntity == buffEntity) {
                    return false;
                }
            }
        }
        return true;
    }
}
