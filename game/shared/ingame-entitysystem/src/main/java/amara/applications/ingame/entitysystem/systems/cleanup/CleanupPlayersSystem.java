/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class CleanupPlayersSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            PlayerCharacterComponent.class
        );
        for (int entity : observer.getRemoved().getEntitiesWithAny(PlayerCharacterComponent.class)) {
            int characterEntity = observer.getRemoved().getComponent(entity, PlayerCharacterComponent.class).getEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, characterEntity);
        }
    }
}
