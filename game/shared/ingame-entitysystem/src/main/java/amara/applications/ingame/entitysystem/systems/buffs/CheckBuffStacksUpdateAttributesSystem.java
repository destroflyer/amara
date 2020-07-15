package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent;
import amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent;
import amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CheckBuffStacksUpdateAttributesSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, StacksComponent.class);
        for (int stacksEntity : observer.getChanged().getEntitiesWithAny(StacksComponent.class)) {
            for (int buffStatusEntity : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)) {
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                BuffStacksComponent buffStacksComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), BuffStacksComponent.class);
                if ((buffStacksComponent != null) && (buffStacksComponent.getStacksEntity() == stacksEntity)) {
                    if (entityWorld.hasComponent(activeBuffComponent.getBuffEntity(), ContinuousAttributesPerStackComponent.class)) {
                        entityWorld.setComponent(activeBuffComponent.getTargetEntity(), new RequestUpdateAttributesComponent());
                    }
                    break;
                }
            }
        }
    }
}
