package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.GUIUtil;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.game.GameTimeComponent;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayGameTimeSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayGameTimeSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }
    private String currentFormattedTime;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        float time = entityWorld.getComponent(Map.GAME_ENTITY, GameTimeComponent.class).getTime();
        String formattedTime = GUIUtil.getFormattedTime(time);
        // Only update the time (-> calling expensive nifty redraw methods) when the displayed text actually changes (once per second)
        if (!formattedTime.equals(currentFormattedTime)) {
            screenController.setGameTime(formattedTime);
            currentFormattedTime = formattedTime;
        }
    }
}
