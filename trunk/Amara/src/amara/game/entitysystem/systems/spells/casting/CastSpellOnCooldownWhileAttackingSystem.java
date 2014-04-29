/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;

/**
 *
 * @author Carl
 */
public class CastSpellOnCooldownWhileAttackingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellOnCooldownWhileAttackingComponent.class, AutoAttackTargetComponent.class)){
            int spellIndex = entityWorld.getComponent(casterEntity, CastSpellOnCooldownWhileAttackingComponent.class).getSpellIndex();
            int spellEntity = entityWorld.getComponent(casterEntity, SpellsComponent.class).getSpellsEntities()[spellIndex];
            if(!entityWorld.hasComponent(spellEntity, RemainingCooldownComponent.class)){
                int castInformationEntity = entityWorld.createEntity();
                ExecutePlayerCommandsSystem.castSpell(entityWorld, casterEntity, new CastSpellComponent(spellEntity, castInformationEntity));
            }
        }
    }
}
