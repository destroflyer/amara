/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs.areas;

import amara.game.entitysystem.components.buffs.areas.*;
import amara.game.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateAreaTransformsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AreaOriginComponent.class, PositionComponent.class);
        EntityComponentMapReadonly observerNew = observer.getNew();
        EntityComponentMapReadonly observerChanged = observer.getChanged();
        for(int buffAreaEntity : observerNew.getEntitiesWithAll(AreaOriginComponent.class)){
            int originEntity = entityWorld.getComponent(buffAreaEntity, AreaOriginComponent.class).getOriginEntity();
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, entityWorld);
        }
        for(int buffAreaEntity : entityWorld.getEntitiesWithAll(AreaOriginComponent.class)){
            int originEntity = entityWorld.getComponent(buffAreaEntity, AreaOriginComponent.class).getOriginEntity();
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, observerNew);
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, observerChanged);
        }
    }
    
    private void tryTransformUpdate(EntityWorld entityWorld, int buffAreaEntity, int originEntity, EntityComponentMapReadonly entityComponentMapReadonly){
        PositionComponent positionComponent = entityComponentMapReadonly.getComponent(originEntity, PositionComponent.class);
        if(positionComponent != null){
            entityWorld.setComponent(buffAreaEntity, new PositionComponent(positionComponent.getPosition().clone()));
        }
        DirectionComponent directionComponent = entityComponentMapReadonly.getComponent(originEntity, DirectionComponent.class);
        if(directionComponent != null){
            entityWorld.setComponent(buffAreaEntity, new DirectionComponent(directionComponent.getRadian()));
        }
    }
}
