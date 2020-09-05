package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.network.serializing.Serializable;

@Serializable
public class ItemIndex {

    public ItemIndex() {

    }

    public ItemIndex(ItemSet itemSet, int index) {
        this.itemSet = itemSet;
        this.index = index;
    }
    public enum ItemSet {
        INVENTORY,
        BAG
    }
    private ItemSet itemSet;
    private int index;

    public ItemSet getItemSet() {
        return itemSet;
    }

    public int getIndex() {
        return index;
    }
}
