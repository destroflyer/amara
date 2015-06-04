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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AggroTargetComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AggroTargetComponent.class)){
            onAggroTargetChanged(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AggroTargetComponent.class)){
            onAggroTargetChanged(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void onAggroTargetChanged(EntityWorld entityWorld, int entity){
        CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
        if((campComponent != null) && entityWorld.hasComponent(campComponent.getCampEntity(), CampUnionAggroComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            for(int otherEntity : entityWorld.getEntitiesWithAll(CampComponent.class)){
                int otherCampEntity = entityWorld.getComponent(otherEntity, CampComponent.class).getCampEntity();
                if(otherCampEntity == campComponent.getCampEntity()){
                    AggroUtil.tryDrawAggro(entityWorld, otherEntity, targetEntity);
                }
            }
        }
    }
}