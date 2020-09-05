/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityComponentMapReadonly;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayPlayerAnnouncementSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayPlayerAnnouncementSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, PlayerAnnouncementComponent.class);
        checkChange(observer.getNew());
        checkChange(observer.getChanged());
        if (observer.getRemoved().hasEntity(getPlayerEntity())) {
            screenController.hidePlayerAnnouncement();
        }
    }

    private void checkChange(EntityComponentMapReadonly entityComponentMap) {
        PlayerAnnouncementComponent playerAnnouncementComponent = entityComponentMap.getComponent(getPlayerEntity(), PlayerAnnouncementComponent.class);
        if (playerAnnouncementComponent != null) {
            screenController.showPlayerAnnouncement(playerAnnouncementComponent.getText());
        }
    }
}
