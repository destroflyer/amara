/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayLevelSystem extends GUIDisplaySystem{

    public DisplayLevelSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, LevelComponent.class);
        check(observer.getNew().getComponent(characterEntity, LevelComponent.class));
        check(observer.getChanged().getComponent(characterEntity, LevelComponent.class));
    }
    
    private void check(LevelComponent levelComponent){
        if(levelComponent != null){
            screenController_HUD.setLevel(levelComponent.getLevel());
        }
    }
}
