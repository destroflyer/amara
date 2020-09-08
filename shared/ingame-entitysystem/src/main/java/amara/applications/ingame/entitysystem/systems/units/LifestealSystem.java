package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.LifestealComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent;
import amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class LifestealSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)){
            EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
            EffectSourceSpellComponent effectSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceSpellComponent.class);
            if ((effectSourceComponent != null) && (effectSourceSpellComponent != null)) {
                int sourceEntity = effectSourceComponent.getSourceEntity();
                AutoAttackComponent autoAttackComponent = entityWorld.getComponent(sourceEntity, AutoAttackComponent.class);
                if ((autoAttackComponent != null) && (autoAttackComponent.getAutoAttackEntity() == effectSourceSpellComponent.getSpellEntity())) {
                    LifestealComponent lifestealComponent = entityWorld.getComponent(sourceEntity, LifestealComponent.class);
                    if ((lifestealComponent != null) && (lifestealComponent.getValue() > 0)) {
                        float physicalDamage = entityWorld.getComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class).getValue();
                        float healedAmount = (lifestealComponent.getValue() * physicalDamage);
                        HealthComponent healthComponent = entityWorld.getComponent(sourceEntity, HealthComponent.class);
                        if (healthComponent != null) {
                            entityWorld.setComponent(sourceEntity, new HealthComponent(healthComponent.getValue() + healedAmount));
                        }
                    }
                }
            }
        }
    }
}
