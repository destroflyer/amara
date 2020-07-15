package amara.applications.ingame.entitysystem.systems.effects.buffs.areas;

import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyRemoveBuffsSystem;
import amara.libraries.entitysystem.*;

public class ApplyRemoveBuffAreasSystem implements EntitySystem {
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveBuffAreaComponent.class)) {
            int buffAreaEntity = entityWorld.getComponent(effectImpactEntity, RemoveBuffAreaComponent.class).getBuffAreaEntity();
            entityWorld.removeComponent(buffAreaEntity, TransformOriginComponent.class);
            entityWorld.removeComponent(buffAreaEntity, HitboxActiveComponent.class);
            entityWorld.removeComponent(buffAreaEntity, PositionComponent.class);
            int buffEntity = entityWorld.getComponent(buffAreaEntity, AreaBuffComponent.class).getBuffEntity();
            for (int buffStatusEntity : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)) {
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                if (activeBuffComponent.getBuffEntity() == buffEntity) {
                    ApplyRemoveBuffsSystem.removeBuff(entityWorld, activeBuffComponent.getTargetEntity(), buffEntity);
                }
            }
        }
    }
}
