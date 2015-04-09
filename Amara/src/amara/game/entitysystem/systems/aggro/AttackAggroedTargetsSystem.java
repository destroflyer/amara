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
        for(int entity : entityWorld.getEntitiesWithAll(AutoAttackComponent.class, TargetsInAggroRangeComponent.class))
        {
            int[] aggroedTargets = entityWorld.getComponent(entity, TargetsInAggroRangeComponent.class).getTargets();
            if(aggroedTargets.length > 0){
                AggroUtil.tryDrawAggro(entityWorld, entity, aggroedTargets[0]);
            }
        }
    }
}
