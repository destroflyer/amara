package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.AggroTargetComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.HasAggroTargetTriggerComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.HasNoAggroTargetTriggerComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityComponentMapReadonly;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerAggroTargetEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        triggerAggroTargetEffectTriggers(entityWorld, HasAggroTargetTriggerComponent.class, observer.getNew());
        triggerAggroTargetEffectTriggers(entityWorld, HasNoAggroTargetTriggerComponent.class, observer.getRemoved());
    }

    private void triggerAggroTargetEffectTriggers(EntityWorld entityWorld, Class<?> aggroTargetTriggerComponentClass, EntityComponentMapReadonly entityComponentMap) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, aggroTargetTriggerComponentClass)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                AggroTargetComponent aggroTargetComponent = entityComponentMap.getComponent(sourceEntity, AggroTargetComponent.class);
                if (aggroTargetComponent != null) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, aggroTargetComponent.getTargetEntity());
                }
            }
        }
    }
}
