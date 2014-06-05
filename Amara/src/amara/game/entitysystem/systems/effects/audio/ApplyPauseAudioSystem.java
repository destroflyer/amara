/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.audio;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.audio.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.audio.*;

/**
 *
 * @author Carl
 */
public class ApplyPauseAudioSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PauseAudioComponent.class)))
        {
            for(int audioEntity : entityWrapper.getComponent(PauseAudioComponent.class).getAudioEntities()){
                entityWorld.setComponent(audioEntity, new IsAudioPausedComponent());
            }
        }
    }
}
