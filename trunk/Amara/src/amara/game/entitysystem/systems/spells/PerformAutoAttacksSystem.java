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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AggroTargetComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AggroTargetComponent.class)){
            entityWorld.setComponent(entity, new AutoAttackTargetComponent());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AggroTargetComponent.class)){
            entityWorld.setComponent(entity, new AutoAttackTargetComponent());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            entityWorld.removeComponent(entity, AutoAttackTargetComponent.class);
        }
        observer.reset();
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(AggroTargetComponent.class))){
            int targetEntity = entityWrapper.getComponent(AggroTargetComponent.class).getTargetEntity();
            if(isAttackable(entityWorld, entityWrapper.getId(), targetEntity)){
                if(entityWrapper.hasComponent(AutoAttackTargetComponent.class)){
                    int autoAttackEntity = entityWrapper.getComponent(AutoAttackComponent.class).getAutoAttackEntity();
                    if(!entityWrapper.hasComponent(IsCastingComponent.class)){
                        int castInformationEntity = entityWorld.createEntity();
                        entityWorld.setComponent(castInformationEntity, new TargetComponent(targetEntity));
                        ExecutePlayerCommandsSystem.castSpell(entityWorld, entityWrapper.getId(), new CastSpellComponent(autoAttackEntity, castInformationEntity));
                    }
                }
            }
            else{
                entityWrapper.removeComponent(AggroTargetComponent.class);
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
