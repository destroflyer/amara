package amara.applications.ingame.entitysystem.systems.effects.buffs;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.*;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.general.EntityArrayUtil;
import amara.libraries.entitysystem.*;

public class ApplyRemoveBuffsSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveBuffComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int buffEntity = entityWorld.getComponent(effectImpactEntity, RemoveBuffComponent.class).getBuffEntity();
            removeBuff(entityWorld, targetEntity, buffEntity);
        }
    }

    public static void removeAllBuffs(EntityWorld entityWorld, int targetEntity){
        BuffsComponent buffsComponent = entityWorld.getComponent(targetEntity, BuffsComponent.class);
        if (buffsComponent != null) {
            for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                removeBuff(entityWorld, targetEntity, buffEntity);
            }
        }
    }

    public static void removeBuff(EntityWorld entityWorld, int targetEntity, int buffEntity) {
        BuffsComponent buffsComponent = entityWorld.getComponent(targetEntity, BuffsComponent.class);
        if (buffsComponent != null) {
            int[] buffStatusEntities = buffsComponent.getBuffStatusEntities();
            for (int buffStatusEntity : buffStatusEntities) {
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                if (activeBuffComponent.getBuffEntity() == buffEntity) {
                    int[] newBuffStatusEntities = EntityArrayUtil.remove(buffStatusEntities, buffStatusEntity, true);
                    entityWorld.removeEntity(buffStatusEntity);
                    entityWorld.setComponent(targetEntity, new BuffsComponent(newBuffStatusEntities));
                    entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
                    OnBuffRemoveEffectTriggersComponent onBuffRemoveEffectTriggersComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), OnBuffRemoveEffectTriggersComponent.class);
                    if (onBuffRemoveEffectTriggersComponent != null) {
                        EffectTriggerUtil.triggerEffects(entityWorld, onBuffRemoveEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
                    }
                    break;
                }
            }
        }
    }
}
