/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RepeatingBuffEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper buffStatus : entityWorld.getWrapped(entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)))
        {
            ActiveBuffComponent activeBuffComponent = buffStatus.getComponent(ActiveBuffComponent.class);
            RepeatingEffectComponent repeatingEffectComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), RepeatingEffectComponent.class);
            if(repeatingEffectComponent != null){
                TimeSinceLastRepeatingEffectComponent timeSinceLastRepeatingEffectComponent = buffStatus.getComponent(TimeSinceLastRepeatingEffectComponent.class);
                float timeSinceLastRepeatingEffect = (((timeSinceLastRepeatingEffectComponent != null) ? timeSinceLastRepeatingEffectComponent.getDuration() : 0) + deltaSeconds);
                if(timeSinceLastRepeatingEffect >= repeatingEffectComponent.getInterval()){
                    EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
                    effectCast.setComponent(new PrepareEffectComponent(repeatingEffectComponent.getEffectEntity()));
                    EntityUtil.transferComponents(entityWorld, buffStatus.getId(), effectCast.getId(), new Class[]{
                        EffectSourceComponent.class,
                        EffectSourceSpellComponent.class
                    });
                    effectCast.setComponent(new AffectedTargetsComponent(activeBuffComponent.getTargetEntity()));
                    timeSinceLastRepeatingEffect = 0;
                }
                buffStatus.setComponent(new TimeSinceLastRepeatingEffectComponent(timeSinceLastRepeatingEffect));
            }
        }
    }
}
