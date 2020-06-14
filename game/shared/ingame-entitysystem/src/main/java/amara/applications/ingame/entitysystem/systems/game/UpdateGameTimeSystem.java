/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.game;

import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateGameTimeSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        float time = 0;
        GameTimeComponent gameTimeComponent = entityWorld.getComponent(Map.GAME_ENTITY, GameTimeComponent.class);
        if (gameTimeComponent != null) {
            time = gameTimeComponent.getTime();
        }
        entityWorld.setComponent(Map.GAME_ENTITY, new GameTimeComponent(time + deltaSeconds));
    }

    public static float getGameTime(EntityWorld entityWorld) {
        return entityWorld.getComponent(Map.GAME_ENTITY, GameTimeComponent.class).getTime();
    }
}
