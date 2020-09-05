/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DirectionSystem implements EntitySystem{
    
    public DirectionSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, DirectionComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(DirectionComponent.class)){
            updateDirection(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(DirectionComponent.class)){
            updateDirection(entityWorld, entity);
        }
    }
    
    private void updateDirection(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        DirectionComponent directionComponent = entityWorld.getComponent(entity, DirectionComponent.class);
        JMonkeyUtil.setLocalRotation(node, new Vector3f(directionComponent.getVector().getX(), 0, directionComponent.getVector().getY()));
    }
}
