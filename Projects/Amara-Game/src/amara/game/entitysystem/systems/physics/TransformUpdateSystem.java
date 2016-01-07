package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.EntitySystem;
import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.systems.physics.intersectionHelper.TransformUpdater;

/**
 *
 * @author Philipp
 */
public class TransformUpdateSystem implements EntitySystem {
    private final TransformUpdater updater = new TransformUpdater();
    
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        updater.updateTransforms(entityWorld);
    }

}
