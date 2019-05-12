/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.physics;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.physics.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddCollisiongGroupsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddCollisionGroupsComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            CollisionGroupComponent oldCollisionGroupComponent = entityWorld.getComponent(targetEntity, CollisionGroupComponent.class);
            AddCollisionGroupsComponent addCollisionGroupsComponent = entityWrapper.getComponent(AddCollisionGroupsComponent.class);
            long targetOf = (oldCollisionGroupComponent.getTargetOf() | addCollisionGroupsComponent.getTargetOf());
            long targets = (oldCollisionGroupComponent.getTargets() | addCollisionGroupsComponent.getTargets());
            entityWorld.setComponent(targetEntity, new CollisionGroupComponent(targetOf, targets));
        }
    }
}
