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
public class ApplyAddCollisiongGroupsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddCollisionGroupsComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            CollisionGroupComponent oldCollisionGroupComponent = entityWorld.getComponent(targetEntity, CollisionGroupComponent.class);
            long targetOf;
            long targets;
            if (oldCollisionGroupComponent != null) {
                targetOf = oldCollisionGroupComponent.getTargetOf();
                targets = oldCollisionGroupComponent.getTargets();
            } else {
                targetOf = 0;
                targets = 0;
            }
            AddCollisionGroupsComponent addCollisionGroupsComponent = entityWorld.getComponent(effectImpactEntity, AddCollisionGroupsComponent.class);
            targetOf |= addCollisionGroupsComponent.getTargetOf();
            targets |= addCollisionGroupsComponent.getTargets();
            entityWorld.setComponent(targetEntity, new CollisionGroupComponent(targetOf, targets));
        }
    }
}
