/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;

/**
 *
 * @author Carl
 */
public class PerformAutoAttacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(AggroTargetComponent.class))){
            int targetEntity = entityWrapper.getComponent(AggroTargetComponent.class).getTargetEntity();
            if(!entityWrapper.hasComponent(IsWalkingToAggroTargetComponent.class)){
                int autoAttackEntity = entityWrapper.getComponent(AutoAttackComponent.class).getAutoAttackEntity();
                ExecutePlayerCommandsSystem.castSpell(entityWorld, entityWrapper.getId(), new CastSpellComponent(autoAttackEntity, targetEntity));
            }
        }
    }
}
