package amara.applications.ingame.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class OnBuffRemoveEffectTriggersComponent {

    public OnBuffRemoveEffectTriggersComponent() {

    }

    public OnBuffRemoveEffectTriggersComponent(int... effectTriggerEntities) {
        this.effectTriggerEntities = effectTriggerEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectTriggerEntities;

    public int[] getEffectTriggerEntities() {
        return effectTriggerEntities;
    }
}
