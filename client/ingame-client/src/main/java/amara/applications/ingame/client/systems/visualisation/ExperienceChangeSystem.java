package amara.applications.ingame.client.systems.visualisation;

import java.util.HashMap;

import amara.applications.ingame.entitysystem.components.units.ExperienceComponent;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;

public class ExperienceChangeSystem extends EntityTextNotificationSystem {

    public ExperienceChangeSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager) {
        super(hudAttachmentsSystem, entityHeightMap, assetManager);
        hudOffset.set(0, 29, 0);
    }
    private HashMap<Integer, Integer> cachedExperience = new HashMap<>();
    private final ColorRGBA color = new ColorRGBA(1, 1, 1, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, ExperienceComponent.class, LevelComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAll()) {
            cachedExperience.put(entity, entityWorld.getComponent(entity, ExperienceComponent.class).getExperience());
        }
        for (int entity : observer.getChanged().getEntitiesWithAll()) {
            int experience = entityWorld.getComponent(entity, ExperienceComponent.class).getExperience();
            if (!observer.getChanged().hasComponent(entity, LevelComponent.class)) {
                onExperienceChange(entity, experience);
            }
            cachedExperience.put(entity, experience);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAll()) {
            cachedExperience.put(entity, 0);
        }
    }

    private void onExperienceChange(int entity, int currentExperience) {
        Integer oldExperience = cachedExperience.get(entity);
        if (oldExperience == null) {
            oldExperience = 0;
        }
        int change = (currentExperience - oldExperience);
        if (change != 0) {
            displayTextNotification(entity, ((change > 0) ? "+" : "") + change + " xp", color);
        }
    }
}
