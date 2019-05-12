/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.players.WaitingToRespawnComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayDeathTimerSystem extends GUIDisplaySystem{

    public DisplayDeathTimerSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, WaitingToRespawnComponent.class);
        check(observer.getNew().getComponent(playerEntity, WaitingToRespawnComponent.class));
        check(observer.getChanged().getComponent(playerEntity, WaitingToRespawnComponent.class));
    }
    
    private void check(WaitingToRespawnComponent waitingToRespawnComponent){
        if(waitingToRespawnComponent != null){
            screenController_HUD.setDeathTimer(waitingToRespawnComponent.getRemainingDuration());
        }
    }
}
