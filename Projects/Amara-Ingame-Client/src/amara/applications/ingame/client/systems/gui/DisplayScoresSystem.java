/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayScoresSystem extends GUIDisplaySystem{

    public DisplayScoresSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CharacterKillsComponent.class, DeathsComponent.class, CharacterAssistsComponent.class, CreepScoreComponent.class);
        for(int playerEntity : entityWorld.getEntitiesWithAll(PlayerIndexComponent.class)){
            int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
            int playerSelectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
            int scoreEntity = entityWorld.getComponent(playerSelectedEntity, ScoreComponent.class).getScoreEntity();
            //Kills
            check(playerIndex, observer.getNew().getComponent(scoreEntity, CharacterKillsComponent.class));
            check(playerIndex, observer.getChanged().getComponent(scoreEntity, CharacterKillsComponent.class));
            //Deaths
            check(playerIndex, observer.getNew().getComponent(scoreEntity, DeathsComponent.class));
            check(playerIndex, observer.getChanged().getComponent(scoreEntity, DeathsComponent.class));
            //Assists
            check(playerIndex, observer.getNew().getComponent(scoreEntity, CharacterAssistsComponent.class));
            check(playerIndex, observer.getChanged().getComponent(scoreEntity, CharacterAssistsComponent.class));
            //CreepScore
            check(playerIndex, observer.getNew().getComponent(scoreEntity, CreepScoreComponent.class));
            check(playerIndex, observer.getChanged().getComponent(scoreEntity, CreepScoreComponent.class));
        }
    }
    
    private void check(int playerIndex, CharacterKillsComponent characterKillsComponent){
        if(characterKillsComponent != null){
            screenController_HUD.setPlayerScore_CharacterKills(playerIndex, characterKillsComponent.getKills());
        }
    }
    
    private void check(int playerIndex, DeathsComponent deathsComponent){
        if(deathsComponent != null){
            screenController_HUD.setPlayerScore_Deaths(playerIndex, deathsComponent.getDeaths());
        }
    }
    
    private void check(int playerIndex, CharacterAssistsComponent characterAssistsComponent){
        if(characterAssistsComponent != null){
            screenController_HUD.setPlayerScore_CharacterAssists(playerIndex, characterAssistsComponent.getAssists());
        }
    }
    
    private void check(int playerIndex, CreepScoreComponent creepScoreComponent){
        if(creepScoreComponent != null){
            screenController_HUD.setPlayerScore_CreepScore(playerIndex, creepScoreComponent.getKills());
        }
    }
}
