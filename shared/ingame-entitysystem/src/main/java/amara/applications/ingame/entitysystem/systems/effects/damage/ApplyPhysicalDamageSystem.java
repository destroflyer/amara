package amara.applications.ingame.entitysystem.systems.effects.damage;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyPhysicalDamageSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            float physicalDamage = entityWorld.getComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class).getValue();
            if (!DamageUtil.dealDamage(entityWorld, targetEntity, physicalDamage)) {
                entityWorld.removeEntity(effectImpactEntity);
            }
        }
    }
}
