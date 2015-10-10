/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;

/**
 *
 * @author Carl
 */
public class AttackMoveSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AttackMoveComponent.class, AggroTargetComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(AttackMoveComponent.class)){
            walk(entityWorld, entity);
        }
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            walk(entityWorld, entity);
        }
    }
    
    private void walk(EntityWorld entityWorld, Integer entity){
        AttackMoveComponent attackMoveComponent = entityWorld.getComponent(entity, AttackMoveComponent.class);
        if(attackMoveComponent != null){
            ExecutePlayerCommandsSystem.tryWalk(entityWorld, entity, attackMoveComponent.getTargetEntity(), -1);
        }
    }
}
