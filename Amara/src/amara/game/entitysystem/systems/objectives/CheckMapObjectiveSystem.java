/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.objectives;

import amara.engine.applications.ingame.server.appstates.GameRunningAppState;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.maps.Map;

/**
 *
 * @author Carl
 */
public class CheckMapObjectiveSystem implements EntitySystem{

    public CheckMapObjectiveSystem(Map map, GameRunningAppState gameRunningAppState){
        this.map = map;
        this.gameRunningAppState = gameRunningAppState;
    }
    private Map map;
    private GameRunningAppState gameRunningAppState;
    private boolean isFinished = false;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if((!isFinished) && entityWorld.hasComponent(map.getObjectiveEntity(), FinishedObjectiveComponent.class)){
            isFinished = true;
            gameRunningAppState.onGameOver();
        }
    }
}
