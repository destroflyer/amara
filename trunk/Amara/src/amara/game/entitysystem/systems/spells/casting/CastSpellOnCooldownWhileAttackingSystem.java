/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.input.casts.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.CastTypeComponent.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;

/**
 *
 * @author Carl
 */
public class CastSpellOnCooldownWhileAttackingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellOnCooldownWhileAttackingComponent.class, AggroTargetComponent.class)){
            int spellIndex = entityWorld.getComponent(casterEntity, CastSpellOnCooldownWhileAttackingComponent.class).getSpellIndex();
            int spellEntity = entityWorld.getComponent(casterEntity, SpellsComponent.class).getSpellsEntities()[spellIndex];
            if(!entityWorld.hasComponent(spellEntity, RemainingCooldownComponent.class)){
                int castInformationEntity = entityWorld.createEntity();
                int targetEntity = entityWorld.getComponent(casterEntity, AggroTargetComponent.class).getTargetEntity();
                Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                CastType castType = entityWorld.getComponent(spellEntity, CastTypeComponent.class).getCastType();
                switch(castType){
                    case SINGLE_TARGET:
                        entityWorld.setComponent(castInformationEntity, new TargetComponent(targetEntity));
                        break;
                    
                    case POSITIONAL_SKILLSHOT:
                        entityWorld.setComponent(castInformationEntity, new PositionComponent(targetPosition.clone()));
                        break;
                    
                    case LINEAR_SKILLSHOT:
                        Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
                        entityWorld.setComponent(castInformationEntity, new DirectionComponent(targetPosition.subtract(casterPosition)));
                        break;
                }
                ExecutePlayerCommandsSystem.castSpell(entityWorld, casterEntity, new CastSpellComponent(spellEntity, castInformationEntity));
            }
        }
    }
}
