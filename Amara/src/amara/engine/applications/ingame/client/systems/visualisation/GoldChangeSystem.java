/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import com.jme3.math.ColorRGBA;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.GoldComponent;

/**
 *
 * @author Carl
 */
public class GoldChangeSystem extends EntityTextNotificationSystem{

    public GoldChangeSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        super(hudAttachmentsSystem, entityHeightMap);
        hudOffset.set(0, 16, 0);
    }
    private HashMap<Integer, Integer> cachedGold = new HashMap<Integer, Integer>();
    private final ColorRGBA color = new ColorRGBA(1, 1, 0, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, GoldComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll()){
            cachedGold.put(entity, entityWorld.getComponent(entity, GoldComponent.class).getGold());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll()){
            onGoldChange(entityWorld, entity, entityWorld.getComponent(entity, GoldComponent.class).getGold());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll()){
            cachedGold.put(entity, 0);
        }
        observer.reset();
    }
    
    private void onGoldChange(EntityWorld entityWorld, int entity, int currentGold){
        Integer oldGold = cachedGold.get(entity);
        if(oldGold == null){
            oldGold = 0;
        }
        int change = (currentGold - oldGold);
        if(change != 0){
            displayTextNotification(entityWorld, entity, ((change > 0)?"+":"") + change + " g", color);
        }
        cachedGold.put(entity, currentGold);
    }
}
