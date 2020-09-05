/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayDeathTimerSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayDeathTimerSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, WaitingToRespawnComponent.class);
        check(observer.getNew().getComponent(characterEntity, WaitingToRespawnComponent.class));
        check(observer.getChanged().getComponent(characterEntity, WaitingToRespawnComponent.class));
    }

    private void check(WaitingToRespawnComponent waitingToRespawnComponent) {
        if(waitingToRespawnComponent != null){
            screenController.setDeathTimer(waitingToRespawnComponent.getRemainingDuration());
        }
    }
}
