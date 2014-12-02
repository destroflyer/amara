/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.filters;

import com.jme3.post.Filter;
import com.jme3.water.WaterFilter;
import amara.engine.appstates.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.game.*;
import amara.game.games.Game;

/**
 *
 * @author Carl
 */
public class WaterSpeedSystem implements EntitySystem{

    public WaterSpeedSystem(MapAppState mapAppState){
        this.mapAppState = mapAppState;
    }
    private MapAppState mapAppState;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, GameSpeedComponent.class);
        GameSpeedComponent gameSpeedComponent = observer.getChanged().getComponent(Game.ENTITY, GameSpeedComponent.class);
        if(gameSpeedComponent != null){
            for(Filter filter : mapAppState.getActiveFilters()){
                if(filter instanceof WaterFilter){
                    WaterFilter waterFilter = (WaterFilter) filter;
                    waterFilter.setSpeed(WaterAppState.DEFAULT_SPEED * gameSpeedComponent.getSpeed());
                }
            }
        }
        observer.reset();
    }
}
