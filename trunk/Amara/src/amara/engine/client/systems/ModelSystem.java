/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import amara.engine.client.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class ModelSystem implements EntitySystem{
    
    public ModelSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(PositionComponent.class))
        {
            Node node = entitySceneMap.requestNode(entity);
            Geometry box = new Geometry("", new Box(1, 1, 1));
            box.setMaterial(MaterialFactory.generateLightingMaterial(ColorRGBA.Red));
            box.getMaterial().getAdditionalRenderState().setWireframe(true);
            node.attachChild(box);
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(PositionComponent.class))
        {
            entitySceneMap.removeNode(entity);
        }
    }
}
