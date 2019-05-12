/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.game;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.game.*;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.games.Game;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyPlayCinematicSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PlayCinematicComponent.class)))
        {
            String cinematicClassName = entityWrapper.getComponent(PlayCinematicComponent.class).getCinematicClassName();
            entityWorld.setComponent(Game.ENTITY, new CinematicComponent(cinematicClassName));
        }
    }
}
