package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;

public class AggroUtil {

    public static void trySetAggroIfStill(EntityWorld entityWorld, int entity, int targetEntity) {
        if ((!entityWorld.hasComponent(entity, AggroTargetComponent.class)) && isAttackable(entityWorld, entity, targetEntity)) {
            setAggroIfStill(entityWorld, entity, targetEntity);
        }
    }

    public static void setAggroIfStill(EntityWorld entityWorld, int entity, int targetEntity) {
        if (entityWorld.hasComponent(entity, AttackMoveComponent.class) || (!entityWorld.hasComponent(entity, MovementComponent.class))) {
            entityWorld.setComponent(entity, new AggroTargetComponent(targetEntity));
        }
    }

    public static boolean tryCancelActionAndSetAggro(EntityWorld entityWorld, int entity, int targetEntity) {
        if (isAttackable(entityWorld, entity, targetEntity)) {
            if (UnitUtil.tryCancelAction(entityWorld, entity)) {
                entityWorld.setComponent(entity, new AggroTargetComponent(targetEntity));
                return true;
            }
        }
        return false;
    }

    public static boolean isAttackable(EntityWorld entityWorld, int attackingEntity, int targetEntity) {
        if (entityWorld.hasComponent(attackingEntity, IsAliveComponent.class)) {
            AutoAttackComponent autoAttackComponent = entityWorld.getComponent(attackingEntity, AutoAttackComponent.class);
            if (autoAttackComponent != null) {
                int targetRulesEntity = entityWorld.getComponent(autoAttackComponent.getAutoAttackEntity(), SpellTargetRulesComponent.class).getTargetRulesEntity();
                return TargetUtil.isValidTarget(entityWorld, attackingEntity, targetEntity, targetRulesEntity);
            }
        }
        return false;
    }
}
