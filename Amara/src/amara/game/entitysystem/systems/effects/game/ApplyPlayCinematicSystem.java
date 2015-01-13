/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.game;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.game.*;
import amara.game.entitysystem.components.game.*;
import amara.game.games.Game;

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
