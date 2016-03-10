/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.units.IsHoveredComponent;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.ingame.appstates.IngameMouseCursorAppState;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class MarkHoveredUnitsSystem implements EntitySystem{
    
    public MarkHoveredUnitsSystem(EntitySceneMap entitySceneMap, PlayerTeamSystem playerTeamSystem, IngameMouseCursorAppState ingameMouseCursorAppState){
        this.entitySceneMap = entitySceneMap;
        this.playerTeamSystem = playerTeamSystem;
        this.ingameMouseCursorAppState = ingameMouseCursorAppState;
    }
    private final static String NODE_NAME_MARKER = "hoveredMarker";
    private EntitySceneMap entitySceneMap;
    private PlayerTeamSystem playerTeamSystem;
    private IngameMouseCursorAppState ingameMouseCursorAppState;
    private ColorRGBA colorAllies = new ColorRGBA(0.1f, 1, 0.1f, 1);
    private ColorRGBA colorEnemies = new ColorRGBA(1, 0.1f, 0.1f, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsHoveredComponent.class);
        //Check removed first to keep a potential hover cursor at the end
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsHoveredComponent.class)){
            ingameMouseCursorAppState.setCursor_Default();
            Node node = entitySceneMap.requestNode(entity);
            Node attachmentNode = (Node) node.getChild(NODE_NAME_MARKER);
            if(attachmentNode != null){
                ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
                modelObject.unregisterModel(attachmentNode.getChild(0));
                node.detachChild(attachmentNode);
            }
        }
        for(int entity : observer.getNew().getEntitiesWithAll(IsHoveredComponent.class)){
            boolean isAllied = playerTeamSystem.isAllied(entityWorld, entity);
            if(isAllied){
                ingameMouseCursorAppState.setCursor_Default();
            }
            else{
                ingameMouseCursorAppState.setCursor_Enemy();
            }
            Node node = entitySceneMap.requestNode(entity);
            Node attachmentNode = new Node();
            attachmentNode.setName(NODE_NAME_MARKER);
            ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
            if(modelObject != null){
                Spatial clonedModel = modelObject.getModelSpatial().deepClone();
                Material material = new Material(MaterialFactory.getAssetManager(), "Shaders/cartoonedge/matdefs/cartoonedge.j3md");
                material.setColor("EdgesColor", (isAllied?colorAllies:colorEnemies));
                material.setFloat("EdgeSize", (0.1f / FastMath.pow(modelObject.getSkin().getModelScale().getY(), 2.5f)));
                for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel)){
                    geometry.setMaterial(material);
                }
                modelObject.registerModel(clonedModel);
                JMonkeyUtil.setHardwareSkinningPreferred(clonedModel, false);
                attachmentNode.attachChild(clonedModel);
                node.attachChild(attachmentNode);
            }
        }
    }
}
