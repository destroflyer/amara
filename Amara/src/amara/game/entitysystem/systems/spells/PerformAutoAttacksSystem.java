/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.input.casts.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.game.entitysystem.systems.targets.TargetUtil;

/**
 *
 * @author Carl
 */
public class PerformAutoAttacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(AutoAttackTargetComponent.class))){
            int targetEntity = entityWrapper.getComponent(AutoAttackTargetComponent.class).getTargetEntityID();
            if(isAttackable(entityWorld, entityWrapper.getId(), targetEntity)){
                int autoAttackEntity = entityWrapper.getComponent(AutoAttackComponent.class).getAutoAttackEntity();
                if(!entityWorld.hasComponent(autoAttackEntity, RemainingCooldownComponent.class)){
                    int castInformationEntity = entityWorld.createEntity();
                    entityWorld.setComponent(castInformationEntity, new TargetComponent(targetEntity));
                    ExecutePlayerCommandsSystem.castSpell(entityWorld, entityWrapper.getId(), new CastSpellComponent(autoAttackEntity, castInformationEntity));
                }
            }
            else{
                entityWrapper.removeComponent(AutoAttackTargetComponent.class);
            }
        }
    }
    
    public static boolean isAttackable(EntityWorld entityWorld, int attackingEntity, int targetEntity){
        int autoAttackEntity = entityWorld.getComponent(attackingEntity, AutoAttackComponent.class).getAutoAttackEntity();
        int targetRulesEntity = entityWorld.getComponent(autoAttackEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
        return TargetUtil.isValidTarget(entityWorld, attackingEntity, targetEntity, targetRulesEntity);
    }
}
