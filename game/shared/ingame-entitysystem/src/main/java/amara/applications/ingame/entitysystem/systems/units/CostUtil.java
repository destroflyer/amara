package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent;
import amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent;
import amara.applications.ingame.entitysystem.components.costs.BuffStacksCostComponent;
import amara.applications.ingame.entitysystem.components.costs.GoldCostComponent;
import amara.applications.ingame.entitysystem.components.units.GoldComponent;
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
            BuffStacksComponent buffStacksComponent = entityWorld.getComponent(buffStacksCostComponent.getBuffEntity(), BuffStacksComponent.class);
            if (buffStacksComponent == null) {
                return false;
            }
            StacksComponent stacksComponent = entityWorld.getComponent(buffStacksComponent.getStacksEntity(), StacksComponent.class);
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
            BuffStacksComponent buffStacksComponent = entityWorld.getComponent(buffStacksCostComponent.getBuffEntity(), BuffStacksComponent.class);
            int oldStacks = entityWorld.getComponent(buffStacksComponent.getStacksEntity(), StacksComponent.class).getStacks();
            int newStacks = oldStacks - buffStacksCostComponent.getStacks();
            entityWorld.setComponent(buffStacksComponent.getStacksEntity(), new StacksComponent(newStacks));
        }
    }
}
