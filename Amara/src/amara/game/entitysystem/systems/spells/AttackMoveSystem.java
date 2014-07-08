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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AttackMoveComponent.class, AggroTargetComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AttackMoveComponent.class)){
            walk(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            walk(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void walk(EntityWorld entityWorld, int entity){
        AttackMoveComponent attackMoveComponent = entityWorld.getComponent(entity, AttackMoveComponent.class);
        if(attackMoveComponent != null){
            ExecutePlayerCommandsSystem.walk(entityWorld, entity, attackMoveComponent.getTargetEntity(), -1);
        }
    }
}
