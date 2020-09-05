package amara.applications.ingame.entitysystem.systems.effects.buffs;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.OnBuffAddEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.*;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.general.EntityArrayUtil;
import amara.libraries.entitysystem.*;

public class ApplyAddBuffsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBuffComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            AddBuffComponent addBuffComponent = entityWorld.getComponent(effectImpactEntity, AddBuffComponent.class);
            for (int buffEntity : addBuffComponent.getBuffEntities()) {
                int buffStatusEntity = addBuff(entityWorld, targetEntity, buffEntity, addBuffComponent.getDuration());
                EntityUtil.transferComponents(entityWorld, effectImpactEntity, buffStatusEntity, new Class[] {
                        EffectSourceComponent.class,
                        EffectSourceSpellComponent.class
                });
            }
        }
    }

    public static int addBuff(EntityWorld entityWorld, int targetEntity, int buffEntity) {
        return addBuff(entityWorld, targetEntity, buffEntity, -1);
    }

    public static int addBuff(EntityWorld entityWorld, int targetEntity, int buffEntity, float duration) {
        int[] buffStatusEntities;
        BuffsComponent buffsComponent = entityWorld.getComponent(targetEntity, BuffsComponent.class);
        if (buffsComponent != null) {
            buffStatusEntities = buffsComponent.getBuffStatusEntities();
        } else {
            buffStatusEntities = new int[0];
        }
        int buffStatusEntity = -1;
        for (int currentBuffStatusEntity : buffStatusEntities) {
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(currentBuffStatusEntity, ActiveBuffComponent.class);
            if (activeBuffComponent.getBuffEntity() == buffEntity) {
                buffStatusEntity = currentBuffStatusEntity;
                break;
            }
        }
        if (buffStatusEntity == -1) {
            buffStatusEntity = entityWorld.createEntity();
            entityWorld.setComponent(buffStatusEntity, new ActiveBuffComponent(targetEntity, buffEntity));
            int[] newBuffStatusEntities = EntityArrayUtil.add(buffStatusEntities, buffStatusEntity);
            entityWorld.setComponent(targetEntity, new BuffsComponent(newBuffStatusEntities));
        }
        if (duration != -1) {
            entityWorld.setComponent(buffStatusEntity, new RemainingBuffDurationComponent(duration));
        } else {
            entityWorld.removeComponent(buffStatusEntity, RemainingBuffDurationComponent.class);
        }
        entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        OnBuffAddEffectTriggersComponent onBuffAddEffectTriggersComponent = entityWorld.getComponent(buffEntity, OnBuffAddEffectTriggersComponent.class);
        if (onBuffAddEffectTriggersComponent != null) {
            EffectTriggerUtil.triggerEffects(entityWorld, onBuffAddEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
        }
        return buffStatusEntity;
    }
}
