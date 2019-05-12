/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayGoldSystem extends GUIDisplaySystem{

    public DisplayGoldSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        float gold = 0;
        GoldComponent goldComponent = entityWorld.getComponent(characterEntity, GoldComponent.class);
        if(goldComponent != null){
            gold = goldComponent.getGold();
        }
        screenController_HUD.setGold(gold);
    }
}
