/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayStatsPlayerScoreSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayStatsPlayerScoreSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CharacterKillsComponent.class, DeathsComponent.class, CharacterAssistsComponent.class, CreepScoreComponent.class);
        ScoreComponent scoreComponent = entityWorld.getComponent(characterEntity, ScoreComponent.class);
        if(scoreComponent != null){
            int scoreEntity = scoreComponent.getScoreEntity();
            if(hasComponentChanged(observer, scoreEntity, CharacterKillsComponent.class, DeathsComponent.class, CharacterAssistsComponent.class)){
                update_KDA(entityWorld, scoreEntity);
            }
            if(hasComponentChanged(observer, scoreEntity, CreepScoreComponent.class)){
                update_CreepScore(entityWorld, scoreEntity);
            }
        }
    }
    
    private void update_KDA(EntityWorld entityWorld, int scoreEntity){
        int kills = entityWorld.getComponent(scoreEntity, CharacterKillsComponent.class).getKills();
        int deaths = entityWorld.getComponent(scoreEntity, DeathsComponent.class).getDeaths();
        int assists = entityWorld.getComponent(scoreEntity, CharacterAssistsComponent.class).getAssists();
        screenController.setStats_PlayerScore_KDA(kills, deaths, assists);
    }
    
    private void update_CreepScore(EntityWorld entityWorld, int scoreEntity){
        int kills = entityWorld.getComponent(scoreEntity, CreepScoreComponent.class).getKills();
        screenController.setStats_PlayerScore_CreepScore(kills);
    }
}
