package amara.applications.ingame.entitysystem.systems.physics;

import amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent;
import amara.applications.ingame.entitysystem.components.physics.HitboxComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.physics.RemoveOnMapLeaveComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.ConvexShape;
import amara.libraries.physics.shapes.Shape;
import amara.libraries.physics.shapes.Vector2D;
import com.jme3.math.Vector2f;

public final class RemoveOnMapLeaveSystem implements EntitySystem {

    public RemoveOnMapLeaveSystem(PolyMapManager polyMapManager) {
        this.polyMapManager = polyMapManager;
    }
    private PolyMapManager polyMapManager;

    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(HitboxComponent.class, HitboxActiveComponent.class, PositionComponent.class, RemoveOnMapLeaveComponent.class)) {
            Shape shape = entityWorld.getComponent(entity, HitboxComponent.class).getShape();
            if (shape instanceof ConvexShape) {
                ConvexShape convex = (ConvexShape)shape;
                Vector2f position2f = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2D position2D = new Vector2D(position2f.getX(), position2f.getY());
                double radius = convex.getBoundCircle().getGlobalRadius();
                if (polyMapManager.outOfMapBounds(position2D, radius)) {
                    entityWorld.removeEntity(entity);
                }
            }
        }
    }
}
