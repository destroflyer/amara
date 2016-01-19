/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.objectives;

import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.shared.maps.Map;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckMapObjectiveSystem implements EntitySystem{

    public CheckMapObjectiveSystem(Map map, IngameServerApplication mainApplication){
        this.map = map;
        this.mainApplication = mainApplication;
    }
    private Map map;
    private IngameServerApplication mainApplication;
    private boolean isFinished = false;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(!isFinished){
            int objectiveEntity = entityWorld.getComponent(map.getEntity(), MapObjectiveComponent.class).getObjectiveEntity();
            if(entityWorld.hasComponent(objectiveEntity, FinishedObjectiveComponent.class)){
                isFinished = true;
                mainApplication.getMasterServer().onGameOver(mainApplication);
            }
        }
    }
}
