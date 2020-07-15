package amara.applications.ingame.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;

@Serializable
public class BuffVisualisationComponent {

    public BuffVisualisationComponent() {

    }

    public BuffVisualisationComponent(String name) {
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }
}
