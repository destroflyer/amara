/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.audio;

import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.audio.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyStopAudioSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, StopAudioComponent.class)))
        {
            for(int audioEntity : entityWrapper.getComponent(StopAudioComponent.class).getAudioEntities()){
                entityWorld.removeComponent(audioEntity, AudioSourceComponent.class);
                entityWorld.setComponent(audioEntity, new StopPlayingAudioComponent());
            }
        }
    }
}
