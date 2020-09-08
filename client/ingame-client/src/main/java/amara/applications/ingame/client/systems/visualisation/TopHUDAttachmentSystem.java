package amara.applications.ingame.client.systems.visualisation;

import com.jme3.math.Vector3f;
import amara.libraries.entitysystem.EntityWorld;

public abstract class TopHUDAttachmentSystem extends SimpleHUDAttachmentSystem {

    public TopHUDAttachmentSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, Class<?> componentClass) {
        this(hudAttachmentsSystem, entityHeightMap, new Class[] { componentClass }, new Class[0]);
    }

    public TopHUDAttachmentSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, Class<?>[] primaryComponentClasses, Class<?>[] secondaryComponentClasses) {
        super(hudAttachmentsSystem, primaryComponentClasses, secondaryComponentClasses);
        this.entityHeightMap = entityHeightMap;
    }
    private EntityHeightMap entityHeightMap;

    @Override
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity) {
        return entityHeightMap.getWorldOffset(entity);
    }
}
