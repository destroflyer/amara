/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import amara.game.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class AggroUtil{
    
    public static void tryDrawAggro(EntityWorld entityWorld, int entity, int targetEntity){
        if(!entityWorld.hasComponent(entity, AggroTargetComponent.class)){
            drawAggro(entityWorld, entity, targetEntity);
        }
    }
    
    public static void drawAggro(EntityWorld entityWorld, int entity, int targetEntity){
        if(entityWorld.hasAllComponents(entity, AutoAttackComponent.class, IsAliveComponent.class)){
            if(entityWorld.hasComponent(entity, AttackMoveComponent.class) || (!entityWorld.hasComponent(entity, MovementComponent.class))){
                entityWorld.setComponent(entity, new AggroTargetComponent(targetEntity));
            }
        }
    }
}
