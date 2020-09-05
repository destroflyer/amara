/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.physics;

import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class TransformOriginsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, TransformOriginComponent.class, PositionComponent.class, DirectionComponent.class);
        EntityComponentMapReadonly observerNew = observer.getNew();
        EntityComponentMapReadonly observerChanged = observer.getChanged();
        // New/Changed origins
        for (int buffAreaEntity : observerNew.getEntitiesWithAny(TransformOriginComponent.class)) {
            int originEntity = entityWorld.getComponent(buffAreaEntity, TransformOriginComponent.class).getOriginEntity();
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, entityWorld);
        }
        for (int buffAreaEntity : observerChanged.getEntitiesWithAny(TransformOriginComponent.class)) {
            int originEntity = entityWorld.getComponent(buffAreaEntity, TransformOriginComponent.class).getOriginEntity();
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, entityWorld);
        }
        // New/Changed transform of origins
        for (int buffAreaEntity : entityWorld.getEntitiesWithAny(TransformOriginComponent.class)) {
            int originEntity = entityWorld.getComponent(buffAreaEntity, TransformOriginComponent.class).getOriginEntity();
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, observerNew);
            tryTransformUpdate(entityWorld, buffAreaEntity, originEntity, observerChanged);
        }
    }

    private void tryTransformUpdate(EntityWorld entityWorld, int buffAreaEntity, int originEntity, EntityComponentMapReadonly entityComponentMapReadonly) {
        PositionComponent positionComponent = entityComponentMapReadonly.getComponent(originEntity, PositionComponent.class);
        if (positionComponent != null) {
            entityWorld.setComponent(buffAreaEntity, new PositionComponent(positionComponent.getPosition().clone()));
        }
        DirectionComponent directionComponent = entityComponentMapReadonly.getComponent(originEntity, DirectionComponent.class);
        if (directionComponent != null) {
            entityWorld.setComponent(buffAreaEntity, new DirectionComponent(directionComponent.getRadian()));
        }
    }
}
