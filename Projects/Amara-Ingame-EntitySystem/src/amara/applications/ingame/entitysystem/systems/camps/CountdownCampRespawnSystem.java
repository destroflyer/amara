/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.camps;

import amara.applications.ingame.entitysystem.components.camps.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownCampRespawnSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(CampRemainingRespawnDurationComponent.class))){
            CampRemainingRespawnDurationComponent remainingRespawnDurationComponent = entityWrapper.getComponent(CampRemainingRespawnDurationComponent.class);
            float duration = (remainingRespawnDurationComponent.getDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new CampRemainingRespawnDurationComponent(duration));
            }
            else{
                entityWrapper.removeComponent(CampRemainingRespawnDurationComponent.class);
                entityWrapper.setComponent(new CampSpawnComponent());
            }
        }
    }
}
