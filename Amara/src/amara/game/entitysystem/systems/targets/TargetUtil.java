/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.targets;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.targets.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class TargetUtil{
    
    public static boolean isValidTarget(EntityWorld entityWorld, int sourceEntity, int targetEntity, int targetRulesEntity){
        boolean isValid = false;
        if(entityWorld.hasComponent(targetEntity, IsTargetableComponent.class)){
            TeamComponent sourceTeamComponent = entityWorld.getComponent(sourceEntity, TeamComponent.class);
            TeamComponent targetTeamComponent = entityWorld.getComponent(targetEntity, TeamComponent.class);
            if((sourceTeamComponent != null) && (targetTeamComponent != null)){
                isValid = false;
                if(entityWorld.getComponent(targetRulesEntity, AcceptAlliesComponent.class) != null){
                    isValid |= (sourceTeamComponent.getTeamEntity() == targetTeamComponent.getTeamEntity());
                }
                if(entityWorld.getComponent(targetRulesEntity, AcceptEnemiesComponent.class) != null){
                    isValid |= (sourceTeamComponent.getTeamEntity() != targetTeamComponent.getTeamEntity());
                }
            }
            else{
                isValid = true;
            }
        }
        return isValid;
    }
}
