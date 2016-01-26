/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.math.ColorRGBA;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class LevelUpNotificationSystem extends EntityTextNotificationSystem{

    public LevelUpNotificationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        super(hudAttachmentsSystem, entityHeightMap);
        hudOffset.set(0, 50, 0);
    }
    private final ColorRGBA color = new ColorRGBA(1, 1, 1, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, LevelComponent.class);
        for(int entity : observer.getChanged().getEntitiesWithAll()){
            displayTextNotification(entityWorld, entity, "LEVEL UP", color);
        }
    }
}