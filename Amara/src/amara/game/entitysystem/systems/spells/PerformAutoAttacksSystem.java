/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.spells.casting.*;

/**
 *
 * @author Carl
 */
public class PerformAutoAttacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(AutoAttackTargetComponent.class)))
        {
            int autoAttackEntityID = entityWrapper.getComponent(AutoAttackComponent.class).getAutoAttackEntityID();
            if(entityWorld.getCurrent().getComponent(autoAttackEntityID, RemainingCooldownComponent.class) == null){
                int targetEntityID = entityWrapper.getComponent(AutoAttackTargetComponent.class).getTargetEntityID();
                CastSingleTargetSpellSystem.castSingleTargetSpell(entityWorld, entityWrapper.getId(), autoAttackEntityID, targetEntityID);
                SetCooldownOnCastingSystem.setOnCooldown(entityWorld, autoAttackEntityID);
            }
        }
    }
}
