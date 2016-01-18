/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.audio;

import amara.game.entitysystem.components.audio.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RemoveAudioCommandsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(StartPlayingAudioComponent.class)){
            entityWorld.removeComponent(entity, StartPlayingAudioComponent.class);
        }
        for(int entity : entityWorld.getEntitiesWithAll(StopPlayingAudioComponent.class)){
            entityWorld.removeComponent(entity, StopPlayingAudioComponent.class);
        }
    }
}
