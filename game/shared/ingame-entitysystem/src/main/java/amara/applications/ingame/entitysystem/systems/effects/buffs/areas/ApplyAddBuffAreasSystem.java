/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.buffs.areas;

import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBuffAreasSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBuffAreaComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            int buffAreaEntity = entityWrapper.getComponent(AddBuffAreaComponent.class).getBuffAreaEntity();
            entityWorld.setComponent(buffAreaEntity, new AreaOriginComponent(targetEntity));
            EffectCastSourceComponent effectCastSourceComponent = entityWrapper.getComponent(EffectCastSourceComponent.class);
            if(effectCastSourceComponent != null){
                entityWorld.setComponent(buffAreaEntity, new AreaSourceComponent(effectCastSourceComponent.getSourceEntity()));
            }
            entityWorld.setComponent(buffAreaEntity, new HitboxActiveComponent());
        }
    }
}
