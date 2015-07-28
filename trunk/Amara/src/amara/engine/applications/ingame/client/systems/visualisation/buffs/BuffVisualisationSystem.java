/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.status.*;

/**
 *
 * @author Carl
 */
public abstract class BuffVisualisationSystem implements EntitySystem{

    public BuffVisualisationSystem(EntitySceneMap entitySceneMap, String visualisationName){
        this.entitySceneMap = entitySceneMap;
        this.visualisationName = visualisationName;
    }
    protected EntitySceneMap entitySceneMap;
    private String visualisationName;
    protected boolean scaleVisualisation = true;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ActiveBuffComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ActiveBuffComponent.class)){
            updateVisualAttachment(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ActiveBuffComponent.class)){
            updateVisualAttachment(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(ActiveBuffComponent.class)){
            ActiveBuffComponent activeBuffComponent = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class);
            if(shouldBeVisualized(entityWorld, activeBuffComponent)){
                int targetEntity = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class).getTargetEntity();
                removeVisualAttachment(entity, targetEntity);
            }
        }
        observer.reset();
    }
    
    private void updateVisualAttachment(EntityWorld entityWorld, int buffStatusEntity){
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
        if(shouldBeVisualized(entityWorld, activeBuffComponent)){
            int targetEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getTargetEntity();
            removeVisualAttachment(buffStatusEntity, targetEntity);
            Spatial visualAttachment = createBuffVisualisation(entityWorld, buffStatusEntity, targetEntity);
            if(visualAttachment != null){
                prepareVisualAttachment(buffStatusEntity, targetEntity, visualAttachment);
                Node entityNode = entitySceneMap.requestNode(targetEntity);
                entityNode.attachChild(visualAttachment);
            }
        }
    }
    
    private boolean shouldBeVisualized(EntityWorld entityWorld, ActiveBuffComponent activeBuffComponent){
        BuffVisualisationComponent buffVisualisationComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), BuffVisualisationComponent.class);
        return ((buffVisualisationComponent != null) && (buffVisualisationComponent.getName().equals(visualisationName)));
    }
    
    protected void prepareVisualAttachment(int buffStatusEntity, int targetEntity, Spatial visualAttachment){
        visualAttachment.setName(getVisualAttachmentID(buffStatusEntity));
        if(scaleVisualisation){
            Node entityNode = entitySceneMap.requestNode(targetEntity);
            ModelObject modelObject = (ModelObject) entityNode.getChild(ModelSystem.NODE_NAME_MODEL);
            if(modelObject != null){
                visualAttachment.setLocalScale(modelObject.getSkin().getModelScale());
            }
        }
    }
    
    private void removeVisualAttachment(int buffStatusEntity, int targetEntity){
        Node entityNode = entitySceneMap.requestNode(targetEntity);
        Spatial visualAttachment;
        do{
            visualAttachment = entityNode.getChild(getVisualAttachmentID(buffStatusEntity));
            if(visualAttachment != null){
                removeVisualAttachment(targetEntity, entityNode, visualAttachment);
            }
        }while(visualAttachment != null);
        
    }
    
    protected void removeVisualAttachment(int targetEntity, Node entityNode, Spatial visualAttachment){
        visualAttachment.getParent().detachChild(visualAttachment);
    }
    
    private String getVisualAttachmentID(int buffStatusEntity){
        return ("buffVisualisation_" + visualisationName + "_" + buffStatusEntity);
    }
    
    protected abstract Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity);
}
