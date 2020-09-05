/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.filters;

import com.jme3.post.Filter;
import com.jme3.water.WaterFilter;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.applications.display.appstates.WaterAppState;
import amara.libraries.applications.display.ingame.appstates.MapAppState;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class WaterSpeedSystem implements EntitySystem {

    public WaterSpeedSystem(MapAppState mapAppState) {
        this.mapAppState = mapAppState;
    }
    private MapAppState mapAppState;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, GameSpeedComponent.class);
        GameSpeedComponent gameSpeedComponent = observer.getChanged().getComponent(Map.GAME_ENTITY, GameSpeedComponent.class);
        if (gameSpeedComponent != null) {
            for (Filter filter : mapAppState.getActiveFilters()) {
                if (filter instanceof WaterFilter) {
                    WaterFilter waterFilter = (WaterFilter) filter;
                    waterFilter.setSpeed(WaterAppState.DEFAULT_SPEED * gameSpeedComponent.getSpeed());
                }
            }
        }
    }
}
