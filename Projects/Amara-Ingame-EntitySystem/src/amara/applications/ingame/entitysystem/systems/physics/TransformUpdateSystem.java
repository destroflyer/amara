package amara.applications.ingame.entitysystem.systems.physics;

import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.TransformUpdater;
import amara.libraries.entitysystem.*;

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
