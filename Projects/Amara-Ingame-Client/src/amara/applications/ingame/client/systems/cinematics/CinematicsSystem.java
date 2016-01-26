/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.cinematics;

import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.games.Game;
import amara.core.Util;
import amara.libraries.applications.display.appstates.CinematicAppState;
import amara.libraries.applications.display.cinematics.Cinematic;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CinematicsSystem implements EntitySystem{

    public CinematicsSystem(CinematicAppState cinematicAppState){
        this.cinematicAppState = cinematicAppState;
    }
    private CinematicAppState cinematicAppState;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CinematicComponent.class);
        checkCinematicComponent(observer.getNew().getComponent(Game.ENTITY, CinematicComponent.class));
        checkCinematicComponent(observer.getChanged().getComponent(Game.ENTITY, CinematicComponent.class));
    }
    
    private void checkCinematicComponent(CinematicComponent cinematicComponent){
        if(cinematicComponent != null){
            Cinematic cinematic = Util.createObjectByClassName(cinematicComponent.getCinematicClassName(), Cinematic.class);
            cinematicAppState.playCinematic(cinematic);
        }
    }
}