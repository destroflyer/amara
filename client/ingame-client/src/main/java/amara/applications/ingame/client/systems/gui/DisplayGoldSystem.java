/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayGoldSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayGoldSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        float gold = 0;
        GoldComponent goldComponent = entityWorld.getComponent(characterEntity, GoldComponent.class);
        if(goldComponent != null){
            gold = goldComponent.getGold();
        }
        screenController.setPlayer_Gold(gold);
    }
}
