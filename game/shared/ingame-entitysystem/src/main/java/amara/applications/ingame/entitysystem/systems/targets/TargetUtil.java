package amara.applications.ingame.entitysystem.systems.targets;

import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.*;

public class TargetUtil {

    public static boolean isValidTarget(EntityWorld entityWorld, int sourceEntity, int targetEntity, int targetRulesEntity) {
        boolean isValid;
        if (entityWorld.hasComponent(targetRulesEntity, RequireProjectileComponent.class)) {
            isValid = entityWorld.hasComponent(targetEntity, IsProjectileComponent.class);
        } else if (entityWorld.hasComponent(targetRulesEntity, AcceptUntargetableComponent.class)) {
            isValid = true;
        } else {
            isValid = entityWorld.hasComponent(targetEntity, IsTargetableComponent.class);
        }
        if (isValid) {
            if (entityWorld.hasComponent(targetRulesEntity, RequireCharacterComponent.class)) {
                isValid = entityWorld.hasComponent(targetEntity, IsCharacterComponent.class);
            }
            if(isValid){
                TeamComponent sourceTeamComponent = entityWorld.getComponent(sourceEntity, TeamComponent.class);
                TeamComponent targetTeamComponent = entityWorld.getComponent(targetEntity, TeamComponent.class);
                if((sourceTeamComponent != null) && (targetTeamComponent != null)){
                    isValid = false;
                    if(entityWorld.hasComponent(targetRulesEntity, AcceptAlliesComponent.class)){
                        isValid |= (sourceTeamComponent.getTeamEntity() == targetTeamComponent.getTeamEntity());
                    }
                    if(entityWorld.hasComponent(targetRulesEntity, AcceptEnemiesComponent.class)){
                        isValid |= (sourceTeamComponent.getTeamEntity() != targetTeamComponent.getTeamEntity());
                    }
                }
            }
            if (isValid) {
                RequireAllBuffsComponent requireAllBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireAllBuffsComponent.class);
                if ((requireAllBuffsComponent != null) && (!BuffUtil.hasAllBuffs(entityWorld, targetEntity, requireAllBuffsComponent.getBuffEntities()))) {
                    return false;
                }
                RequireAnyBuffsComponent requireAnyBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireAnyBuffsComponent.class);
                if ((requireAnyBuffsComponent != null) && (!BuffUtil.hasAnyBuffs(entityWorld, targetEntity, requireAnyBuffsComponent.getBuffEntities()))) {
                    return false;
                }
                RequireNoBuffsComponent requireNoBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireNoBuffsComponent.class);
                if ((requireNoBuffsComponent != null) && BuffUtil.hasAnyBuffs(entityWorld, targetEntity, requireNoBuffsComponent.getBuffEntities())) {
                    return false;
                }
                RequireEntityComponent requireEntityComponent = entityWorld.getComponent(targetRulesEntity, RequireEntityComponent.class);
                if ((requireEntityComponent != null) && (targetEntity != requireEntityComponent.getEntity())) {
                    return false;
                }
            }
        }
        return isValid;
    }
}
