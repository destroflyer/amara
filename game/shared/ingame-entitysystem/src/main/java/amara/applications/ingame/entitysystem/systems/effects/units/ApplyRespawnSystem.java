/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.units;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.visuals.AnimationComponent;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

import java.util.Optional;

/**
 *
 * @author Carl
 */
public class ApplyRespawnSystem implements EntitySystem {

    public ApplyRespawnSystem(Map map) {
        this.map = map;
    }
    private Map map;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RespawnComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, IsRespawnableComponent.class)) {
                entityWorld.removeComponent(targetEntity, AnimationComponent.class);
                entityWorld.removeComponent(targetEntity, HealthComponent.class);
                entityWorld.removeComponent(targetEntity, DamageHistoryComponent.class);
                entityWorld.setComponent(targetEntity, new HitboxActiveComponent());
                entityWorld.setComponent(targetEntity, new IsAliveComponent());
                entityWorld.setComponent(targetEntity, new IsTargetableComponent());
                entityWorld.setComponent(targetEntity, new IsVulnerableComponent());
                entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
                RespawnPositionComponent respawnPositionComponent = entityWorld.getComponent(targetEntity, RespawnPositionComponent.class);
                if (respawnPositionComponent != null) {
                    entityWorld.setComponent(targetEntity, new PositionComponent(respawnPositionComponent.getPosition()));
                }
                RespawnDirectionComponent respawnDirectionComponent = entityWorld.getComponent(targetEntity, RespawnDirectionComponent.class);
                if (respawnDirectionComponent != null) {
                    entityWorld.setComponent(targetEntity, new DirectionComponent(respawnDirectionComponent.getDirection()));
                }
                getPlayerEntity(entityWorld, targetEntity).ifPresent(playerEntity -> map.spawnPlayer(entityWorld, playerEntity));
            }
        }
    }

    private Optional<Integer> getPlayerEntity(EntityWorld entityWorld, int characterEntity) {
        return entityWorld.getEntitiesWithAny(PlayerCharacterComponent.class).stream()
                .filter(entity -> entityWorld.getComponent(entity, PlayerCharacterComponent.class).getEntity() == characterEntity)
                .findFirst();
    }
}
