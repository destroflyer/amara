package amara.applications.ingame.entitysystem.systems.units.shields;

import amara.applications.ingame.entitysystem.components.units.ShieldsComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ShieldAmountComponent;
import amara.libraries.entitysystem.EntityWorld;

public class ShieldUtil {

    public static float getTotalShieldAmount(EntityWorld entityWorld, int entity) {
        float totalShieldAmount = 0;
        ShieldsComponent shieldsComponent = entityWorld.getComponent(entity, ShieldsComponent.class);
        if (shieldsComponent != null) {
            for (int shieldStatusEntity : shieldsComponent.getShieldStatusEntities()) {
                totalShieldAmount += entityWorld.getComponent(shieldStatusEntity, ShieldAmountComponent.class).getValue();
            }
        }
        return totalShieldAmount;
    }
}
