/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.game;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.game.*;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyPlayCinematicSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PlayCinematicComponent.class)) {
            String cinematicClassName = entityWorld.getComponent(effectImpactEntity, PlayCinematicComponent.class).getCinematicClassName();
            entityWorld.setComponent(Map.GAME_ENTITY, new CinematicComponent(cinematicClassName));
        }
    }
}
