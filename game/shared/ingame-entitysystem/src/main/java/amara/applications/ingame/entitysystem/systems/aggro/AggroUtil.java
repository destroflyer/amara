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
public class AggroUtil {

    public static void tryDrawAggro(EntityWorld entityWorld, int entity, int targetEntity) {
        if (!entityWorld.hasComponent(entity, AggroTargetComponent.class)) {
            drawAggro(entityWorld, entity, targetEntity);
        }
    }

    public static void drawAggro(EntityWorld entityWorld, int entity, int targetEntity) {
        if (entityWorld.hasAllComponents(entity, AutoAttackComponent.class, IsAliveComponent.class)) {
            if (entityWorld.hasComponent(entity, AttackMoveComponent.class) || (!entityWorld.hasComponent(entity, MovementComponent.class))) {
                if (isAttackable(entityWorld, entity, targetEntity)) {
                    entityWorld.setComponent(entity, new AggroTargetComponent(targetEntity));
                }
            }
        }
    }

    public static boolean isAttackable(EntityWorld entityWorld, int attackingEntity, int targetEntity) {
        int autoAttackEntity = entityWorld.getComponent(attackingEntity, AutoAttackComponent.class).getAutoAttackEntity();
        int targetRulesEntity = entityWorld.getComponent(autoAttackEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
        return TargetUtil.isValidTarget(entityWorld, attackingEntity, targetEntity, targetRulesEntity);
    }
}
