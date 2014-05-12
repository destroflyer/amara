/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class CheckCampAggroTargetSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AggroTargetComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            if(entityWorld.hasComponent(entity, CampComponent.class)){
                entityWorld.setComponent(entity, new ResetCampComponent());
            }
        }
        observer.reset();
    }
}
