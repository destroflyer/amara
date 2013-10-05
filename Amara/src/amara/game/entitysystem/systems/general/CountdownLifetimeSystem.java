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
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(LifetimeComponent.class))
        {
            LifetimeComponent lifetimeComponent = entityWorld.getCurrent().getComponent(entity, LifetimeComponent.class);
            float duration = (lifetimeComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.getCurrent().setComponent(entity, new LifetimeComponent(duration));
            }
            else{
                entityWorld.removeEntity(entity);
            }
        }
    }
}
