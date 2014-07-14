/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import amara.engine.JMonkeyUtil;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.SelectedUnitComponent;

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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, SelectedUnitComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(SelectedUnitComponent.class)){
            int selectedUnit = observer.getNew().getComponent(entity, SelectedUnitComponent.class).getEntity();
            Node node = entitySceneMap.requestNode(selectedUnit);
            node.attachChild(createGroundTexture("Textures/selection_markers/circle.png", 2, 2));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(SelectedUnitComponent.class)){
            int selectedUnit = observer.getRemoved().getComponent(entity, SelectedUnitComponent.class).getEntity();
            Node node = entitySceneMap.requestNode(selectedUnit);
            node.detachChildNamed(NODE_NAME_SELECTION_MARKER);
        }
        observer.reset();
    }
    
    public static Spatial createGroundTexture(String textureFilePath, float width, float height){
        Spatial selectionMarker = new Geometry(NODE_NAME_SELECTION_MARKER, new Quad(width, height));
        selectionMarker.rotate(JMonkeyUtil.getQuaternion_X(-90));
        selectionMarker.setLocalTranslation((width / -2), 0, (height / 2));
        Material material = MaterialFactory.generateLightingMaterial(textureFilePath);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        material.getAdditionalRenderState().setDepthTest(false);
        selectionMarker.setMaterial(material);
        selectionMarker.setUserData("layer", 1);
        return selectionMarker;
    }
}
