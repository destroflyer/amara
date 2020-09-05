package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersection.*;

public class TriggerCollisionEffectSystem implements EntitySystem {

    public TriggerCollisionEffectSystem(IntersectionInformant intersectionInformant) {
        this.intersectionInformant = intersectionInformant;
    }
    private IntersectionInformant intersectionInformant;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        IntersectionTracker<Pair<Integer>> tracker = intersectionInformant.getTracker(entityWorld, this);
        for (Pair<Integer> pair : tracker.getEntries()) {
            checkEffectTriggers(entityWorld, pair.getA(), pair.getB());
            checkEffectTriggers(entityWorld, pair.getB(), pair.getA());
        }
    }

    private void checkEffectTriggers(EntityWorld entityWorld, int effectingEntity, int targetEntity) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, CollisionTriggerComponent.class)) {
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                if (triggerSourceEntity == effectingEntity) {
                    boolean triggerEffect = true;
                    int targetRulesEntity = entityWorld.getComponent(effectTriggerEntity, CollisionTriggerComponent.class).getTargetRulesEntity();
                    if (targetRulesEntity != -1) {
                        triggerEffect = TargetUtil.isValidTarget(entityWorld, effectingEntity, targetEntity, targetRulesEntity);
                    }
                    if (triggerEffect) {
                        EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
                    }
                }
            }
        }
    }
}
