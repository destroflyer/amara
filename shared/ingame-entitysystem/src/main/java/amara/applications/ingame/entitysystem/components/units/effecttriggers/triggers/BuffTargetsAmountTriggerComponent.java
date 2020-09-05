package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class BuffTargetsAmountTriggerComponent {

    public BuffTargetsAmountTriggerComponent() {

    }

    public BuffTargetsAmountTriggerComponent(int buffEntity, int amount) {
        this.buffEntity = buffEntity;
        this.amount = amount;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;
    private int amount;

    public int getBuffEntity() {
        return buffEntity;
    }

    public int getAmount() {
        return amount;
    }
}
