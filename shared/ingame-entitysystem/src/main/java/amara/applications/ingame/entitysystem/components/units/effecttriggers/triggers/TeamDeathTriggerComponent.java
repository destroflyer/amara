package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class TeamDeathTriggerComponent {

    public TeamDeathTriggerComponent() {

    }

    public TeamDeathTriggerComponent(int... excludedEntities) {
        this.excludedEntities = excludedEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] excludedEntities;

    public int[] getExcludedEntities() {
        return excludedEntities;
    }
}
