/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckAggroTargetAttackibilitySystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAny(AggroTargetComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            if(!isAttackable(entityWorld, entity, targetEntity)){
                entityWorld.removeComponent(entity, AggroTargetComponent.class);
            }
        }
    }
    
    public static boolean isAttackable(EntityWorld entityWorld, int attackingEntity, int targetEntity){
        int autoAttackEntity = entityWorld.getComponent(attackingEntity, AutoAttackComponent.class).getAutoAttackEntity();
        int targetRulesEntity = entityWorld.getComponent(autoAttackEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
        return TargetUtil.isValidTarget(entityWorld, attackingEntity, targetEntity, targetRulesEntity);
    }
}
