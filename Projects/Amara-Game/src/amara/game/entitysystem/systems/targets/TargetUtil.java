/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.targets;

import amara.game.entitysystem.components.targets.*;
import amara.game.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class TargetUtil{
    
    public static boolean isValidTarget(EntityWorld entityWorld, int sourceEntity, int targetEntity, int targetRulesEntity){
        boolean isValid;
        if(entityWorld.hasComponent(targetRulesEntity, RequireProjectileComponent.class)){
            isValid = entityWorld.hasComponent(targetEntity, IsProjectileComponent.class);
        }
        else{
            isValid = entityWorld.hasComponent(targetEntity, IsTargetableComponent.class);
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
        return isValid;
    }
}
