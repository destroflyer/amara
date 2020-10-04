package amara.applications.ingame.entitysystem.systems.conditions;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.conditions.*;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.EntityWorld;

public class ConditionUtil {

    public static boolean isConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        boolean result = (isOrConditionMet(entityWorld, conditionEntity, targetEntity)
             && isHasBuffConditionMet(entityWorld, conditionEntity, targetEntity)
             && isHasHealthPortionConditionMet(entityWorld, conditionEntity, targetEntity)
             && isNameAmountConditionMet(entityWorld, conditionEntity, targetEntity)
             && isIsCharacterConditionMet(entityWorld, conditionEntity, targetEntity)
             && isHasRuleTargetConditionMet(entityWorld, conditionEntity, targetEntity)
             && isNotExistingConditionMet(entityWorld, conditionEntity));
        return (result != entityWorld.hasComponent(conditionEntity, NotConditionComponent.class));
    }

    private static boolean isOrConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        OrConditionsComponent orConditionComponent = entityWorld.getComponent(conditionEntity, OrConditionsComponent.class);
        if (orConditionComponent != null) {
            boolean isAtLeastOneConditionMet = false;
            for (int orConditionEntity : orConditionComponent.getConditionEntities()) {
                if (isConditionMet(entityWorld, orConditionEntity, targetEntity)){
                    isAtLeastOneConditionMet = true;
                    break;
                }
            }
            return isAtLeastOneConditionMet;
        }
        return true;
    }

    private static boolean isHasBuffConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        HasBuffConditionComponent hasBuffConditionComponent = entityWorld.getComponent(conditionEntity, HasBuffConditionComponent.class);
        if (hasBuffConditionComponent != null) {
            int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, targetEntity, hasBuffConditionComponent.getBuffEntity());
            if (buffStatusEntity == -1) {
                return false;
            }
            if (hasBuffConditionComponent.getRequiredStacks() != 0) {
                StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
                return ((stacksComponent != null) && (stacksComponent.getStacks() >= hasBuffConditionComponent.getRequiredStacks()));
            }
        }
        return true;
    }

    private static boolean isHasHealthPortionConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        HasHealthPortionConditionComponent hasHealthPortionConditionComponent = entityWorld.getComponent(conditionEntity, HasHealthPortionConditionComponent.class);
        if (hasHealthPortionConditionComponent != null) {
            HealthComponent healthComponent = entityWorld.getComponent(targetEntity, HealthComponent.class);
            MaximumHealthComponent maximumHealthComponent = entityWorld.getComponent(targetEntity, MaximumHealthComponent.class);
            if ((healthComponent != null) && (maximumHealthComponent != null)) {
                float healthPortion = (healthComponent.getValue() / maximumHealthComponent.getValue());
                if (healthPortion == hasHealthPortionConditionComponent.getPortion()) {
                    return hasHealthPortionConditionComponent.isAllowEqual();
                } else {
                    return (healthPortion > hasHealthPortionConditionComponent.getPortion()) != hasHealthPortionConditionComponent.isLessOrMore();
                }
            }
        }
        return true;
    }

    private static boolean isNameAmountConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        NameAmountConditionComponent nameAmountConditionComponent = entityWorld.getComponent(conditionEntity, NameAmountConditionComponent.class);
        if (nameAmountConditionComponent != null) {
            int amount = 0;
            String targetName = entityWorld.getComponent(targetEntity, NameComponent.class).getName();
            for (int entity : entityWorld.getEntitiesWithAny(NameComponent.class)) {
                String name = entityWorld.getComponent(entity, NameComponent.class).getName();
                if (name.equals(targetName)) {
                    amount++;
                }
            }
            return (amount <= nameAmountConditionComponent.getMaximum());
        }
        return true;
    }

    private static boolean isIsCharacterConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        IsCharacterConditionComponent isCharacterConditionComponent = entityWorld.getComponent(conditionEntity, IsCharacterConditionComponent.class);
        if (isCharacterConditionComponent != null) {
            return entityWorld.hasComponent(targetEntity, IsCharacterComponent.class);
        }
        return true;
    }

    private static boolean isHasRuleTargetConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        HasRuleTargetConditionComponent hasRuleTargetConditionComponent = entityWorld.getComponent(conditionEntity, HasRuleTargetConditionComponent.class);
        if (hasRuleTargetConditionComponent != null) {
            return TargetUtil.hasValidTarget(entityWorld, targetEntity, hasRuleTargetConditionComponent.getTargetRulesEntity());
        }
        return true;
    }

    private static boolean isNotExistingConditionMet(EntityWorld entityWorld, int conditionEntity) {
        return !entityWorld.hasComponent(conditionEntity, NotExistingConditionComponent.class);
    }
}
