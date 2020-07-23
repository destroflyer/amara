package amara.applications.ingame.entitysystem.components.targets;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class RequireEntityComponent {

    public RequireEntityComponent() {

    }

    public RequireEntityComponent(int entity) {
        this.entity = entity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int entity;

    public int getEntity() {
        return entity;
    }
}
