/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.synchronizing;

import amara.applications.ingame.entitysystem.components.game.GameSpeedComponent;
import amara.applications.ingame.shared.games.Game;
import amara.libraries.entitysystem.EntityWorld;

import java.util.function.Consumer;

/**
 *
 * @author Carl
 */
public class GameSynchronizingUtil {

    public static void simulateSecondFrames(EntityWorld entityWorld, float lastTimePerFrame, Consumer<Float> consumer) {
        float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
        for (float i = gameSpeed; i > 0; i--) {
            float simulatedTimePerFrame = Math.min(i, 1) * lastTimePerFrame;
            consumer.accept(simulatedTimePerFrame);
        }
    }
}
