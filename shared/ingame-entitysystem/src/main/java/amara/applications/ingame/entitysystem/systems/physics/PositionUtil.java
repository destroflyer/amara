package amara.applications.ingame.entitysystem.systems.physics;

import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionUtil {

    public static List<Integer> sortByDistance(EntityWorld entityWorld, List<Integer> entities, int... priorizedTargetEntities) {
        Stream<Integer> entitiesStream = entities.stream();
        PositionComponent targetPositionComponent = null;
        for (int priorizedTargetEntity : priorizedTargetEntities) {
            targetPositionComponent = entityWorld.getComponent(priorizedTargetEntity, PositionComponent.class);
            if (targetPositionComponent != null) {
                break;
            }
        }
        if (targetPositionComponent != null) {
            Vector2f targetPosition = targetPositionComponent.getPosition();
            entitiesStream = entitiesStream.sorted((entity1, entity2) -> {
                PositionComponent positionComponent1 = entityWorld.getComponent(entity1, PositionComponent.class);
                PositionComponent positionComponent2 = entityWorld.getComponent(entity2, PositionComponent.class);
                if ((positionComponent1 != null) && (positionComponent2 != null)) {
                    float distanceSquared1 = targetPosition.distanceSquared(positionComponent1.getPosition());
                    float distanceSquared2 = targetPosition.distanceSquared(positionComponent2.getPosition());
                    return Float.compare(distanceSquared1, distanceSquared2);
                }
                return 0;
            });
        }
        return entitiesStream.collect(Collectors.toList());
    }

    public static boolean isInRange(EntityWorld entityWorld, int entity1, int entity2, float range) {
        PositionComponent positionComponent1 = entityWorld.getComponent(entity1, PositionComponent.class);
        PositionComponent positionComponent2 = entityWorld.getComponent(entity2, PositionComponent.class);
        if ((positionComponent1 != null) && (positionComponent2 != null)) {
            Vector2f position1 = positionComponent1.getPosition();
            Vector2f position2 = positionComponent2.getPosition();
            return (position1.distanceSquared(position2) <= (range * range));
        }
        return false;
    }
}
