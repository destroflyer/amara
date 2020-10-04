package amara.applications.ingame.entitysystem.components.general;

import com.jme3.network.serializing.Serializable;

@Serializable
public class DeltaDescriptionComponent {

    public DeltaDescriptionComponent() {

    }

    public DeltaDescriptionComponent(String description) {
        this.description = description;
    }
    private String description;

    public String getDescription() {
        return description;
    }
}
