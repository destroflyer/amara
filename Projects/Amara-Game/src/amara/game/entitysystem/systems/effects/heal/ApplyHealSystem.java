/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.heal;

import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyHealSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingHealComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            HealthComponent healthComponent = entityWorld.getComponent(targetID, HealthComponent.class);
            if(healthComponent != null){
                ResultingHealComponent resultingHealComponent = entityWrapper.getComponent(ResultingHealComponent.class);
                float health = (healthComponent.getValue() + resultingHealComponent.getValue());
                entityWorld.setComponent(targetID, new HealthComponent(health));
            }
        }
    }
}
