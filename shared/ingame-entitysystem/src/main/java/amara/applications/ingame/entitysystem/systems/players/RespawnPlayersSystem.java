/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.players;

import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
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

    private void onPlayerDeath(EntityWorld entityWorld, int characterEntity) {
        int rulesEntity = entityWorld.getComponent(map.getEntity(), PlayerDeathRulesComponent.class).getRulesEntity();
        if (entityWorld.hasComponent(rulesEntity, RespawnPlayersComponent.class)) {
            float remainingDuration = getRespawnDuration(entityWorld, rulesEntity);
            // Use WaitingToRespawnComponent instead of a delayed trigger, because effecttriggers aren't sent to the client (Deathtimer is needed in UI)
            entityWorld.setComponent(characterEntity, new WaitingToRespawnComponent(remainingDuration));
        }
    }

    private float getRespawnDuration(EntityWorld entityWorld, int rulesEntity) {
        RespawnTimerComponent respawnTimerComponent = entityWorld.getComponent(rulesEntity, RespawnTimerComponent.class);
        if (respawnTimerComponent != null) {
            return (respawnTimerComponent.getInitialDuration() + (respawnTimerComponent.getDeltaDurationPerTime() * UpdateGameTimeSystem.getGameTime(entityWorld)));
        }
        return 0;
    }
}
