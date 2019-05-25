/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.game.GameTimeComponent;
import amara.applications.ingame.shared.games.Game;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayGameTimeSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayGameTimeSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        float time = entityWorld.getComponent(Game.ENTITY, GameTimeComponent.class).getTime();
        screenController.setGameTime(time);
    }
}
