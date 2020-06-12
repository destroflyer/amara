/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.audio;

import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RemoveAudiosAfterPlayingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SendEntityChangesSystem.COMPONENT_EQUALITY_DEFINITION, StartPlayingAudioComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(StartPlayingAudioComponent.class)){
            removeAudioAfterPlaying(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(StartPlayingAudioComponent.class)){
            removeAudioAfterPlaying(entityWorld, entity);
        }
    }
    
    private void removeAudioAfterPlaying(EntityWorld entityWorld, int audioEntity){
        if(entityWorld.hasComponent(audioEntity, AudioRemoveAfterPlayingComponent.class)){
            entityWorld.removeEntity(audioEntity);
        }
    }
}
