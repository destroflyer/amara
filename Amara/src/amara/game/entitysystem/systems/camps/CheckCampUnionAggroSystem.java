/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.aggro.AggroUtil;

/**
 *
 * @author Carl
 */
public class CheckCampUnionAggroSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(AggroTargetComponent.class)){
            onAggroTargetChanged(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(AggroTargetComponent.class)){
            onAggroTargetChanged(entityWorld, entity);
        }
    }
    
    private void onAggroTargetChanged(EntityWorld entityWorld, Integer entity){
        CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
        if((campComponent != null) && entityWorld.hasComponent(campComponent.getCampEntity(), CampUnionAggroComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            for(Integer otherEntity : entityWorld.getEntitiesWithAll(CampComponent.class)){
                int otherCampEntity = entityWorld.getComponent(otherEntity, CampComponent.class).getCampEntity();
                if(otherCampEntity == campComponent.getCampEntity()){
                    AggroUtil.tryDrawAggro(entityWorld, otherEntity, targetEntity);
                }
            }
        }
    }
}
