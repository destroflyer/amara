package amara.applications.ingame.entitysystem.ai.actions;

import amara.ingame.ai.Action;

public class BuyItemAction extends Action {

    public BuyItemAction(String itemId) {
        this.itemId = itemId;
    }
    private String itemId;

    public String getItemId() {
        return itemId;
    }
}
