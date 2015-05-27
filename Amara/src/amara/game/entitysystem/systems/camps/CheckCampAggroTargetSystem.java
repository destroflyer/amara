/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.aggro.*;

/**
 *
 * @author Carl
 */
public class CheckCampAggroTargetSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(CampComponent.class, AggroTargetComponent.class))
        {
            if(!entityWorld.hasComponent(entity, ResetCampComponent.class)){
                int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
                if(!CheckAggroTargetAttackibilitySystem.isAttackable(entityWorld, entity, targetEntity)){
                    entityWorld.setComponent(entity, new ResetCampComponent());
                }
            }
        }
    }
}
