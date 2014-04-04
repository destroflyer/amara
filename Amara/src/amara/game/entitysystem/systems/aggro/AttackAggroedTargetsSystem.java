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
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(AutoAttackComponent.class, TargetsInAggroRangeComponent.class)))
        {
            if((!entityWrapper.hasComponent(AutoAttackTargetComponent.class)) && (!entityWrapper.hasComponent(MovementComponent.class))){
                int[] aggroedTargets = entityWrapper.getComponent(TargetsInAggroRangeComponent.class).getTargets();
                if(aggroedTargets.length > 0){
                    entityWrapper.setComponent(new AutoAttackTargetComponent(aggroedTargets[0]));
                }
            }
        }
    }
}
