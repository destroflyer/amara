package amara.applications.ingame.entitysystem.components.effects.general;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class RemoveEffectTriggersComponent {

    public RemoveEffectTriggersComponent() {

    }

    public RemoveEffectTriggersComponent(int[] effectTriggerEntities, boolean removeEntities) {
        this.effectTriggerEntities = effectTriggerEntities;
        this.removeEntities = removeEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectTriggerEntities;
    private boolean removeEntities;

    public int[] getEffectTriggerEntities() {
        return effectTriggerEntities;
    }

    public boolean isRemoveEntities() {
        return removeEntities;
    }
}
