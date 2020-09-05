/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.movement;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.movement.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyTeleportSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, TeleportComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int targetPositionEntity = entityWorld.getComponent(effectImpactEntity, TeleportComponent.class).getTargetEntity();
            Vector2f targetPosition = entityWorld.getComponent(targetPositionEntity, PositionComponent.class).getPosition();
            entityWorld.setComponent(targetEntity, new PositionComponent(targetPosition.clone()));
            if (entityWorld.hasComponent(targetPositionEntity, TemporaryComponent.class)) {
                entityWorld.removeEntity(targetPositionEntity);
            }
        }
    }
}
