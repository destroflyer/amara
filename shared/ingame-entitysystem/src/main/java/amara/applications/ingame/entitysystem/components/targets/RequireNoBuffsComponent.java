package amara.applications.ingame.entitysystem.components.targets;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class RequireNoBuffsComponent {

    public RequireNoBuffsComponent() {

    }

    public RequireNoBuffsComponent(int... buffEntities) {
        this.buffEntities = buffEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] buffEntities;

    public int[] getBuffEntities() {
        return buffEntities;
    }
}
