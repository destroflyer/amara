/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.effects.units.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckWaitingToRespawnSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(WaitingToRespawnComponent.class)) {
            WaitingToRespawnComponent respawnComponent = entityWorld.getComponent(entity, WaitingToRespawnComponent.class);
            if (respawnComponent.getRemainingDuration() <= 0) {
                entityWorld.removeComponent(entity, WaitingToRespawnComponent.class);
                // Trigger instant respawn
                int respawnTrigger = entityWorld.createEntity();
                entityWorld.setComponent(respawnTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(respawnTrigger, new CustomTargetComponent(entity));
                int respawnEffect = entityWorld.createEntity();
                entityWorld.setComponent(respawnEffect, new RespawnComponent());
                entityWorld.setComponent(respawnTrigger, new TriggeredEffectComponent(respawnEffect));
                entityWorld.setComponent(respawnTrigger, new TriggerSourceComponent(entity));
                entityWorld.setComponent(respawnTrigger, new TriggerOnceComponent(true));
            }
        }
    }
}
