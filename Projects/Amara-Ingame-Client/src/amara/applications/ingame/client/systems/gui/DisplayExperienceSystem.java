/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.units.LevelUpSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayExperienceSystem extends GUIDisplaySystem{

    public DisplayExperienceSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        LevelComponent levelComponent = entityWorld.getComponent(characterEntity, LevelComponent.class);
        if(levelComponent != null){
            ComponentMapObserver observer = entityWorld.requestObserver(this, ExperienceComponent.class);
            check(levelComponent, observer.getNew().getComponent(characterEntity, ExperienceComponent.class));
            check(levelComponent, observer.getChanged().getComponent(characterEntity, ExperienceComponent.class));
        }
    }
    
    private void check(LevelComponent levelComponent, ExperienceComponent experienceComponent){
        if(experienceComponent != null){
            float portion = (((float) experienceComponent.getExperience()) / LevelUpSystem.getNeededLevelUpExperience(levelComponent.getLevel()));
            screenController_HUD.setExperience(portion);
        }
    }
}
