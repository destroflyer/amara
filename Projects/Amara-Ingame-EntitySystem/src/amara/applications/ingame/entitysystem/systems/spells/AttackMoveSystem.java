/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class AttackMoveSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AttackMoveComponent.class, AggroTargetComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AttackMoveComponent.class)){
            walk(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            walk(entityWorld, entity);
        }
    }
    
    private void walk(EntityWorld entityWorld, int entity){
        AttackMoveComponent attackMoveComponent = entityWorld.getComponent(entity, AttackMoveComponent.class);
        if(attackMoveComponent != null){
            ExecutePlayerCommandsSystem.tryWalk(entityWorld, entity, attackMoveComponent.getTargetEntity(), -1);
        }
    }
}
