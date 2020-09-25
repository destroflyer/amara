package amara.applications.ingame.client.systems.visualisation;

import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.scene.Spatial;

public abstract class SimpleVisualAttachmentSystem implements EntitySystem {

    public SimpleVisualAttachmentSystem(Class<?>[] primaryComponentClasses, Class<?>[] secondaryComponentClasses) {
        this.primaryComponentClasses = primaryComponentClasses;
        this.secondaryComponentClasses = secondaryComponentClasses;

        allComponentClasses = new Class[primaryComponentClasses.length + secondaryComponentClasses.length];
        for (int i = 0; i < primaryComponentClasses.length; i++) {
            allComponentClasses[i] = primaryComponentClasses[i];
        }
        for (int i = 0; i < secondaryComponentClasses.length; i++) {
            allComponentClasses[primaryComponentClasses.length + i] = secondaryComponentClasses[i];
        }
    }
    private Class<?>[] primaryComponentClasses;
    private Class<?>[] secondaryComponentClasses;
    private Class<?>[] allComponentClasses;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, allComponentClasses);
        for (int entity : observer.getNew().getEntitiesWithAny(primaryComponentClasses)) {
            if (shouldHaveVisualAttachment(entityWorld, entity)) {
                createAndAttachVisualAttachment(entityWorld, entity);
            }
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(primaryComponentClasses)) {
            if (shouldHaveVisualAttachment(entityWorld, entity)) {
                Spatial visualAttachment = getVisualAttachment(entity);
                if (visualAttachment == null) {
                    visualAttachment = createAndAttachVisualAttachment(entityWorld, entity);
                }
                updateVisualAttachment(entityWorld, entity, visualAttachment);
            } else {
                detach(entity);
            }
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(primaryComponentClasses)) {
            if (!shouldHaveVisualAttachment(entityWorld, entity)) {
                detach(entity);
            }
        }
        for (Class<?> secondaryComponentClass : secondaryComponentClasses) {
            for (int entity : observer.getNew().getEntitiesWithAny(secondaryComponentClass)) {
                onSecondaryComponentUpdated(entityWorld, entity, secondaryComponentClass);
            }
            for (int entity : observer.getChanged().getEntitiesWithAny(secondaryComponentClass)) {
                onSecondaryComponentUpdated(entityWorld, entity, secondaryComponentClass);
            }
            for (int entity : observer.getRemoved().getEntitiesWithAny(secondaryComponentClass)) {
                onSecondaryComponentUpdated(entityWorld, entity, secondaryComponentClass);
            }
        }
    }

    private void onSecondaryComponentUpdated(EntityWorld entityWorld, int secondaryEntity, Class<?> secondaryComponentClass) {
        int primaryEntity = getPrimaryEntityBySecondaryComponent(entityWorld, secondaryEntity, secondaryComponentClass);
        if (primaryEntity != -1) {
            Spatial visualAttachment = getVisualAttachment(primaryEntity);
            if (visualAttachment != null) {
                updateVisualAttachment(entityWorld, primaryEntity, visualAttachment);
            }
        }
    }

    protected int getPrimaryEntityBySecondaryComponent(EntityWorld entityWorld, int entity, Class<?> secondaryComponentClass) {
        return entity;
    }

    protected Spatial createAndAttachVisualAttachment(EntityWorld entityWorld, int entity) {
        Spatial visualAttachment = createVisualAttachment(entityWorld, entity);
        visualAttachment.setName(getVisualAttachmentID(entity));
        updateVisualAttachment(entityWorld, entity, visualAttachment);
        attach(entity, visualAttachment);
        return visualAttachment;
    }

    protected abstract void attach(int entity, Spatial visualAttachment);

    protected abstract void detach(int entity);

    protected abstract Spatial getVisualAttachment(int entity);

    protected boolean shouldHaveVisualAttachment(EntityWorld entityWorld, int entity) {
        return entityWorld.hasAllComponents(entity, primaryComponentClasses);
    }

    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);

    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment) {

    }

    protected String getVisualAttachmentID(int entity) {
        return ("visualAttachment_" + hashCode() + "_" + entity);
    }
}
