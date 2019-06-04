/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayInspectionSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayInspectionSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        screenController.setInspectionVisible(getInspectedEntity() != -1);
    }
}
