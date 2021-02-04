package amara.applications.ingame.client.systems.cinematics;

import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.maps.Map;
import amara.core.Util;
import amara.libraries.applications.display.appstates.CinematicAppState;
import amara.libraries.applications.display.cinematics.Cinematic;
import amara.libraries.entitysystem.*;

public class CinematicsSystem implements EntitySystem {

    public CinematicsSystem(CinematicAppState cinematicAppState) {
        this.cinematicAppState = cinematicAppState;
    }
    private CinematicAppState cinematicAppState;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, CinematicComponent.class);
        checkCinematicComponent(observer.getNew().getComponent(Map.GAME_ENTITY, CinematicComponent.class));
        checkCinematicComponent(observer.getChanged().getComponent(Map.GAME_ENTITY, CinematicComponent.class));
    }

    private void checkCinematicComponent(CinematicComponent cinematicComponent) {
        if (cinematicComponent != null) {
            Cinematic cinematic = Util.createObjectByClassName(cinematicComponent.getCinematicClassName());
            cinematicAppState.playCinematic(cinematic);
        }
    }
}
