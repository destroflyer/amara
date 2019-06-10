/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units.scores;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateDeathsScoreSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)){
            ScoreComponent scoreComponent = entityWorld.getComponent(entity, ScoreComponent.class);
            if(scoreComponent != null){
                int scoreEntity = scoreComponent.getScoreEntity();
                int deaths = 1;
                DeathsComponent deathsComponent = entityWorld.getComponent(scoreEntity, DeathsComponent.class);
                if(deathsComponent !=  null){
                    deaths += deathsComponent.getDeaths();
                }
                entityWorld.setComponent(scoreEntity, new DeathsComponent(deaths));
            }
        }
    }
}
