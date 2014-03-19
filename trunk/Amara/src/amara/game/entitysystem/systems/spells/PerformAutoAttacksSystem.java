/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.spells.casting.*;

/**
 *
 * @author Carl
 */
public class PerformAutoAttacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(AutoAttackTargetComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(AutoAttackTargetComponent.class).getTargetEntityID();
            if(isAttackable(entityWorld, entityWrapper.getId(), targetEntity)){
                int autoAttackEntity = entityWrapper.getComponent(AutoAttackComponent.class).getAutoAttackEntityID();
                if(!entityWorld.hasComponent(autoAttackEntity, RemainingCooldownComponent.class)){
                    CastSingleTargetSpellSystem.castSingleTargetSpell(entityWorld, entityWrapper.getId(), autoAttackEntity, targetEntity);
                    SetCooldownOnCastingSystem.setOnCooldown(entityWorld, autoAttackEntity);
                    int animationEntity = entityWrapper.getComponent(AutoAttackAnimationComponent.class).getAnimationEntity();
                    float attackSpeed = entityWrapper.getComponent(AttackSpeedComponent.class).getValue();
                    entityWorld.setComponent(animationEntity, new LoopDurationComponent(1 / attackSpeed));
                    entityWorld.setComponent(animationEntity, new RemainingLoopsComponent(1));
                    entityWrapper.setComponent(new AnimationComponent(animationEntity));
                }
            }
            else{
                entityWrapper.removeComponent(AutoAttackTargetComponent.class);
            }
        }
    }
    
    public static boolean isAttackable(EntityWorld entityWorld, int attackingEntity, int targetEntity){
        if(entityWorld.hasComponent(targetEntity, IsTargetableComponent.class)){
            TeamComponent teamComponent1 = entityWorld.getComponent(attackingEntity, TeamComponent.class);
            TeamComponent teamComponent2 = entityWorld.getComponent(targetEntity, TeamComponent.class);
            if((teamComponent1 == null) || (teamComponent2 == null)){
                return true;
            }
            return (teamComponent1.getTeamEntityID() != teamComponent2.getTeamEntityID());
        }
        return false;
    }
}
