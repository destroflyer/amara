/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class HealthRegenerationSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(HealthComponent.class, HealthRegenerationComponent.class)){
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            float healthRegeneration = entityWorld.getComponent(entity, HealthRegenerationComponent.class).getValue();
            entityWorld.setComponent(entity, new HealthComponent(health + (healthRegeneration * deltaSeconds)));
        }
    }
}
