package amara.applications.ingame.client.systems.visualisation;

import java.util.HashMap;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import amara.applications.ingame.client.gui.GUIUtil;
import amara.applications.ingame.entitysystem.components.units.GoldComponent;
import amara.libraries.entitysystem.*;

public class GoldChangeSystem extends EntityTextNotificationSystem {

    public GoldChangeSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager) {
        super(hudAttachmentsSystem, entityHeightMap, assetManager);
        hudOffset.set(0, 16, 0);
    }
    private HashMap<Integer, Float> cachedGold = new HashMap<>();
    private final ColorRGBA color = new ColorRGBA(1, 1, 0, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, GoldComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAll()) {
            cachedGold.put(entity, entityWorld.getComponent(entity, GoldComponent.class).getGold());
        }
        for (int entity : observer.getChanged().getEntitiesWithAll()) {
            onGoldChange(entity, entityWorld.getComponent(entity, GoldComponent.class).getGold());
        }
        for (int entity : observer.getRemoved().getEntitiesWithAll()) {
            cachedGold.put(entity, 0f);
        }
    }

    private void onGoldChange(int entity, float currentGold) {
        Float oldGold = cachedGold.get(entity);
        if (oldGold == null) {
            oldGold = 0f;
        }
        // Don't judge me...
        int change = Math.round(currentGold - oldGold);
        if (change != 0) {
            displayTextNotification(entity, GUIUtil.getValueText_Signed(change) + " g", color);
        }
        cachedGold.put(entity, currentGold);
    }
}
