package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.audio.AudioRemoveAfterPlayingComponent;
import amara.applications.ingame.entitysystem.components.audio.StartPlayingAudioComponent;
import amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent;
import amara.libraries.entitysystem.EntityWorld;

public class CleanupUtil {

    public static void tryCleanupEntities(EntityWorld entityWorld, int[] entities) {
        for (int entity : entities) {
            tryCleanupEntity(entityWorld, entity);
        }
    }

    public static void tryCleanupEntity(EntityWorld entityWorld, int entity) {
        boolean removeEntity = (!entityWorld.hasComponent(entity, CustomCleanupComponent.class));
        if (removeEntity) {
            // These audio entities will be kept so they are sent to the client and finally removed by the RemoveAudiosAfterPlayingSystem
            if (entityWorld.hasComponent(entity, AudioRemoveAfterPlayingComponent.class)
             && entityWorld.hasComponent(entity, StartPlayingAudioComponent.class)){
                removeEntity = false;
            }
        }
        if (removeEntity) {
            entityWorld.removeEntity(entity);
        }
    }
}
