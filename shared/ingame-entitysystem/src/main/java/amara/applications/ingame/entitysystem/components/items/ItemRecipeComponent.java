package amara.applications.ingame.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;

@Serializable
public class ItemRecipeComponent {

    public ItemRecipeComponent() {

    }

    public ItemRecipeComponent(int combineCostEntity, String... itemIDs) {
        this.combineCostEntity = combineCostEntity;
        this.itemIDs = itemIDs;
    }
    private int combineCostEntity;
    private String[] itemIDs;

    public int getCombineCostEntity() {
        return combineCostEntity;
    }

    public String[] getItemIDs() {
        return itemIDs;
    }
}
