/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class AttackAggroedTargetsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(AutoAttackComponent.class, TargetsInAggroRangeComponent.class)){
            int[] aggroedTargetEntities = entityWorld.getComponent(entity, TargetsInAggroRangeComponent.class).getTargetEntities();
            if(aggroedTargetEntities.length > 0){
                AggroUtil.tryDrawAggro(entityWorld, entity, aggroedTargetEntities[0]);
            }
        }
    }
}
