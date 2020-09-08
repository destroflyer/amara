package amara.applications.ingame.entitysystem.systems.effects.units;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.units.AddShieldComponent;
import amara.applications.ingame.entitysystem.components.units.ShieldsComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ActiveShieldComponent;
import amara.applications.ingame.entitysystem.components.units.shields.RemainingShieldDurationComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ShieldAmountComponent;
import amara.applications.ingame.entitysystem.systems.general.EntityArrayUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyAddShieldSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddShieldComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            AddShieldComponent addShieldComponent = entityWorld.getComponent(effectImpactEntity, AddShieldComponent.class);
            addShield(entityWorld, targetEntity, addShieldComponent.getShieldEntity(), addShieldComponent.getDuration());
        }
    }

    public static void addShield(EntityWorld entityWorld, int targetEntity, int shieldEntity, float duration) {
        int[] oldShieldStatusEntities;
        ShieldsComponent shieldsComponent = entityWorld.getComponent(targetEntity, ShieldsComponent.class);
        if (shieldsComponent != null) {
            oldShieldStatusEntities = shieldsComponent.getShieldStatusEntities();
        } else {
            oldShieldStatusEntities = new int[0];
        }
        int shieldStatusEntity = -1;
        for (int currentShieldStatusEntity : oldShieldStatusEntities) {
            ActiveShieldComponent activeShieldComponent = entityWorld.getComponent(currentShieldStatusEntity, ActiveShieldComponent.class);
            if (activeShieldComponent.getShieldEntity() == shieldEntity) {
                shieldStatusEntity = currentShieldStatusEntity;
                oldShieldStatusEntities = EntityArrayUtil.remove(oldShieldStatusEntities, shieldStatusEntity, true);
                break;
            }
        }
        if (shieldStatusEntity == -1) {
            shieldStatusEntity = entityWorld.createEntity();
            entityWorld.setComponent(shieldStatusEntity, new ActiveShieldComponent(targetEntity, shieldEntity));
        }
        float fullShieldAmount = entityWorld.getComponent(shieldEntity, ShieldAmountComponent.class).getValue();
        entityWorld.setComponent(shieldStatusEntity, new ShieldAmountComponent(fullShieldAmount));
        if (duration != -1) {
            entityWorld.setComponent(shieldStatusEntity, new RemainingShieldDurationComponent(duration));
        } else {
            entityWorld.removeComponent(shieldStatusEntity, RemainingShieldDurationComponent.class);
        }
        int[] newShieldStatusEntities = EntityArrayUtil.add(oldShieldStatusEntities, shieldStatusEntity);
        entityWorld.setComponent(targetEntity, new ShieldsComponent(newShieldStatusEntities));
    }

    public static void removeShield(EntityWorld entityWorld, int targetEntity, int shieldEntity) {
        ShieldsComponent shieldsComponent = entityWorld.getComponent(targetEntity, ShieldsComponent.class);
        if (shieldsComponent != null) {
            int[] shieldStatusEntities = shieldsComponent.getShieldStatusEntities();
            for (int shieldStatusEntity : shieldStatusEntities) {
                ActiveShieldComponent activeShieldComponent = entityWorld.getComponent(shieldStatusEntity, ActiveShieldComponent.class);
                if (activeShieldComponent.getShieldEntity() == shieldEntity) {
                    int[] newShieldStatusEntities = EntityArrayUtil.remove(shieldStatusEntities, shieldStatusEntity, true);
                    entityWorld.removeEntity(shieldStatusEntity);
                    entityWorld.setComponent(targetEntity, new ShieldsComponent(newShieldStatusEntities));
                    break;
                }
            }
        }
    }
}
