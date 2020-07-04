package amara.applications.ingame.client.gui;

import amara.applications.ingame.entitysystem.components.items.ItemVisualisationComponent;
import amara.libraries.entitysystem.EntityWorld;

public class GUIItems {

    public static final int DESCRIPTION_LINE_LENGTH = 40;

    public static String getImageFilePath(EntityWorld entityWorld, int[] itemEntities, int itemIndex) {
        int itemEntity = getItemEntity(itemEntities, itemIndex);
        return getImageFilePath(entityWorld, itemEntity);
    }

    public static int getItemEntity(int[] itemEntities, int itemIndex) {
        if (itemEntities != null) {
            if (itemIndex < itemEntities.length) {
                return itemEntities[itemIndex];
            }
        }
        return -1;
    }

    public static String getImageFilePath(EntityWorld entityWorld, int itemEntity) {
        String fileName = "none";
        if (itemEntity != -1) {
            ItemVisualisationComponent itemVisualisationComponent = entityWorld.getComponent(itemEntity, ItemVisualisationComponent.class);
            if (itemVisualisationComponent != null) {
                fileName = itemVisualisationComponent.getName();
            } else {
                fileName = "unknown";
            }
        }
        return "Interface/hud/items/" + fileName + ".png";
    }
}
