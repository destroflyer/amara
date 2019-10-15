/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.players;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.players.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ApplyDisplayPlayerAnnouncementsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPlayerAnnouncementComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            ResultingPlayerAnnouncementComponent resultingPlayerAnnouncementComponent = entityWorld.getComponent(effectImpactEntity, ResultingPlayerAnnouncementComponent.class);
            String text = resultingPlayerAnnouncementComponent.getText();
            float remainingDuration = resultingPlayerAnnouncementComponent.getRemainingDuration();
            entityWorld.setComponent(targetEntity, new PlayerAnnouncementComponent(text));
            if (remainingDuration != -1) {
                entityWorld.setComponent(targetEntity, new PlayerAnnouncementRemainingDurationComponent(remainingDuration));
            }
        }
    }
}
