/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.units.LevelUpSystem;

/**
 *
 * @author Carl
 */
public class DisplayExperienceSystem extends GUIDisplaySystem{

    public DisplayExperienceSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        LevelComponent levelComponent = entityWorld.getComponent(selectedEntity, LevelComponent.class);
        if(levelComponent != null){
            ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ExperienceComponent.class);
            check(levelComponent, observer.getNew().getComponent(selectedEntity, ExperienceComponent.class));
            check(levelComponent, observer.getChanged().getComponent(selectedEntity, ExperienceComponent.class));
            observer.reset();
        }
    }
    
    private void check(LevelComponent levelComponent, ExperienceComponent experienceComponent){
        if(experienceComponent != null){
            float portion = (((float) experienceComponent.getExperience()) / LevelUpSystem.getNeededLevelUpExperience(levelComponent.getLevel()));
            screenController_HUD.setExperience(portion);
        }
    }
}
