/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.attributes;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DeathSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(HealthComponent.class, IsAliveComponent.class)){
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            if(health < 1){
                entityWorld.setComponent(entity, new HealthComponent(0));
                entityWorld.removeComponent(entity, IsAliveComponent.class);
            }
        }
    }
}
