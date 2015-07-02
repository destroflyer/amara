/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.buffs.BuffVisualisationSystem_SonicWaveMark;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.IsHoveredComponent;

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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, IsHoveredComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(IsHoveredComponent.class)){
            Node node = entitySceneMap.requestNode(entity);
            Node attachmentNode = new Node();
            attachmentNode.setName(NODE_NAME_SELECTION_MARKER);
            Spatial attachment = BuffVisualisationSystem_SonicWaveMark.createGroundTexture("Textures/selection_markers/circle.png", 2, 2);
            ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
            attachmentNode.attachChild(attachment);
            attachmentNode.setLocalScale(modelObject.getSkin().getModelScale());
            node.attachChild(attachmentNode);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsHoveredComponent.class)){
            Node node = entitySceneMap.requestNode(entity);
            node.detachChildNamed(NODE_NAME_SELECTION_MARKER);
        }
        observer.reset();
    }
}
