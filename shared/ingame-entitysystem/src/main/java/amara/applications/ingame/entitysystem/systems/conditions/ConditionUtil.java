package amara.applications.ingame.entitysystem.systems.conditions;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.conditions.*;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntityWorld;

public class ConditionUtil {

    public static boolean isConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity) {
        return (isOrConditionMet(entityWorld, conditionEntity, targetEntity)
             && isHasBuffConditionMet(entityWorld, conditionEntity, targetEntity)
             && isHasHealthPortionConditionMet(entityWorld, conditionEntity, targetEntity)
             && isNameAmountConditionMet(entityWorld, conditionEntity, targetEntity)
             && isIsCharacterConditionMet(entityWorld, conditionEntity, targetEntity)
             && isNotExistingConditionMet(entityWorld, conditionEntity));
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
            return BuffUtil.hasAllBuffs(entityWorld, targetEntity, hasBuffConditionComponent.getBuffEntities());
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

    private static boolean isNotExistingConditionMet(EntityWorld entityWorld, int conditionEntity) {
        return !entityWorld.hasComponent(conditionEntity, NotExistingConditionComponent.class);
    }
}
