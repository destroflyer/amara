/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.buffs.areas;

import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyRemoveBuffAreasSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveBuffAreaComponent.class)))
        {
            int buffAreaEntity = entityWrapper.getComponent(RemoveBuffAreaComponent.class).getBuffAreaEntity();
            entityWorld.removeComponent(buffAreaEntity, AreaOriginComponent.class);
            entityWorld.removeComponent(buffAreaEntity, HitboxActiveComponent.class);
            entityWorld.removeComponent(buffAreaEntity, PositionComponent.class);
            int buffEntity = entityWorld.getComponent(buffAreaEntity, AreaBuffComponent.class).getBuffEntity();
            for(int buffStatus : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class);
                if(activeBuffComponent.getBuffEntity() == buffEntity){
                    entityWorld.setComponent(buffStatus, new RemoveFromTargetComponent());
                }
            }
        }
    }
}