/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.players;

import amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent;
import amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.applications.ingame.entitysystem.components.units.IsRespawnableComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerDelayComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent;
import amara.applications.ingame.entitysystem.systems.game.UpdateGameTimeSystem;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class RespawnPlayersSystem implements EntitySystem {

    public RespawnPlayersSystem(Map map) {
        this.map = map;
    }
    private Map map;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int playerEntity : entityWorld.getEntitiesWithAny(PlayerCharacterComponent.class)) {
            int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
            if (observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)) {
                onPlayerDeath(entityWorld, characterEntity);
            }
        }
    }

    private void onPlayerDeath(EntityWorld entityWorld, int characterEntity){
        if (entityWorld.hasComponent(characterEntity, IsRespawnableComponent.class)) {
            int rulesEntity = entityWorld.getComponent(map.getEntity(), PlayerDeathRulesComponent.class).getRulesEntity();
            if (entityWorld.hasComponent(rulesEntity, RespawnPlayersComponent.class)) {
                int respawnTrigger = entityWorld.createEntity();
                entityWorld.setComponent(respawnTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(respawnTrigger, new CustomTargetComponent(characterEntity));
                int respawnEffect = entityWorld.createEntity();
                entityWorld.setComponent(respawnEffect, new RespawnComponent());
                entityWorld.setComponent(respawnTrigger, new TriggeredEffectComponent(respawnEffect));
                entityWorld.setComponent(respawnTrigger, new TriggerSourceComponent(map.getEntity()));
                entityWorld.setComponent(respawnTrigger, new TriggerOnceComponent(true));

                RespawnTimerComponent respawnTimerComponent = entityWorld.getComponent(rulesEntity, RespawnTimerComponent.class);
                if (respawnTimerComponent != null) {
                    float remainingDuration = (respawnTimerComponent.getInitialDuration() + (respawnTimerComponent.getDeltaDurationPerTime() * UpdateGameTimeSystem.getGameTime(entityWorld)));
                    entityWorld.setComponent(respawnTrigger, new TriggerDelayComponent(remainingDuration));
                }
            }
        }
    }
}
