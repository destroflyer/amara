/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBuffComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            AddBuffComponent addBuffComponent = entityWrapper.getComponent(AddBuffComponent.class);
            EntityWrapper buffStatus = entityWorld.getWrapped(entityWorld.createEntity());
            buffStatus.setComponent(new ActiveBuffComponent(targetEntity, addBuffComponent.getBuffEntity()));
            buffStatus.setComponent(new RemainingBuffDurationComponent(addBuffComponent.getDuration()));
        }
    }
}
