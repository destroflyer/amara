/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Node;
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
            Node node = entitySceneMap.requestNode(targetEntityID);
            Spatial visualAttachment = createBuffVisualisation(entityWorld, targetEntityID);
            if(visualAttachment != null){
                visualAttachment.setName(getVisualAttachmentID(buffStatusEntity));
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
        node.detachChildNamed(getVisualAttachmentID(buffStatusEntity));
    }
    
    private String getVisualAttachmentID(int buffStatusEntity){
        return ("buffVisualisation_" + visualisationName + "_" + buffStatusEntity);
    }
    
    protected abstract Spatial createBuffVisualisation(EntityWorld entityWorld, int entity);
}
