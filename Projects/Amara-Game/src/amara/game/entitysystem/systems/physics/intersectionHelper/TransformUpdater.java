/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import java.util.HashSet;
import amara.game.entitysystem.components.physics.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Philipp
 */
public class TransformUpdater {

    public void updateTransforms(EntityWorld entityWorld) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class, DirectionComponent.class, ScaleComponent.class);
        HashSet<Integer> updateNeeded = new HashSet<Integer>();
        for (int entity : observer.getRemoved().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class)) {
            updateNeeded.add(entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class)) {
            updateNeeded.add(entity);
        }
        for (int entity : observer.getNew().getEntitiesWithAny(PositionComponent.class, DirectionComponent.class, ScaleComponent.class)) {
            updateNeeded.add(entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(HitboxComponent.class)) {
            updateNeeded.add(entity);
        }
        for (int entity : observer.getNew().getEntitiesWithAny(HitboxComponent.class)) {
            updateNeeded.add(entity);
        }
        for (int entity : updateNeeded) {
            updateTransforms(entityWorld.getWrapped(entity));
        }
    }

    private void updateTransforms(EntityWrapper entity) {
        HitboxComponent hitbox = entity.getComponent(HitboxComponent.class);
        if (hitbox == null) {
            return;
        }
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        if (pos == null) {
            return;
        }
        DirectionComponent dir = entity.getComponent(DirectionComponent.class);
        ScaleComponent scale = entity.getComponent(ScaleComponent.class);
        Shape shape = hitbox.getShape();

        double x = pos.getPosition().x;
        double y = pos.getPosition().y;
        double radian, s;

        if (dir != null) {
            radian = dir.getRadian();
        } else {
            radian = 0;
        }
        if (scale != null) {
            s = scale.getScale();
        } else {
            s = 1;
        }

        shape.setTransform(new Transform2D(s, radian, x, y));
        entity.setComponent(new HitboxComponent(shape));
    }
}
