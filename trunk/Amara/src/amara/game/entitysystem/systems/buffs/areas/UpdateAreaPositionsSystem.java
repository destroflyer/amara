/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs.areas;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.areas.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class UpdateAreaPositionsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AreaOriginComponent.class, PositionComponent.class);
        for(int buffAreaEntity : observer.getNew().getEntitiesWithAll(AreaOriginComponent.class)){
            int originEntity = entityWorld.getComponent(buffAreaEntity, AreaOriginComponent.class).getOriginEntity();
            tryPositionUpdate(entityWorld, buffAreaEntity, entityWorld.getComponent(originEntity, PositionComponent.class));
        }
        for(int buffAreaEntity : entityWorld.getEntitiesWithAll(AreaOriginComponent.class)){
            int originEntity = entityWorld.getComponent(buffAreaEntity, AreaOriginComponent.class).getOriginEntity();
            tryPositionUpdate(entityWorld, buffAreaEntity, observer.getNew().getComponent(originEntity, PositionComponent.class));
            tryPositionUpdate(entityWorld, buffAreaEntity, observer.getChanged().getComponent(originEntity, PositionComponent.class));
        }
        observer.reset();
    }
    
    private void tryPositionUpdate(EntityWorld entityWorld, int buffAreaEntity, PositionComponent positionComponent){
        if(positionComponent != null){
            entityWorld.setComponent(buffAreaEntity, new PositionComponent(positionComponent.getPosition().clone()));
        }
    }
}
