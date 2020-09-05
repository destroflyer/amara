package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class CollisionTriggerComponent {

    public CollisionTriggerComponent() {

    }

    public CollisionTriggerComponent(int targetRulesEntity) {
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public int getTargetRulesEntity() {
        return targetRulesEntity;
    }
}
