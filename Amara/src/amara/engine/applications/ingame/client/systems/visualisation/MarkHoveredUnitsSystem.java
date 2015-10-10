/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.IsHoveredComponent;

/**
 *
 * @author Carl
 */
public class MarkHoveredUnitsSystem implements EntitySystem{
    
    public MarkHoveredUnitsSystem(EntitySceneMap entitySceneMap, PlayerTeamSystem playerTeamSystem){
        this.entitySceneMap = entitySceneMap;
        this.playerTeamSystem = playerTeamSystem;
    }
    private final static String NODE_NAME_MARKER = "hoveredMarker";
    private EntitySceneMap entitySceneMap;
    private PlayerTeamSystem playerTeamSystem;
    private ColorRGBA colorAllies = new ColorRGBA(0.1f, 1, 0.1f, 1);
    private ColorRGBA colorEnemies = new ColorRGBA(1, 0.1f, 0.1f, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsHoveredComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(IsHoveredComponent.class)){
            Node node = entitySceneMap.requestNode(entity);
            Node attachmentNode = new Node();
            attachmentNode.setName(NODE_NAME_MARKER);
            ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
            if(modelObject != null){
                Spatial clonedModel = modelObject.getModelSpatial().deepClone();
                Material material = new Material(MaterialFactory.getAssetManager(), "Shaders/cartoonedge/matdefs/cartoonedge.j3md");
                material.setColor("EdgesColor", (playerTeamSystem.isAllied(entityWorld, entity)?colorAllies:colorEnemies));
                material.setFloat("EdgeSize", 0.1f / FastMath.pow(modelObject.getSkin().getModelScale(), 2.5f));
                for(Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel)){
                    geometry.setMaterial(material);
                }
                modelObject.registerModel(clonedModel);
                attachmentNode.attachChild(clonedModel);
                node.attachChild(attachmentNode);
            }
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsHoveredComponent.class)){
            Node node = entitySceneMap.requestNode(entity);
            Node attachmentNode = (Node) node.getChild(NODE_NAME_MARKER);
            if(attachmentNode != null){
                ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
                modelObject.unregisterModel(attachmentNode.getChild(0));
                node.detachChild(attachmentNode);
            }
        }
    }
}
