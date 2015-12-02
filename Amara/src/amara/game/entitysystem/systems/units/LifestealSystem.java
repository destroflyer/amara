/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class LifestealSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PhysicalDamageComponent.class)){
            EffectCastSourceComponent effectCastSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceComponent.class);
            EffectCastSourceSpellComponent effectCastSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceSpellComponent.class);
            if((effectCastSourceComponent != null) && (effectCastSourceSpellComponent != null)){
                int sourceEntity = effectCastSourceComponent.getSourceEntity();
                int autoAttackEntity = entityWorld.getComponent(sourceEntity, AutoAttackComponent.class).getAutoAttackEntity();
                if(effectCastSourceSpellComponent.getSpellEntity() == autoAttackEntity){
                    LifestealComponent lifestealComponent = entityWorld.getComponent(sourceEntity, LifestealComponent.class);
                    if((lifestealComponent != null) && (lifestealComponent.getValue() > 0)){
                        float physicalDamage = entityWorld.getComponent(effectImpactEntity, PhysicalDamageComponent.class).getValue();
                        float healedAmount = (lifestealComponent.getValue() * physicalDamage);
                        HealthComponent healthComponent = entityWorld.getComponent(sourceEntity, HealthComponent.class);
                        if(healthComponent != null){
                            entityWorld.setComponent(sourceEntity, new HealthComponent(healthComponent.getValue() + healedAmount));
                        }
                    }
                }
            }
        }
    }
}
