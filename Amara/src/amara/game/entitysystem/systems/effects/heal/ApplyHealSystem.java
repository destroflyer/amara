/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.heal;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.heals.*;

/**
 *
 * @author Carl
 */
public class ApplyHealSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, HealComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            HealthComponent healthComponent = entityWorld.getComponent(targetID, HealthComponent.class);
            if(healthComponent != null){
                HealComponent healComponent = entityWrapper.getComponent(HealComponent.class);
                float health = (healthComponent.getValue() + healComponent.getValue());
                entityWorld.setComponent(targetID, new HealthComponent(health));
            }
        }
    }
}
