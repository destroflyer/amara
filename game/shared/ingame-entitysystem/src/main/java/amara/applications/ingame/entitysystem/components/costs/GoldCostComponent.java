package amara.applications.ingame.entitysystem.components.costs;

import com.jme3.network.serializing.Serializable;

@Serializable
public class GoldCostComponent {

    public GoldCostComponent() {

    }

    public GoldCostComponent(float gold) {
        this.gold = gold;
    }
    private float gold;

    public float getGold() {
        return gold;
    }
}
