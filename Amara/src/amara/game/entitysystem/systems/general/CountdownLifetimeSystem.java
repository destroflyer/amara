/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.general;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.LifetimeComponent;

/**
 *
 * @author Carl
 */
public class CountdownLifetimeSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Integer entity : entityWorld.getEntitiesWithAll(LifetimeComponent.class))
        {
            LifetimeComponent lifetimeComponent = entityWorld.getComponent(entity, LifetimeComponent.class);
            float duration = (lifetimeComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.setComponent(entity, new LifetimeComponent(duration));
            }
            else{
                entityWorld.removeEntity(entity);
            }
        }
    }
}
