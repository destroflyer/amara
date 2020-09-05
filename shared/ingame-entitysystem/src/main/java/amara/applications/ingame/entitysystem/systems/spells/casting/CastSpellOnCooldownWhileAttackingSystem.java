/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.spells.CastTypeComponent.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CastSpellOnCooldownWhileAttackingSystem implements EntitySystem{

    public CastSpellOnCooldownWhileAttackingSystem(CastSpellQueueSystem castSpellQueueSystem){
        this.castSpellQueueSystem = castSpellQueueSystem;
    }
    private CastSpellQueueSystem castSpellQueueSystem;
    
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
                                entityWorld.setComponent(targetEntity, new TemporaryComponent());
                                entityWorld.setComponent(targetEntity, new PositionComponent(aggroTargetPosition.clone()));
                                break;

                            case LINEAR_SKILLSHOT:
                                targetEntity = entityWorld.createEntity();
                                entityWorld.setComponent(targetEntity, new TemporaryComponent());
                                Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
                                entityWorld.setComponent(targetEntity, new DirectionComponent(aggroTargetPosition.subtract(casterPosition)));
                                break;
                        }
                    }
                    castSpellQueueSystem.enqueueSpellCast(casterEntity, spellEntity, targetEntity);
                }
            }
        }
    }
}
