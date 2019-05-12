/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellQueueSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PerformAutoAttacksSystem implements EntitySystem{

    public PerformAutoAttacksSystem(CastSpellQueueSystem castSpellQueueSystem){
        this.castSpellQueueSystem = castSpellQueueSystem;
    }
    private CastSpellQueueSystem castSpellQueueSystem;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(AggroTargetComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            if(!entityWorld.hasComponent(entity, IsWalkingToAggroTargetComponent.class)){
                int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
                castSpellQueueSystem.enqueueSpellCast(entity, autoAttackEntity, targetEntity);
            }
        }
    }
}
