/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import com.jme3.math.Vector2f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public final class MapIntersectionSystem implements EntitySystem
{
    private MapGrid<MapObstacle> mapGrid;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private int cellSize = 4;

    public MapIntersectionSystem(int mapWidth, int mapHeight, List<Shape> shapes)
    {
        this(mapWidth, mapHeight);
         AddObstacles(shapes);
    }
    public MapIntersectionSystem(int mapWidth, int mapHeight)
    {
         mapGrid = new MapGrid(mapWidth / cellSize + 1, mapHeight / cellSize + 1, cellSize);
    }
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for (EntityWrapper entity : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(HitboxComponent.class, CollisionGroupComponent.class)))
        {
            CollisionGroupComponent filterComp = entity.getComponent(CollisionGroupComponent.class);
            if(CollisionGroupComponent.groupsCollide(CollisionGroupComponent.COLLISION_GROUP_MAP, filterComp.getCollidesWithGroups()))
            {
                solveIntersections(entity);
            }
        }
    }
    
    private void solveIntersections(EntityWrapper entity)
    {
        Shape shape = entity.getComponent(HitboxComponent.class).getShape().clone();
        List<MapObstacle> obstacles = mapGrid.getAllIntersectionPartners(shape);
        if(obstacles.isEmpty()) return;
        Vector2D newPosition = new Vector2D();
        Vector2f position = entity.getComponent(PositionComponent.class).getPosition();
        if(position != null)
        {
            newPosition.add(position.x, position.y);
        }
        for (MapObstacle obstacle : obstacles)
        {
            newPosition.add(shape.getResolveVector(obstacle.getShape()));
        }
        entity.setComponent(new PositionComponent(new Vector2f((float)newPosition.getX(), (float)newPosition.getY())));
    }
    
    public void AddObstacles(List<Shape> shapes)
    {
        for (Shape shape : shapes)
        {
            mapGrid.addObstacle(new MapObstacle(shape));
        }
        this.shapes.addAll(shapes);
    }
    
    public ArrayList<Shape> getObstacles()
    {
        return shapes;
    }
}
