/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import intersections.*;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import shapes.*;

/**
 *
 * @author Philipp
 */
public class InsersectionSystem implements EntitySystem
{
    private ConcurrentHashMap<Integer, Hitbox> hitboxMap = new ConcurrentHashMap<Integer, Hitbox>();
    private SweepAndPrune<Hitbox> sweepAndPrune = new SweepAndPrune<Hitbox>();
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        updateHitboxes(entityWorld);
        updateHitboxTransforms(entityWorld);
        updateIntersections(entityWorld);
    }
    
    private void updateHitboxes(EntityWorld entityWorld)
    {
        for(int entity: entityWorld.getNew().getEntitiesWithAny(HitboxComponent.class))
        {
            addHitboxLocal(entityWorld, entity);
        }
        for(int entity: entityWorld.getChanged().getEntitiesWithAny(HitboxComponent.class))
        {
            updateHitboxLocal(entityWorld, entity);
        }
        for(int entity: entityWorld.getRemoved().getEntitiesWithAny(HitboxComponent.class))
        {
            removeHitboxLocal(entityWorld, entity);
        }
    }
    
    private void updateHitboxTransforms(EntityWorld entityWorld)
    {
        HashSet<Integer> updateNeeded = new HashSet<Integer>();
        updateNeeded.addAll(entityWorld.getNew().getEntitiesWithAny(PositionComponent.class, ScaleComponent.class, DirectionComponent.class));
        updateNeeded.addAll(entityWorld.getChanged().getEntitiesWithAny(PositionComponent.class, ScaleComponent.class, DirectionComponent.class));
        updateNeeded.addAll(entityWorld.getRemoved().getEntitiesWithAny(PositionComponent.class, ScaleComponent.class, DirectionComponent.class));
        
        updateNeeded.retainAll(entityWorld.getEntities(HitboxComponent.class));
        for(int entity: updateNeeded)
        {
            updateHitboxTransformLocal(entityWorld, entity);
        }
    }
    
    private void updateIntersections(EntityWorld entityWorld)
    {
        HashSet<Pair<Hitbox>> intersectionPairs = sweepAndPrune.getAllPairs();
        //TODO
    }
    
    private void removeHitboxLocal(EntityWorld entityWorld, int entity)
    {
        sweepAndPrune.remove(hitboxMap.remove(entity));
    }
    private void updateHitboxLocal(EntityWorld entityWorld, int entity)
    {
        removeHitboxLocal(entityWorld, entity);
        addHitboxLocal(entityWorld, entity);
    }
    private void addHitboxLocal(EntityWorld entityWorld, int entity)
    {
        Shape shape = new Circle(1);
        Hitbox hitbox = new Hitbox(entity, shape);
        hitboxMap.put(entity, hitbox);
        updateHitboxTransformLocal(entityWorld, entity);
        sweepAndPrune.add(hitbox);
    }
    
    private void updateHitboxTransformLocal(EntityWorld entityWorld, int entity)
    {
        float radian = 0;
        float scale = 1;
        float x = 0;
        float y = 0;
        
        PositionComponent positionComponent = entityWorld.getCurrent().getComponent(entity, PositionComponent.class);
        if(positionComponent != null)
        {
            x = positionComponent.getPosition().x;
            y = positionComponent.getPosition().y;
        }
        
        DirectionComponent directionComponent = entityWorld.getCurrent().getComponent(entity, DirectionComponent.class);
        if(directionComponent != null)
        {
            radian = directionComponent.getRadian();
        }
        
        ScaleComponent scaleComponent = entityWorld.getCurrent().getComponent(entity, ScaleComponent.class);
        if(scaleComponent != null)
        {
            radian = scaleComponent.getScale();
        }
        
        hitboxMap.get(entity).getShape().getTransform().setPosition(x, y);
        hitboxMap.get(entity).getShape().getTransform().setRadian(radian);
        hitboxMap.get(entity).getShape().getTransform().setScale(scale);
    }
}
