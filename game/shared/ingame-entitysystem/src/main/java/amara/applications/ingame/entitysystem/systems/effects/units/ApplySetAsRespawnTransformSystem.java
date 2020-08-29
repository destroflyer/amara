package amara.applications.ingame.entitysystem.systems.effects.units;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.units.SetAsRespawnTransformComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.units.RespawnDirectionComponent;
import amara.applications.ingame.entitysystem.components.units.RespawnPositionComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

public class ApplySetAsRespawnTransformSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SetAsRespawnTransformComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            Vector2f position = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            Vector2f direction = entityWorld.getComponent(targetEntity, DirectionComponent.class).getVector();
            entityWorld.setComponent(targetEntity, new RespawnPositionComponent(position.clone()));
            entityWorld.setComponent(targetEntity, new RespawnDirectionComponent(direction.clone()));
        }
    }
}
