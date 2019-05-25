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
public class DisplayScoreboardScoresSystem extends PlayersDisplaySystem {

    public DisplayScoreboardScoresSystem(ScreenController_HUD screenController_HUD){
        super(screenController_HUD);
    }
    private ComponentMapObserver observer;

    @Override
    protected void preUpdate(EntityWorld entityWorld, float deltaSeconds){
        super.preUpdate(entityWorld, deltaSeconds);
        observer = entityWorld.requestObserver(this, CharacterKillsComponent.class, DeathsComponent.class, CharacterAssistsComponent.class, CreepScoreComponent.class);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int playerEntity){
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        ScoreComponent scoreComponent = entityWorld.getComponent(characterEntity, ScoreComponent.class);
        if(scoreComponent != null){
            int scoreEntity = scoreComponent.getScoreEntity();
            if(hasComponentChanged(observer, scoreEntity, CharacterKillsComponent.class, DeathsComponent.class, CharacterAssistsComponent.class)){
                update_KDA(entityWorld, playerIndex, scoreEntity);
            }
            if(hasComponentChanged(observer, scoreEntity, CreepScoreComponent.class)){
                update_CreepScore(entityWorld, playerIndex, scoreEntity);
            }
        }
    }
    
    private void update_KDA(EntityWorld entityWorld, int playerIndex,int scoreEntity){
        int kills = entityWorld.getComponent(scoreEntity, CharacterKillsComponent.class).getKills();
        int deaths = entityWorld.getComponent(scoreEntity, DeathsComponent.class).getDeaths();
        int assists = entityWorld.getComponent(scoreEntity, CharacterAssistsComponent.class).getAssists();
        screenController_HUD.setScoreboard_KDA(playerIndex, kills, deaths, assists);
    }
    
    private void update_CreepScore(EntityWorld entityWorld, int playerIndex,int scoreEntity){
        int kills = entityWorld.getComponent(scoreEntity, CreepScoreComponent.class).getKills();
        screenController_HUD.setScoreboard_CreepScore(playerIndex, kills);
    }
}
