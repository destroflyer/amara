/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.physics;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.*;

/**
 *
 * @author Philipp
 */
public final class MapIntersectionSystem implements EntitySystem
{
    private PolyMapManager map;

    public MapIntersectionSystem(PolyMapManager map)
    {
        this.map = map;
    }
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for (EntityWrapper entity : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(HitboxComponent.class, HitboxActiveComponent.class, CollisionGroupComponent.class, PositionComponent.class)))
        {
            CollisionGroupComponent filterComp = entity.getComponent(CollisionGroupComponent.class);
            if(CollisionGroupComponent.isColliding(filterComp.getTargetOf(), CollisionGroupComponent.MAP))
            {
                Shape shape = entity.getComponent(HitboxComponent.class).getShape();
                if(shape instanceof ConvexShape)
                {
                    ConvexShape convex = (ConvexShape)shape;
                    Vector2f pos = entity.getComponent(PositionComponent.class).getPosition();
                    Vector2D position = new Vector2D(pos.x, pos.y);
                    double radius = convex.getBoundCircle().getGlobalRadius();
                    Vector2D validPos = map.closestValid(position, radius);
                    if(!position.equals(validPos))
                    {
                        entity.setComponent(new PositionComponent(new Vector2f((float)validPos.getX(), (float)validPos.getY())));
                    }
                }
            }
        }
        for (EntityWrapper entity : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(HitboxComponent.class, HitboxActiveComponent.class, PositionComponent.class, RemoveOnMapLeaveComponent.class)))
        {
            Shape shape = entity.getComponent(HitboxComponent.class).getShape();
            if(shape instanceof ConvexShape)
            {
                ConvexShape convex = (ConvexShape)shape;
                Vector2f pos = entity.getComponent(PositionComponent.class).getPosition();
                Vector2D position = new Vector2D(pos.x, pos.y);
                double radius = convex.getBoundCircle().getGlobalRadius();
                if(map.outOfMapBounds(position, radius))
                {
                    entity.clearComponents();
                }
            }
        }
    }
}
