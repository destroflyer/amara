/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.conditions;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.conditions.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ConditionUtil{
    
    public static boolean isConditionMet(EntityWorld entityWorld, int conditionEntity, int targetEntity){
        OrConditionsComponent orConditionComponent = entityWorld.getComponent(conditionEntity, OrConditionsComponent.class);
        if(orConditionComponent != null){
            boolean isAtLeastOneConditionMet = false;
            for(int orConditionEntity : orConditionComponent.getConditionEntities()){
                if(isConditionMet(entityWorld, orConditionEntity, targetEntity)){
                    isAtLeastOneConditionMet = true;
                    break;
                }
            }
            if(!isAtLeastOneConditionMet){
                return false;
            }
        }
        HasBuffConditionComponent hasBuffConditionComponent = entityWorld.getComponent(conditionEntity, HasBuffConditionComponent.class);
        if(hasBuffConditionComponent != null){
            for(int buffEntity : hasBuffConditionComponent.getBuffEntities()){
                boolean hasBuff = false;
                for(int buffStatusEntity : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)){
                    ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                    if((activeBuffComponent.getTargetEntity() == targetEntity) && (activeBuffComponent.getBuffEntity() == buffEntity)){
                        hasBuff = true;
                        break;
                    }
                }
                if(!hasBuff){
                    return false;
                }
            }
        }
        HasHealthPortionConditionComponent hasHealthPortionConditionComponent = entityWorld.getComponent(conditionEntity, HasHealthPortionConditionComponent.class);
        if(hasHealthPortionConditionComponent != null){
            HealthComponent healthComponent = entityWorld.getComponent(targetEntity, HealthComponent.class);
            MaximumHealthComponent maximumHealthComponent = entityWorld.getComponent(targetEntity, MaximumHealthComponent.class);
            if((healthComponent != null) && (maximumHealthComponent != null)){
                float healthPortion = (healthComponent.getValue() / maximumHealthComponent.getValue());
                if(healthPortion == hasHealthPortionConditionComponent.getPortion()){
                    if(!hasHealthPortionConditionComponent.isAllowEqual()){
                        return false;
                    }
                }
                else if((healthPortion > hasHealthPortionConditionComponent.getPortion()) == hasHealthPortionConditionComponent.isLessOrMore()){
                    return false;
                }
            }
        }
        return true;
    }
}
