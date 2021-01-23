package amara.applications.ingame.client.systems.visualisation;

import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;

public class LevelUpNotificationSystem extends EntityTextNotificationSystem {

    public LevelUpNotificationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager) {
        super(hudAttachmentsSystem, entityHeightMap, assetManager);
        hudOffset.set(0, 50, 0);
    }
    private final ColorRGBA color = new ColorRGBA(1, 1, 1, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, LevelComponent.class);
        for (int entity : observer.getChanged().getEntitiesWithAll()) {
            displayTextNotification(entity, "LEVEL UP", color);
        }
    }
}
