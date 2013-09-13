/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class PositionSystem implements EntitySystem{
    
    public PositionSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(PositionComponent.class))
        {
            updatePosition(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(PositionComponent.class))
        {
            updatePosition(entityWorld, entity);
        }
    }
    
    private void updatePosition(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        PositionComponent positionComponent = entityWorld.getCurrent().getComponent(entity, PositionComponent.class);
        node.setLocalTranslation(positionComponent.getPosition().getX(), 0, positionComponent.getPosition().getY());
    }
}
