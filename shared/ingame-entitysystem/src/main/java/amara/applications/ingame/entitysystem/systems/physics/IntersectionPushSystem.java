package amara.applications.ingame.entitysystem.systems.physics;

import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.physics.intersection.IntersectionTracker;
import amara.libraries.physics.intersection.Pair;
import amara.libraries.physics.shapes.Shape;
import amara.libraries.physics.shapes.Vector2D;
import com.jme3.math.Vector2f;

public class IntersectionPushSystem implements EntitySystem {

    private IntersectionInformant info;

    public IntersectionPushSystem(IntersectionInformant info) {
        this.info = info;
    }

    public void update(EntityWorld entityWorld, float deltaSeconds) {
        IntersectionTracker<Pair<Integer>> tracker = info.getTracker(entityWorld, this);
        for (Pair<Integer> pair: tracker.getEntries()) {
            checkAndApplyPushes(entityWorld, pair);
        }
        for (Pair<Integer> pair: tracker.getRepeaters()) {
            checkAndApplyPushes(entityWorld, pair);
        }
    }

    private void checkAndApplyPushes(EntityWorld entityWorld, Pair<Integer> pair) {
        if (entityWorld.hasComponent(pair.getA(), PositionComponent.class) && entityWorld.hasComponent(pair.getB(), PositionComponent.class)) {
            boolean aPushesB = (entityWorld.hasComponent(pair.getA(), IntersectionPushesComponent.class) && entityWorld.hasComponent(pair.getB(), IntersectionPushedComponent.class));
            boolean bPushesA = (entityWorld.hasComponent(pair.getB(), IntersectionPushesComponent.class) && entityWorld.hasComponent(pair.getA(), IntersectionPushedComponent.class));

            if (aPushesB || bPushesA) {
                Shape shapeA = entityWorld.getComponent(pair.getA(), HitboxComponent.class).getShape();
                Shape shapeB = entityWorld.getComponent(pair.getB(), HitboxComponent.class).getShape();
                Vector2D resolver = shapeA.getIntersectionResolver(shapeB);

                double pushedFactorA = (bPushesA ? (aPushesB ? 0.5 : 1) : 0);
                double pushedFactorB = (aPushesB ? (bPushesA ? 0.5 : 1) : 0);
                Vector2D resolverA = resolver.mult(pushedFactorA);
                Vector2D resolverB = resolver.mult(-1 * pushedFactorB);

                if (!resolverA.withinEpsilon()) {
                    applyPush(entityWorld, pair.getA(), resolverA);
                }
                if (!resolverB.withinEpsilon()) {
                    applyPush(entityWorld, pair.getB(), resolverB);
                }
            }
        }
    }

    private void applyPush(EntityWorld entityWorld, int entity, Vector2D pushedDistance) {
        float deltaX = (float) pushedDistance.getX();
        float deltaY = (float) pushedDistance.getY();
        Vector2f oldPosition = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        Vector2f newPosition = new Vector2f(oldPosition.x + deltaX, oldPosition.y + deltaY);
        entityWorld.setComponent(entity, new PositionComponent(newPosition));
    }
}
