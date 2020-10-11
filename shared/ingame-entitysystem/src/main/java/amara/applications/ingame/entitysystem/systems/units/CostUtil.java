package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.costs.BuffStacksCostComponent;
import amara.applications.ingame.entitysystem.components.costs.GoldCostComponent;
import amara.applications.ingame.entitysystem.components.costs.ManaCostComponent;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.applications.ingame.entitysystem.components.units.GoldComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntityWorld;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CostUtil {

    public static boolean isPayable(EntityWorld entityWorld, int entity, int costEntity) {
        LinkedList<Integer> costEntities = new LinkedList<>();
        costEntities.add(costEntity);
        return isPayable(entityWorld, entity, costEntities);
    }

    public static boolean isPayable(EntityWorld entityWorld, int entity, List<Integer> costEntities) {
        ManaComponent manaComponent = entityWorld.getComponent(entity, ManaComponent.class);
        float remainingMana = ((manaComponent != null) ? manaComponent.getValue() : 0);
        GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
        float remainingGold = ((goldComponent != null) ? goldComponent.getGold() : 0);
        HashMap<Integer, Integer> remainingBuffStacks = new HashMap<>();
        BuffsComponent buffsComponent = entityWorld.getComponent(entity, BuffsComponent.class);
        if (buffsComponent != null) {
            for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
                if (stacksComponent != null) {
                    int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                    remainingBuffStacks.put(buffEntity, stacksComponent.getStacks());
                }
            }
        }
        for (int costEntity : costEntities) {
            // Mana
            ManaCostComponent manaCostComponent = entityWorld.getComponent(costEntity, ManaCostComponent.class);
            if (manaCostComponent != null) {
                if (manaCostComponent.getMana() > remainingMana) {
                    return false;
                }
                remainingMana -= manaCostComponent.getMana();
            }
            // Gold
            GoldCostComponent goldCostComponent = entityWorld.getComponent(costEntity, GoldCostComponent.class);
            if (goldCostComponent != null) {
                if (goldCostComponent.getGold() > remainingGold) {
                    return false;
                }
                remainingGold -= goldCostComponent.getGold();
            }
            // BuffStacks
            BuffStacksCostComponent buffStacksCostComponent = entityWorld.getComponent(costEntity, BuffStacksCostComponent.class);
            if (buffStacksCostComponent != null) {
                int remainingStacks = remainingBuffStacks.get(buffStacksCostComponent.getBuffEntity());
                if (buffStacksCostComponent.getStacks() > remainingStacks) {
                    return false;
                }
                remainingStacks -= buffStacksCostComponent.getStacks();
                remainingBuffStacks.put(buffStacksCostComponent.getBuffEntity(), remainingStacks);
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
        // Mana
        ManaCostComponent manaCostComponent = entityWorld.getComponent(costEntity, ManaCostComponent.class);
        if (manaCostComponent != null) {
            ManaComponent manaComponent = entityWorld.getComponent(entity, ManaComponent.class);
            entityWorld.setComponent(entity, new ManaComponent(manaComponent.getValue() - manaCostComponent.getMana()));
        }
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
