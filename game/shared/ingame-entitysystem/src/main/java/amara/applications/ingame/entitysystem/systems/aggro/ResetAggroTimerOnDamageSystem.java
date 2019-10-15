/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ResetAggroTimerOnDamageSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)) {
            resetAggroTimer(entityWorld, effectImpactEntity);
        }
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)) {
            resetAggroTimer(entityWorld, effectImpactEntity);
        }
    }

    private void resetAggroTimer(EntityWorld entityWorld, int effectImpactEntity) {
        EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
        if (effectSourceComponent != null) {
            int damagingEntity = effectSourceComponent.getSourceEntity();
            if (entityWorld.hasComponent(damagingEntity, RemainingAggroResetDurationComponent.class)) {
                AggroResetTimersSystem.resetAggroTimer(entityWorld, damagingEntity);
            }
        }
    }
}
