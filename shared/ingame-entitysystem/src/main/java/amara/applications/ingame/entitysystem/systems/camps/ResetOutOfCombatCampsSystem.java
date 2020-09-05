/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.camps;

import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ResetOutOfCombatCampsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CampInCombatComponent.class);
        for(int campEntity : observer.getRemoved().getEntitiesWithAny(CampInCombatComponent.class)){
            for(int entity : entityWorld.getEntitiesWithAny(CampComponent.class)){
                CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
                if(campComponent.getCampEntity() == campEntity){
                    entityWorld.setComponent(entity, new CampResetComponent());
                }
            }
        }
    }
}
