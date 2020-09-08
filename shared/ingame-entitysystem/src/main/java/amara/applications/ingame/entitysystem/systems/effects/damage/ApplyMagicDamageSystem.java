package amara.applications.ingame.entitysystem.systems.effects.damage;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyMagicDamageSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            float magicDamage = entityWorld.getComponent(effectImpactEntity, ResultingMagicDamageComponent.class).getValue();
            if (!DamageUtil.dealDamage(entityWorld, targetEntity, magicDamage)) {
                entityWorld.removeEntity(effectImpactEntity);
            }
        }
    }
}
