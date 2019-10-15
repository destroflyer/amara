/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.buffs.areas;

import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBuffAreasSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBuffAreaComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int buffAreaEntity = entityWorld.getComponent(effectImpactEntity, AddBuffAreaComponent.class).getBuffAreaEntity();
            entityWorld.setComponent(buffAreaEntity, new AreaOriginComponent(targetEntity));
            entityWorld.setComponent(buffAreaEntity, new HitboxActiveComponent());
            EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
            if(effectSourceComponent != null){
                entityWorld.setComponent(buffAreaEntity, new AreaSourceComponent(effectSourceComponent.getSourceEntity()));
            }
        }
    }
}
