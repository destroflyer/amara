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
public class ApplyPlayAudioSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PlayAudioComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            PlayAudioComponent playAudioComponent = entityWorld.getComponent(effectImpactEntity, PlayAudioComponent.class);
            for (int referencedAudioEntity : playAudioComponent.getAudioEntities()) {
                int audioEntity;
                if (playAudioComponent.isClone()) {
                    audioEntity = entityWorld.createEntity();
                    EntityUtil.transferComponents(entityWorld, referencedAudioEntity, audioEntity, new Class[] {
                        AudioComponent.class,
                        AudioGlobalComponent.class,
                        AudioLoopComponent.class,
                        AudioRemoveAfterPlayingComponent.class,
                        AudioVolumeComponent.class,
                    });
                } else {
                    audioEntity = referencedAudioEntity;
                }
                entityWorld.setComponent(audioEntity, new AudioSourceComponent(targetEntity));
                entityWorld.setComponent(audioEntity, new StartPlayingAudioComponent());
            }
        }
    }
}
