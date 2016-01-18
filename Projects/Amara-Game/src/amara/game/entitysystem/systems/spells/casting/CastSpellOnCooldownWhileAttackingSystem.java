/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.CastTypeComponent.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CastSpellOnCooldownWhileAttackingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellOnCooldownWhileAttackingComponent.class, AggroTargetComponent.class)){
            for(int spellIndex : entityWorld.getComponent(casterEntity, CastSpellOnCooldownWhileAttackingComponent.class).getSpellIndices()){
                int spellEntity = entityWorld.getComponent(casterEntity, SpellsComponent.class).getSpellsEntities()[spellIndex];
                if(!entityWorld.hasComponent(spellEntity, RemainingCooldownComponent.class)){
                    int targetEntity = -1;
                    CastType castType = entityWorld.getComponent(spellEntity, CastTypeComponent.class).getCastType();
                    if(castType != CastType.SELFCAST){
                        int aggoTargetEntity = entityWorld.getComponent(casterEntity, AggroTargetComponent.class).getTargetEntity();
                        Vector2f aggroTargetPosition = entityWorld.getComponent(aggoTargetEntity, PositionComponent.class).getPosition();
                        switch(castType){
                            case SINGLE_TARGET:
                                targetEntity = aggoTargetEntity;
                                break;

                            case POSITIONAL_SKILLSHOT:
                                targetEntity = entityWorld.createEntity();
                                entityWorld.setComponent(targetEntity, new PositionComponent(aggroTargetPosition.clone()));
                                break;

                            case LINEAR_SKILLSHOT:
                                targetEntity = entityWorld.createEntity();
                                Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
                                entityWorld.setComponent(targetEntity, new DirectionComponent(aggroTargetPosition.subtract(casterPosition)));
                                break;
                        }
                    }
                    ExecutePlayerCommandsSystem.castSpell(entityWorld, casterEntity, new CastSpellComponent(spellEntity, targetEntity));
                }
            }
        }
    }
}
