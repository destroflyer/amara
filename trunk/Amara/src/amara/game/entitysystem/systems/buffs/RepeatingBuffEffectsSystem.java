/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;

/**
 *
 * @author Carl
 */
public class RepeatingBuffEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper buffStatus : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)))
        {
            int buffEntity = buffStatus.getComponent(ActiveBuffComponent.class).getBuffEntityID();
            RepeatingEffectComponent repeatingEffectComponent = entityWorld.getComponent(buffEntity, RepeatingEffectComponent.class);
            if(repeatingEffectComponent != null){
                TimeSinceLastRepeatingEffectComponent timeSinceLastRepeatingEffectComponent = buffStatus.getComponent(TimeSinceLastRepeatingEffectComponent.class);
                float timeSinceLastRepeatingEffect = (((timeSinceLastRepeatingEffectComponent != null)?timeSinceLastRepeatingEffectComponent.getDuration():0) + deltaSeconds);
                if(timeSinceLastRepeatingEffect >= repeatingEffectComponent.getInterval()){
                    EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
                    effectCast.setComponent(new PrepareEffectComponent(repeatingEffectComponent.getEffectEntityID()));
                    EntityUtil.transferComponents(buffStatus, effectCast, new Class[]{
                        EffectCastSourceComponent.class,
                        EffectCastSourceSpellComponent.class
                    });
                    int targetEntity = buffStatus.getComponent(ActiveBuffComponent.class).getTargetEntityID();
                    effectCast.setComponent(new AffectedTargetsComponent(new int[]{targetEntity}));
                    timeSinceLastRepeatingEffect = 0;
                }
                buffStatus.setComponent(new TimeSinceLastRepeatingEffectComponent(timeSinceLastRepeatingEffect));
            }
        }
    }
}
