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
    private EntitySceneMap entitySceneMap;
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
            int targetEntityID = observer.getRemoved().getComponent(entity, ActiveBuffComponent.class).getTargetEntityID();
            removeVisualAttachment(targetEntityID);
        }
        observer.reset();
    }
    
    private void updateVisualAttachment(EntityWorld entityWorld, int buffStatusEntityID){
        if(shouldBeVisualized(entityWorld, buffStatusEntityID)){
            int targetEntityID = entityWorld.getComponent(buffStatusEntityID, ActiveBuffComponent.class).getTargetEntityID();
            removeVisualAttachment(targetEntityID);
            Node node = entitySceneMap.requestNode(targetEntityID);
            Spatial visualAttachment = createBuffVisualisation(entityWorld, targetEntityID);
            if(visualAttachment != null){
                visualAttachment.setName(getVisualAttachmentID());
                node.attachChild(visualAttachment);
            }
        }
    }
    
    private boolean shouldBeVisualized(EntityWorld entityWorld, int buffStatusEntityID){
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntityID, ActiveBuffComponent.class);
        BuffVisualisationComponent buffVisualisationComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntityID(), BuffVisualisationComponent.class);
        return ((buffVisualisationComponent != null) && (buffVisualisationComponent.getName().equals(visualisationName)));
    }
    
    private void removeVisualAttachment(int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(getVisualAttachmentID());
    }
    
    private String getVisualAttachmentID(){
        return ("buffVisualisation_" + visualisationName);
    }
    
    protected abstract Spatial createBuffVisualisation(EntityWorld entityWorld, int entity);
}
