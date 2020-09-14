package amara.applications.ingame.entitysystem.systems.effects.units;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.units.DisableAutoAttackComponent;
import amara.applications.ingame.entitysystem.components.units.IsAutoAttackEnabledComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyDisableAutoAttackSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, DisableAutoAttackComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.removeComponent(targetEntity, IsAutoAttackEnabledComponent.class);
        }
    }
}
