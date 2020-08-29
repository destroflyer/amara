package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class NoBuffTargetsTriggerComponent {

    public NoBuffTargetsTriggerComponent() {

    }

    public NoBuffTargetsTriggerComponent(int... buffEntities) {
        this.buffEntities = buffEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] buffEntities;

    public int[] getBuffEntities() {
        return buffEntities;
    }
}
