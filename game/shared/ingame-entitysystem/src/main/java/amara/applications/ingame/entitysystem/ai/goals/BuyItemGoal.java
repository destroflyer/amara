package amara.applications.ingame.entitysystem.ai.goals;

import amara.applications.ingame.entitysystem.ai.actions.BuyItemAction;
import amara.applications.ingame.entitysystem.components.costs.GoldCostComponent;
import amara.applications.ingame.entitysystem.components.items.ItemIDComponent;
import amara.applications.ingame.entitysystem.components.items.ItemRecipeComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent;
import amara.applications.ingame.entitysystem.components.units.GoldComponent;
import amara.applications.ingame.entitysystem.systems.shop.ShopUtil;
import amara.ingame.ai.Action;
import amara.ingame.ai.Goal;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.StaticEntityWorld;

import java.util.LinkedList;

public class BuyItemGoal extends Goal {

    private int[] itemEntities;
    private String itemIdToBuy;

    @Override
    public boolean isEnabled(EntityWorld entityWorld, int entity) {
        if (itemEntities == null) {
            for (int shopEntity : entityWorld.getEntitiesWithAny(ShopItemsComponent.class)) {
                if (ShopUtil.canUseShop(entityWorld, entity, shopEntity)) {
                    String[] itemTemplateNames = entityWorld.getComponent(shopEntity, ShopItemsComponent.class).getItemTemplateNames();
                    itemEntities = new int[itemTemplateNames.length];
                    for (int i = 0; i < itemEntities.length; i++) {
                        itemEntities[i] = StaticEntityWorld.loadTemplate(itemTemplateNames[i]);
                    }
                    break;
                }
            }
        }
        float gold = entityWorld.getComponent(entity, GoldComponent.class).getGold();
        LinkedList<String> validItemIdsToBuy = new LinkedList<>();
        for (int itemEntity : itemEntities) {
            int combineCostEntity = StaticEntityWorld.getEntityWorld().getComponent(itemEntity, ItemRecipeComponent.class).getCombineCostEntity();
            float goldCost = StaticEntityWorld.getEntityWorld().getComponent(combineCostEntity, GoldCostComponent.class).getGold();
            if (gold >= goldCost) {
                String itemId = StaticEntityWorld.getEntityWorld().getComponent(itemEntity, ItemIDComponent.class).getID();
                validItemIdsToBuy.add(itemId);
            }
        }
        if (validItemIdsToBuy.size() > 0) {
            itemIdToBuy = validItemIdsToBuy.get((int) (Math.random() * validItemIdsToBuy.size()));
            return true;
        }
        return false;
    }

    @Override
    public void initialize(EntityWorld entityWorld, int entity) {

    }

    @Override
    public double getValue(EntityWorld entityWorld, int entity) {
        return 0;
    }

    @Override
    public void addBestActions(EntityWorld entityWorld, int entity, LinkedList<Action> actions) {
        actions.add(new BuyItemAction(itemIdToBuy));
    }
}
