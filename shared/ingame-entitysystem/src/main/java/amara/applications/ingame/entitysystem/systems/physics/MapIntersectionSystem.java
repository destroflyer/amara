package amara.applications.ingame.entitysystem.systems.physics;

import amara.applications.ingame.entitysystem.components.physics.CollisionGroupComponent;
import amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent;
import amara.applications.ingame.entitysystem.components.physics.HitboxComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.MapCollisionTriggerComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.physics.shapes.ConvexShape;
import amara.libraries.physics.shapes.Shape;
import amara.libraries.physics.shapes.Vector2D;
import com.jme3.math.Vector2f;
import amara.libraries.physics.intersectionHelper.PolyMapManager;

public final class MapIntersectionSystem implements EntitySystem {

    public MapIntersectionSystem(PolyMapManager polyMapManager) {
        this.polyMapManager = polyMapManager;
    }
    private PolyMapManager polyMapManager;

    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(HitboxComponent.class, HitboxActiveComponent.class, CollisionGroupComponent.class, PositionComponent.class)) {
            CollisionGroupComponent filterComp = entityWorld.getComponent(entity, CollisionGroupComponent.class);
            if (CollisionGroupComponent.isColliding(filterComp.getTargetOf(), CollisionGroupComponent.MAP)) {
                Shape shape = entityWorld.getComponent(entity, HitboxComponent.class).getShape();
                if (shape instanceof ConvexShape) {
                    ConvexShape convex = (ConvexShape)shape;
                    Vector2f pos = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2D position = new Vector2D(pos.x, pos.y);
                    double radius = convex.getBoundCircle().getGlobalRadius();
                    Vector2D validPosition = polyMapManager.closestValid(position, radius);
                    if (!position.equals(validPosition)) {
                        entityWorld.setComponent(entity, new PositionComponent(new Vector2f((float) validPosition.getX(), (float) validPosition.getY())));
                        triggerMapCollisionTriggers(entityWorld, entity);
                    }
                }
            }
        }
    }

    private void triggerMapCollisionTriggers(EntityWorld entityWorld, int entity) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, MapCollisionTriggerComponent.class)) {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if (sourceEntity == entity) {
                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
            }
        }
    }
}
