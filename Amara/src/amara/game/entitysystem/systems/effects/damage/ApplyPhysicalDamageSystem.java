/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.damage;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.damage.*;

/**
 *
 * @author Carl
 */
public class ApplyPhysicalDamageSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(ApplyEffectImpactComponent.class, PhysicalDamageComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            HealthComponent healthComponent = entityWorld.getCurrent().getComponent(targetID, HealthComponent.class);
            if(healthComponent != null){
                PhysicalDamageComponent physicalDamageComponent = entityWrapper.getComponent(PhysicalDamageComponent.class);
                float health = (healthComponent.getValue() - physicalDamageComponent.getValue());
                entityWorld.getCurrent().setComponent(targetID, new HealthComponent(health));
            }
        }
    }
}
