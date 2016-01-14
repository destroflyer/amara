/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import java.util.LinkedList;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class AttackAggroedTargetsSystem implements EntitySystem{
    
    private int tmpHighestPriority;
    private LinkedList<Integer> tmpHighestPriorityTargets = new LinkedList<Integer>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(AutoAttackComponent.class, TargetsInAggroRangeComponent.class)){
            int[] aggroedTargetEntities = entityWorld.getComponent(entity, TargetsInAggroRangeComponent.class).getTargetEntities();
            if(aggroedTargetEntities.length > 0){
                tmpHighestPriority = -1;
                for(int targetEntity : aggroedTargetEntities){
                    int aggroPriority = 0;
                    AggroPriorityComponent aggroPriorityComponent = entityWorld.getComponent(targetEntity, AggroPriorityComponent.class);
                    if(aggroPriorityComponent != null){
                        aggroPriority = aggroPriorityComponent.getPriority();
                    }
                    if(aggroPriority > tmpHighestPriority){
                        tmpHighestPriority = aggroPriority;
                        tmpHighestPriorityTargets.clear();
                        tmpHighestPriorityTargets.add(targetEntity);
                    }
                    else if(aggroPriority == tmpHighestPriority){
                        tmpHighestPriorityTargets.add(targetEntity);
                    }
                }
                int targetEntity = tmpHighestPriorityTargets.get((int) (Math.random() * tmpHighestPriorityTargets.size()));
                AggroUtil.tryDrawAggro(entityWorld, entity, targetEntity);
            }
        }
    }
}
