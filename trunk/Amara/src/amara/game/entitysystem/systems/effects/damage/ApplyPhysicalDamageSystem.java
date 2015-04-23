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
public class ApplyPhysicalDamageSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PhysicalDamageComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            boolean wasDamaged = false;
            if(entityWorld.hasComponent(targetID, IsVulnerableComponent.class)){
                HealthComponent healthComponent = entityWorld.getComponent(targetID, HealthComponent.class);
                if(healthComponent != null){
                    float armor = 0;
                    ArmorComponent armorComponent = entityWorld.getComponent(targetID, ArmorComponent.class);
                    if(armorComponent != null){
                        armor = armorComponent.getValue();
                    }
                    float damageFactor;
                    if(armor >= 0){
                        damageFactor = (100 / (100 + armor));
                    }
                    else{
                        damageFactor = (2 - (100 / (100 - armor)));
                    }
                    float damage = (damageFactor * entityWrapper.getComponent(PhysicalDamageComponent.class).getValue());
                    if(damage > 0){
                        float health = (healthComponent.getValue() - damage);
                        entityWorld.setComponent(targetID, new HealthComponent(health));
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
