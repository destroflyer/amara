/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class LifestealSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)){
            EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
            EffectSourceSpellComponent effectSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceSpellComponent.class);
            if((effectSourceComponent != null) && (effectSourceSpellComponent != null)){
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
