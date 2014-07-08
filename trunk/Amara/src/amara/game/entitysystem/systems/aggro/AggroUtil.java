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
public class AggroUtil{
    
    public static void drawAggro(EntityWorld entityWorld, int entity, int targetEntity){
        if(entityWorld.hasComponent(entity, AutoAttackComponent.class) && (!entityWorld.hasComponent(entity, AggroTargetComponent.class))){
            if(entityWorld.hasComponent(entity, AttackMoveComponent.class) || (!entityWorld.hasComponent(entity, MovementComponent.class))){
                entityWorld.setComponent(entity, new AggroTargetComponent(targetEntity));
            }
        }
    }
}
