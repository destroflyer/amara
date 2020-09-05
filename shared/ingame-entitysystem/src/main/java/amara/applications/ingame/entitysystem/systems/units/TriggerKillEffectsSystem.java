package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.KillTriggerComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerKillEffectsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int deadEntity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)) {
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(deadEntity, DamageHistoryComponent.class);
            if (damageHistoryComponent != null) {
                int effectActionIndex = -1;
                for (int i = (damageHistoryComponent.getEntries().length - 1); i >= 0; i--) {
                    DamageHistoryComponent.DamageHistoryEntry damageHistoryEntry = damageHistoryComponent.getEntries()[i];
                    if (effectActionIndex == -1) {
                        effectActionIndex = damageHistoryEntry.getSourceActionIndex();
                    }
                    // Check the last damage history entry and all other entries of the same action (e.g. an autoattack and an added effect)
                    if (damageHistoryEntry.getSourceActionIndex() == effectActionIndex) {
                        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(KillTriggerComponent.class, TriggerSourceComponent.class)) {
                            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                            if ((triggerSourceEntity == damageHistoryEntry.getSourceEntity()) || (triggerSourceEntity == damageHistoryEntry.getSourceSpellEntity())) {
                                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, deadEntity);
                            }
                        }
                    }
                }
            }
        }
    }
}
