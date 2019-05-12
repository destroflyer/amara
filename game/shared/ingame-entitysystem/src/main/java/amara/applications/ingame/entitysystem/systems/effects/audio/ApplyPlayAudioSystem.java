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
public class ApplyPlayAudioSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PlayAudioComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            for(int audioEntity : entityWrapper.getComponent(PlayAudioComponent.class).getAudioEntities()){
                entityWorld.setComponent(audioEntity, new AudioSourceComponent(targetEntity));
                entityWorld.setComponent(audioEntity, new StartPlayingAudioComponent());
            }
        }
    }
}
