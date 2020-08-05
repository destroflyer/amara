package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.costs.BuffStacksCostComponent;
import amara.applications.ingame.entitysystem.components.costs.GoldCostComponent;
import amara.applications.ingame.entitysystem.components.units.GoldComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntityWorld;

import java.util.List;

public class CostUtil {

    public static boolean isPayable(EntityWorld entityWorld, int entity, List<Integer> costEntities) {
        return costEntities.stream().allMatch(costEntity -> isPayable(entityWorld, entity, costEntity));
    }

    public static boolean isPayable(EntityWorld entityWorld, int entity, int costEntity) {
        // Gold
        GoldCostComponent goldCostComponent = entityWorld.getComponent(costEntity, GoldCostComponent.class);
        if ((goldCostComponent != null) && (goldCostComponent.getGold() > 0)) {
            GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
            if ((goldComponent == null) || (goldComponent.getGold() < goldCostComponent.getGold())) {
                return false;
            }
        }
        // BuffStacks
        BuffStacksCostComponent buffStacksCostComponent = entityWorld.getComponent(costEntity, BuffStacksCostComponent.class);
        if (buffStacksCostComponent != null) {
            int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, entity, buffStacksCostComponent.getBuffEntity());
            if (buffStatusEntity == -1) {
                return false;
            }
            StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
            if ((stacksComponent == null) || (stacksComponent.getStacks() < buffStacksCostComponent.getStacks())) {
                return false;
            }
        }
        return true;
    }

    public static void pay(EntityWorld entityWorld, int entity, List<Integer> costEntities) {
        for (int costEntity : costEntities) {
            pay(entityWorld, entity, costEntity);
        }
    }

    public static void pay(EntityWorld entityWorld, int entity, int costEntity) {
        // Gold
        GoldCostComponent goldCostComponent = entityWorld.getComponent(costEntity, GoldCostComponent.class);
        if (goldCostComponent != null) {
            GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
            entityWorld.setComponent(entity, new GoldComponent(goldComponent.getGold() - goldCostComponent.getGold()));
        }
        // BuffStacks
        BuffStacksCostComponent buffStacksCostComponent = entityWorld.getComponent(costEntity, BuffStacksCostComponent.class);
        if (buffStacksCostComponent != null) {
            int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, entity, buffStacksCostComponent.getBuffEntity());
            int oldStacks = entityWorld.getComponent(buffStatusEntity, StacksComponent.class).getStacks();
            int newStacks = oldStacks - buffStacksCostComponent.getStacks();
            entityWorld.setComponent(buffStatusEntity, new StacksComponent(newStacks));
        }
    }
}
