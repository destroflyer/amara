/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import java.util.HashMap;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class BuffVisualisationSystem implements EntitySystem{

    public BuffVisualisationSystem(EntitySceneMap entitySceneMap, String visualisationName){
        this.entitySceneMap = entitySceneMap;
        this.visualisationName = visualisationName;
    }
    protected final static int VISUALISATION_LAYER = 5;
    protected EntitySceneMap entitySceneMap;
    private String visualisationName;
    protected boolean scaleVisualisation = true;
    private HashMap<Integer, Integer> activeBuffCounts = new HashMap<Integer, Integer>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, ActiveBuffComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ActiveBuffComponent.class)){
            onBuffAdded(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ActiveBuffComponent.class)){
            onBuffAdded(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(ActiveBuffComponent.class)){
            ActiveBuffComponent activeBuffComponent = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class);
            if(shouldBeVisualized(entityWorld, activeBuffComponent)){
                int targetEntity = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class).getTargetEntity();
                if(decreaseBuffCount(targetEntity) == 0){
                    removeVisualAttachment(targetEntity);
                }
            }
        }
    }
    
    private void onBuffAdded(EntityWorld entityWorld, int buffStatusEntity){
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
        if(shouldBeVisualized(entityWorld, activeBuffComponent)){
            int targetEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getTargetEntity();
            if(increaseBuffCount(targetEntity) == 1){
                Spatial visualAttachment = createBuffVisualisation(entityWorld, targetEntity);
                if(visualAttachment != null){
                    prepareVisualAttachment(targetEntity, visualAttachment);
                    Node entityNode = entitySceneMap.requestNode(targetEntity);
                    entityNode.attachChild(visualAttachment);
                }
            }
        }
    }
    
    private boolean shouldBeVisualized(EntityWorld entityWorld, ActiveBuffComponent activeBuffComponent){
        BuffVisualisationComponent buffVisualisationComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), BuffVisualisationComponent.class);
        return ((buffVisualisationComponent != null) && (buffVisualisationComponent.getName().equals(visualisationName)));
    }
    
    private int increaseBuffCount(int entity){
        Integer buffsCount = activeBuffCounts.get(entity);
        if(buffsCount == null){
            buffsCount = 0;
        }
        buffsCount++;
        activeBuffCounts.put(entity, buffsCount);
        return buffsCount;
    }
    
    private int decreaseBuffCount(int entity){
        int buffsCount = (activeBuffCounts.get(entity) - 1);
        activeBuffCounts.put(entity, buffsCount);
        return buffsCount;
    }
    
    protected void prepareVisualAttachment(int targetEntity, Spatial visualAttachment){
        visualAttachment.setName(getVisualAttachmentID());
        if(scaleVisualisation){
            Node entityNode = entitySceneMap.requestNode(targetEntity);
            ModelObject modelObject = (ModelObject) entityNode.getChild(ModelSystem.NODE_NAME_MODEL);
            if(modelObject != null){
                visualAttachment.setLocalScale(modelObject.getSkin().getModelScale());
            }
        }
    }
    
    private void removeVisualAttachment(int targetEntity){
        Node entityNode = entitySceneMap.requestNode(targetEntity);
        Spatial visualAttachment;
        do{
            visualAttachment = entityNode.getChild(getVisualAttachmentID());
            if(visualAttachment != null){
                removeVisualAttachment(targetEntity, entityNode, visualAttachment);
            }
        }while(visualAttachment != null);
        
    }
    
    protected void removeVisualAttachment(int targetEntity, Node entityNode, Spatial visualAttachment){
        visualAttachment.getParent().detachChild(visualAttachment);
    }
    
    private String getVisualAttachmentID(){
        return ("buffVisualisation_" + visualisationName);
    }
    
    protected abstract Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity);
}