/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.attributes;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class MaximumHealthSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Integer entity : entityWorld.getEntitiesWithAll(MaximumHealthComponent.class, HealthComponent.class))
        {
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
            if(health > maximumHealth){
                entityWorld.setComponent(entity, new HealthComponent(maximumHealth));
            }
        }
    }
}
