/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.GoldComponent;

/**
 *
 * @author Carl
 */
public class DisplayGoldSystem extends GUIDisplaySystem{

    public DisplayGoldSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        int gold = 0;
        GoldComponent goldComponent = entityWorld.getComponent(selectedEntity, GoldComponent.class);
        if(goldComponent != null){
            gold = goldComponent.getGold();
        }
        screenController_HUD.setGold(gold);
    }
}
