/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import java.util.HashSet;

/**
 *
 * @author Philipp
 */
public class TransformUpdater
{
    public boolean updateTransforms(EntityWorld entityWorld)
    {
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, PositionComponent.class, DirectionComponent.class, ScaleComponent.class);
        if(observer.isEmpty()) return false;
        HashSet<Integer> updateNeeded = new HashSet<Integer>();
        for(int entity: observer.getRemoved().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: observer.getChanged().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: observer.getNew().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: observer.getChanged().getEntitiesWithAny(HitboxComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: observer.getNew().getEntitiesWithAny(HitboxComponent.class))
        {
            updateNeeded.add(entity);
        }
        for(int entity: updateNeeded)
        {
            updateTransforms(entityWorld.getWrapped(entity));
        }
        observer.reset();
        return true;
    }
    
    private void updateTransforms(EntityWrapper entity)
    {
        HitboxComponent hitbox = entity.getComponent(HitboxComponent.class);
        if(hitbox == null) return;
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        DirectionComponent dir = entity.getComponent(DirectionComponent.class);
        ScaleComponent scale = entity.getComponent(ScaleComponent.class);
        Shape shape = hitbox.getShape();
        Transform transform = shape.getTransform();
        if(pos != null)
        {
            transform.setPosition(pos.getPosition().x, pos.getPosition().y);
        }
        else
        {
            transform.setPosition(0, 0);
        }
        if(dir != null)
        {
            transform.setRadian(dir.getRadian());
        }
        else
        {
            transform.setRadian(0);
        }
        if(scale != null)
        {
            transform.setScale(scale.getScale());
        }
        else
        {
            transform.setScale(1);
        }
        entity.setComponent(new HitboxComponent(shape));
    }
}
