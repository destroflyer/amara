/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.damage;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ApplyMagicDamageSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            boolean wasDamaged = false;
            if(entityWorld.hasComponent(targetEntity, IsVulnerableComponent.class)){
                HealthComponent healthComponent = entityWorld.getComponent(targetEntity, HealthComponent.class);
                if(healthComponent != null){
                    float damage = entityWrapper.getComponent(ResultingMagicDamageComponent.class).getValue();
                    if(damage > 0){
                        float health = (healthComponent.getValue() - damage);
                        entityWorld.setComponent(targetEntity, new HealthComponent(health));
                        wasDamaged = true;
                    }
                }
            }
            if(!wasDamaged){
                entityWorld.removeEntity(entityWrapper.getId());
            }
        }
    }
}
