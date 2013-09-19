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
public class DeathSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(HealthComponent.class))
        {
            float health = entityWorld.getCurrent().getComponent(entity, HealthComponent.class).getValue();
            if(health < 1){
                entityWorld.removeEntity(entity);
            }
        }
    }
}
