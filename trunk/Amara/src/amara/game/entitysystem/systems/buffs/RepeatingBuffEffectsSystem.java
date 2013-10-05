/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.spawns.*;

/**
 *
 * @author Carl
 */
public class RepeatingBuffEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int buffStatus : entityWorld.getCurrent().getEntitiesWithAll(ActiveBuffComponent.class))
        {
            int buffID = entityWorld.getCurrent().getComponent(buffStatus, ActiveBuffComponent.class).getBuffEntityID();
            RepeatingEffectComponent repeatingEffectComponent = entityWorld.getCurrent().getComponent(buffID, RepeatingEffectComponent.class);
            if(repeatingEffectComponent != null){
                TimeSinceLastRepeatingEffectComponent timeSinceLastRepeatingEffectComponent = entityWorld.getCurrent().getComponent(buffStatus, TimeSinceLastRepeatingEffectComponent.class);
                float timeSinceLastRepeatingEffect = (((timeSinceLastRepeatingEffectComponent != null)?timeSinceLastRepeatingEffectComponent.getDuration():0) + deltaSeconds);
                if(timeSinceLastRepeatingEffect >= repeatingEffectComponent.getInterval()){
                    EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
                    effectCast.setComponent(new PrepareEffectComponent(repeatingEffectComponent.getEffectEntityID()));
                    CastSourceComponent castSourceComponent = entityWorld.getCurrent().getComponent(buffStatus, CastSourceComponent.class);
                    if(castSourceComponent != null){
                        effectCast.setComponent(new EffectSourceComponent(castSourceComponent.getSourceEntitiyID()));
                    }
                    int targetEntityID = entityWorld.getCurrent().getComponent(buffStatus, ActiveBuffComponent.class).getTargetEntityID();
                    effectCast.setComponent(new AffectedTargetsComponent(new int[]{targetEntityID}));
                    timeSinceLastRepeatingEffect = 0;
                }
                entityWorld.getCurrent().setComponent(buffStatus, new TimeSinceLastRepeatingEffectComponent(timeSinceLastRepeatingEffect));
            }
        }
    }
}
