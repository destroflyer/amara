/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.camps;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckLostAggroCampsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            if(entityWorld.hasComponent(entity, CampComponent.class) && (!entityWorld.hasComponent(entity, CampResetComponent.class))){
                entityWorld.setComponent(entity, new CampResetComponent());
            }
        }
    }
}
