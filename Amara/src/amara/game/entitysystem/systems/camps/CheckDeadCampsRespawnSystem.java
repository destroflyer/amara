/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class CheckDeadCampsRespawnSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CampComponent.class);
        for(int deadEntity : observer.getRemoved().getEntitiesWithAll(CampComponent.class)){
            if(!entityWorld.hasEntity(deadEntity)){
                int campEntity = observer.getRemoved().getComponent(deadEntity, CampComponent.class).getCampEntity();
                if(entityWorld.hasComponent(campEntity, CampRespawnDurationComponent.class)){
                    boolean isCampDead = true;
                    for(Integer entity : entityWorld.getEntitiesWithAll(CampComponent.class)){
                        int aliveCampEntity = entityWorld.getComponent(entity, CampComponent.class).getCampEntity();
                        if(aliveCampEntity == campEntity){
                            isCampDead = false;
                            break;
                        }
                    }
                    if(isCampDead){
                        CampRespawnDurationComponent campRespawnDurationComponent = entityWorld.getComponent(campEntity, CampRespawnDurationComponent.class);
                        if(campRespawnDurationComponent != null){
                            entityWorld.setComponent(campEntity, new CampRemainingRespawnDurationComponent(campRespawnDurationComponent.getDuration()));
                        }
                        else{
                            entityWorld.setComponent(campEntity, new CampSpawnComponent());
                        }
                    }
                }
            }
        }
    }
}
