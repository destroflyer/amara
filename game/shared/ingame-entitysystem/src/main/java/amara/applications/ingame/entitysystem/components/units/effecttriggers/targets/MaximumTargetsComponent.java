package amara.applications.ingame.entitysystem.components.units.effecttriggers.targets;

import com.jme3.network.serializing.Serializable;

@Serializable
public class MaximumTargetsComponent {

    public MaximumTargetsComponent() {

    }

    public MaximumTargetsComponent(int maximum) {
        this.maximum = maximum;
    }
    private int maximum;

    public int getMaximum() {
        return maximum;
    }
}
