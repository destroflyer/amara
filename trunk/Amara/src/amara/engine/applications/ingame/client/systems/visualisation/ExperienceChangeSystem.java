/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import com.jme3.math.ColorRGBA;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ExperienceChangeSystem extends EntityTextNotificationSystem{

    public ExperienceChangeSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        super(hudAttachmentsSystem, entityHeightMap);
        hudOffset.set(0, 29, 0);
    }
    private HashMap<Integer, Integer> cachedExperience = new HashMap<Integer, Integer>();
    private final ColorRGBA color = new ColorRGBA(1, 1, 1, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ExperienceComponent.class, LevelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll()){
            cachedExperience.put(entity, entityWorld.getComponent(entity, ExperienceComponent.class).getExperience());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll()){
            int experience = entityWorld.getComponent(entity, ExperienceComponent.class).getExperience();
            if(!observer.getChanged().hasComponent(entity, LevelComponent.class)){
                onExperienceChange(entityWorld, entity, experience);
            }
            cachedExperience.put(entity, experience);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll()){
            cachedExperience.put(entity, 0);
        }
        observer.reset();
    }
    
    private void onExperienceChange(EntityWorld entityWorld, int entity, int currentExperience){
        Integer oldExperience = cachedExperience.get(entity);
        if(oldExperience == null){
            oldExperience = 0;
        }
        int change = (currentExperience - oldExperience);
        if(change != 0){
            displayTextNotification(entityWorld, entity, ((change > 0)?"+":"") + change + " xp", color);
        }
    }
}
