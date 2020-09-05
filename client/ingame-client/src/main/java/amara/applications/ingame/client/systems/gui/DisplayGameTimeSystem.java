/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.game.GameTimeComponent;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayGameTimeSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayGameTimeSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        float time = entityWorld.getComponent(Map.GAME_ENTITY, GameTimeComponent.class).getTime();
        screenController.setGameTime(time);
    }
}