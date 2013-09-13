/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import amara.engine.JMonkeyUtil;
import amara.engine.client.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.selection.IsSelectedComponent;

/**
 *
 * @author Carl
 */
public class SelectionMarkerSystem implements EntitySystem{
    
    public SelectionMarkerSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    public final static String NODE_NAME_SELECTION_MARKER = "selectionMarker";
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(IsSelectedComponent.class))
        {
            Node node = entitySceneMap.requestNode(entity);
            node.attachChild(createSelectionMarker("Textures/selection_markers/circle.png", 2, 2));
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(IsSelectedComponent.class))
        {
            Node node = entitySceneMap.requestNode(entity);
            node.detachChildNamed(NODE_NAME_SELECTION_MARKER);
        }
    }
    
    private Spatial createSelectionMarker(String textureFilePath, float width, float height){
        Spatial selectionMarker = new Geometry(NODE_NAME_SELECTION_MARKER, new Quad(width, height));
        selectionMarker.rotate(JMonkeyUtil.getQuaternion_X(-90));
        selectionMarker.setLocalTranslation((width / -2), 0, (height / 2));
        Material material = MaterialFactory.generateLightingMaterial(textureFilePath);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        material.getAdditionalRenderState().setDepthTest(false);
        selectionMarker.setMaterial(material);
        return selectionMarker;
    }
}
