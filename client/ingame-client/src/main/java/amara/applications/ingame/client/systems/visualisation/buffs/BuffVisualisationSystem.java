package amara.applications.ingame.client.systems.visualisation.buffs;

import java.util.HashMap;

import amara.applications.ingame.entitysystem.components.buffs.BuffVisualisationComponent;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.*;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class BuffVisualisationSystem implements EntitySystem {

    public BuffVisualisationSystem(EntitySceneMap entitySceneMap, AssetManager assetManager, String visualisationName) {
        this.entitySceneMap = entitySceneMap;
        this.assetManager = assetManager;
        this.visualisationName = visualisationName;
    }
    protected final static int VISUALISATION_LAYER = 5;
    protected EntitySceneMap entitySceneMap;
    protected AssetManager assetManager;
    private String visualisationName;
    protected boolean scaleVisualisation = true;
    private HashMap<Integer, Integer> activeBuffCounts = new HashMap<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, ActiveBuffComponent.class, StacksComponent.class);
        // Add visualisations
        for (int buffStatusEntity : observer.getNew().getEntitiesWithAny(ActiveBuffComponent.class)) {
            onBuffAdded(entityWorld, buffStatusEntity);
        }
        // Update visualisations
        for (int buffStatusEntity : observer.getNew().getEntitiesWithAny(StacksComponent.class)) {
            onStacksChanged(entityWorld, buffStatusEntity);
        }
        for (int buffStatusEntity : observer.getChanged().getEntitiesWithAny(StacksComponent.class)) {
            onStacksChanged(entityWorld, buffStatusEntity);
        }
        // Remove visualisations
        for (int buffStatusEntity : observer.getRemoved().getEntitiesWithAny(ActiveBuffComponent.class)) {
            onBuffRemoved(entityWorld, observer.getRemoved().getComponent(buffStatusEntity, ActiveBuffComponent.class));
        }
    }

    private void onBuffAdded(EntityWorld entityWorld, int buffStatusEntity) {
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
        if (shouldBeVisualized(entityWorld, activeBuffComponent)){
            int targetEntity = activeBuffComponent.getTargetEntity();
            if (increaseBuffCount(targetEntity) == 1) {
                Spatial visualAttachment = createBuffVisualisation(entityWorld, targetEntity);
                if (visualAttachment != null) {
                    prepareVisualAttachment(targetEntity, visualAttachment);
                    Node entityNode = entitySceneMap.requestNode(targetEntity);
                    entityNode.attachChild(visualAttachment);
                }
            }
        }
    }

    protected abstract Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity);

    private void onBuffRemoved(EntityWorld entityWorld, ActiveBuffComponent activeBuffComponent) {
        if (shouldBeVisualized(entityWorld, activeBuffComponent)) {
            int targetEntity = activeBuffComponent.getTargetEntity();
            if (decreaseBuffCount(targetEntity) == 0) {
                removeVisualAttachment(targetEntity);
            }
        }
    }

    private boolean shouldBeVisualized(EntityWorld entityWorld, ActiveBuffComponent activeBuffComponent) {
        BuffVisualisationComponent buffVisualisationComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), BuffVisualisationComponent.class);
        return ((buffVisualisationComponent != null) && (buffVisualisationComponent.getName().equals(visualisationName)));
    }

    private int increaseBuffCount(int entity) {
        Integer buffsCount = activeBuffCounts.get(entity);
        if (buffsCount == null) {
            buffsCount = 0;
        }
        buffsCount++;
        activeBuffCounts.put(entity, buffsCount);
        return buffsCount;
    }

    private int decreaseBuffCount(int entity) {
        int buffsCount = (activeBuffCounts.get(entity) - 1);
        activeBuffCounts.put(entity, buffsCount);
        return buffsCount;
    }

    protected void prepareVisualAttachment(int targetEntity, Spatial visualAttachment) {
        visualAttachment.setName(getVisualAttachmentID());
        if (scaleVisualisation) {
            Node entityNode = entitySceneMap.requestNode(targetEntity);
            ModelObject modelObject = (ModelObject) entityNode.getChild(ModelSystem.NODE_NAME_MODEL);
            if (modelObject != null) {
                visualAttachment.setLocalScale(modelObject.getSkin().getModelScale());
            }
        }
    }

    protected void removeVisualAttachment(int targetEntity) {
        Node entityNode = entitySceneMap.requestNode(targetEntity);
        Spatial visualAttachment;
        do {
            visualAttachment = entityNode.getChild(getVisualAttachmentID());
            if (visualAttachment != null) {
                removeVisualAttachment(targetEntity, entityNode, visualAttachment);
            }
        } while (visualAttachment != null);
    }

    protected void removeVisualAttachment(int targetEntity, Node entityNode, Spatial visualAttachment) {
        visualAttachment.getParent().detachChild(visualAttachment);
    }

    private void onStacksChanged(EntityWorld entityWorld, int buffStatusEntity) {
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
        if (shouldBeVisualized(entityWorld, activeBuffComponent)) {
            int stacks = entityWorld.getComponent(buffStatusEntity, StacksComponent.class).getStacks();
            Node entityNode = entitySceneMap.requestNode(activeBuffComponent.getTargetEntity());
            Spatial visualAttachment = entityNode.getChild(getVisualAttachmentID());
            updateBuffVisualisationStacks(visualAttachment, stacks);
        }
    }

    protected void updateBuffVisualisationStacks(Spatial visualAttachment, int stacks) {

    }

    private String getVisualAttachmentID() {
        return ("buffVisualisation_" + visualisationName);
    }
}
