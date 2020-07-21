package amara.applications.ingame.entitysystem.components.spells;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class LearnEffectTriggersComponent {

    public LearnEffectTriggersComponent() {

    }

    public LearnEffectTriggersComponent(int... effectTriggerEntities) {
        this.effectTriggerEntities = effectTriggerEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectTriggerEntities;

    public int[] getEffectTriggerEntities() {
        return effectTriggerEntities;
    }
}
