package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CheckBuffStacksUpdateAttributesSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, StacksComponent.class);
        for (int buffStatusEntity : observer.getChanged().getEntitiesWithAny(StacksComponent.class)) {
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
            if (entityWorld.hasComponent(activeBuffComponent.getBuffEntity(), ContinuousAttributesPerStackComponent.class)) {
                entityWorld.setComponent(activeBuffComponent.getTargetEntity(), new RequestUpdateAttributesComponent());
            }
        }
    }
}
