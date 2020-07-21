package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

@Serializable
public class CastCostComponent {

    public CastCostComponent() {

    }

    public CastCostComponent(int costEntity) {
        this.costEntity = costEntity;
    }
    private int costEntity;

    public int getCostEntity() {
        return costEntity;
    }
}
