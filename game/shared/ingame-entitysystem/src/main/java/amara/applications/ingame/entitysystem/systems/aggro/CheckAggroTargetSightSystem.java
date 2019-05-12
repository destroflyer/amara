/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.units.TeamVisionSystem;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckAggroTargetSightSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(AggroTargetComponent.class, SightRangeComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            if(!TeamVisionSystem.hasTeamSight(entityWorld, entity, targetEntity)){
                entityWorld.removeComponent(entity, AggroTargetComponent.class);
                UnitUtil.cancelMovement(entityWorld, entity);
            }
        }
    }
}
