package amara.applications.ingame.entitysystem.systems.effects.damage;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.IsVulnerableComponent;
import amara.applications.ingame.entitysystem.components.units.ShieldsComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ActiveShieldComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ShieldAmountComponent;
import amara.applications.ingame.entitysystem.systems.effects.units.ApplyAddShieldSystem;
import amara.libraries.entitysystem.EntityWorld;

public class DamageUtil {

    public static boolean dealDamage(EntityWorld entityWorld, int entity, float damage) {
        boolean wasDamaged = false;
        if (entityWorld.hasComponent(entity, IsVulnerableComponent.class)) {
            float remainingDamage = damage;
            ShieldsComponent shieldsComponent = entityWorld.getComponent(entity, ShieldsComponent.class);
            if (shieldsComponent != null) {
                int[] shieldStatusEntities = shieldsComponent.getShieldStatusEntities();
                for (int i = shieldStatusEntities.length - 1; i >= 0; i--) {
                    float shieldAmount = entityWorld.getComponent(shieldStatusEntities[i], ShieldAmountComponent.class).getValue();
                    if (remainingDamage < shieldAmount) {
                        entityWorld.setComponent(shieldStatusEntities[i], new ShieldAmountComponent(shieldAmount - remainingDamage));
                        remainingDamage = 0;
                        break;
                    } else {
                        int shieldEntity = entityWorld.getComponent(shieldStatusEntities[i], ActiveShieldComponent.class).getShieldEntity();
                        ApplyAddShieldSystem.removeShield(entityWorld, entity, shieldEntity);
                        remainingDamage -= shieldAmount;
                    }
                    wasDamaged = true;
                }
            }
            if (remainingDamage > 0) {
                HealthComponent healthComponent = entityWorld.getComponent(entity, HealthComponent.class);
                if (healthComponent != null) {
                    float health = (healthComponent.getValue() - remainingDamage);
                    entityWorld.setComponent(entity, new HealthComponent(health));
                    wasDamaged = true;
                }
            }
        }
        return wasDamaged;
    }
}
