package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.SpellvampComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent;
import amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class SpellvampSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)){
            EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
            if (effectSourceComponent != null) {
                int sourceEntity = effectSourceComponent.getSourceEntity();
                boolean isNonAutoAttack = true;
                EffectSourceSpellComponent effectSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceSpellComponent.class);
                if (effectSourceSpellComponent != null) {
                    AutoAttackComponent autoAttackComponent = entityWorld.getComponent(sourceEntity, AutoAttackComponent.class);
                    isNonAutoAttack = ((autoAttackComponent == null) || (autoAttackComponent.getAutoAttackEntity() != effectSourceSpellComponent.getSpellEntity()));
                }
                if (isNonAutoAttack) {
                    SpellvampComponent spellvampComponent = entityWorld.getComponent(sourceEntity, SpellvampComponent.class);
                    if ((spellvampComponent != null) && (spellvampComponent.getValue() > 0)) {
                        float magicDamage = entityWorld.getComponent(effectImpactEntity, ResultingMagicDamageComponent.class).getValue();
                        float healedAmount = (spellvampComponent.getValue() * magicDamage);
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
