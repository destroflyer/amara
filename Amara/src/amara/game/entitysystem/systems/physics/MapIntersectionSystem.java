/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import com.jme3.math.Vector2f;

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
            if(CollisionGroupComponent.groupsCollide(CollisionGroupComponent.COLLISION_GROUP_MAP, filterComp.getCollidesWithGroups()))
            {
                Vector2f pos = entity.getComponent(PositionComponent.class).getPosition();
                Point2D position = new Point2D(pos.x, pos.y);
                double radius = entity.getComponent(HitboxComponent.class).getShape().getBoundRadius();
                Point2D validPos = map.closestValid(position, radius);
                if(!position.equals(validPos))
                {
                    entity.setComponent(new PositionComponent(new Vector2f((float)validPos.getX(), (float)validPos.getY())));
                }
            }
        }
        for (EntityWrapper entity : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(HitboxComponent.class, HitboxActiveComponent.class, PositionComponent.class, RemoveOnMapLeaveComponent.class)))
        {
            Vector2f pos = entity.getComponent(PositionComponent.class).getPosition();
            Point2D position = new Point2D(pos.x, pos.y);
                double radius = entity.getComponent(HitboxComponent.class).getShape().getBoundRadius();
            if(map.outOfMapBounds(position, radius))
            {
                entity.clearComponents();
            }
        }
    }
}
