package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

@Serializable
public class SpellUpgradeDescriptionComponent {

    public SpellUpgradeDescriptionComponent() {

    }

    public SpellUpgradeDescriptionComponent(String description) {
        this.description = description;
    }
    private String description;

    public String getDescription() {
        return description;
    }
}
