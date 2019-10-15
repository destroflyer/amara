/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.players;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class CountdownPlayerAnnouncementsSystem implements EntitySystem {
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(PlayerAnnouncementRemainingDurationComponent.class)) {
            PlayerAnnouncementRemainingDurationComponent playerAnnouncementRemainingDurationComponent = entityWorld.getComponent(entity, PlayerAnnouncementRemainingDurationComponent.class);
            if (playerAnnouncementRemainingDurationComponent.getRemainingDuration() != -1) {
                float duration = (playerAnnouncementRemainingDurationComponent.getRemainingDuration() - deltaSeconds);
                if (duration > 0) {
                    entityWorld.setComponent(entity, new PlayerAnnouncementRemainingDurationComponent(duration));
                } else {
                    entityWorld.removeComponent(entity, PlayerAnnouncementComponent.class);
                    entityWorld.removeComponent(entity, PlayerAnnouncementRemainingDurationComponent.class);
                }
            }
        }
    }
}
