/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.EntitySceneMap;
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
    private RemoveVisualAttachmentVisitor removeVisitor = new RemoveVisualAttachmentVisitor();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ActiveBuffComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ActiveBuffComponent.class))
        {
            updateVisualAttachment(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ActiveBuffComponent.class))
        {
            updateVisualAttachment(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(ActiveBuffComponent.class))
        {
            ActiveBuffComponent activeBuffComponent = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class);
            if(shouldBeVisualized(entityWorld, activeBuffComponent)){
                int targetEntityID = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class).getTargetEntityID();
                removeVisualAttachment(targetEntityID, entity);
            }
        }
        observer.reset();
    }
    
    private void updateVisualAttachment(EntityWorld entityWorld, int buffStatusEntity){
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
        if(shouldBeVisualized(entityWorld, activeBuffComponent)){
            int targetEntityID = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getTargetEntityID();
            removeVisualAttachment(targetEntityID, buffStatusEntity);
            Spatial visualAttachment = createBuffVisualisation(entityWorld, targetEntityID, buffStatusEntity);
            if(visualAttachment != null){
                prepareVisualAttachment(visualAttachment, buffStatusEntity);
                Node node = entitySceneMap.requestNode(targetEntityID);
                node.attachChild(visualAttachment);
            }
        }
    }
    
    private boolean shouldBeVisualized(EntityWorld entityWorld, ActiveBuffComponent activeBuffComponent){
        BuffVisualisationComponent buffVisualisationComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntityID(), BuffVisualisationComponent.class);
        return ((buffVisualisationComponent != null) && (buffVisualisationComponent.getName().equals(visualisationName)));
    }
    
    private void removeVisualAttachment(int entity, int buffStatusEntity){
        Node node = entitySceneMap.requestNode(entity);
        removeVisitor.prepare(entity, buffStatusEntity, node);
        node.depthFirstTraversal(removeVisitor);
    }
    
    protected void removeVisualAttachment(int entity, Node node, Spatial visualAttachment){
        visualAttachment.getParent().detachChild(visualAttachment);
    }
    
    protected void prepareVisualAttachment(Spatial visualAttachment, int buffStatusEntity){
        visualAttachment.setName(getVisualAttachmentID(buffStatusEntity));
    }
    
    private String getVisualAttachmentID(int buffStatusEntity){
        return ("buffVisualisation_" + visualisationName + "_" + buffStatusEntity);
    }
    
    protected abstract Spatial createBuffVisualisation(EntityWorld entityWorld, int entity, int buffStatusEntity);
    
    private class RemoveVisualAttachmentVisitor implements SceneGraphVisitor{
        
        private int entity;
        private int buffStatusEntity;
        private Node entityNode;

        @Override
        public void visit(Spatial spatial){
            if(getVisualAttachmentID(buffStatusEntity).equals(spatial.getName())){
                removeVisualAttachment(entity, entityNode, spatial);
            }
        }
        
        public void prepare(int entity, int buffStatusEntity, Node entityNode){
            this.entity = entity;
            this.buffStatusEntity = buffStatusEntity;
            this.entityNode = entityNode;
        }
    }
}
