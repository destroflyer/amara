package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class CurrentCastEffectCastsComponent {

    public CurrentCastEffectCastsComponent() {

    }

    public CurrentCastEffectCastsComponent(int... effectCastEntities) {
        this.effectCastEntities = effectCastEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectCastEntities;

    public int[] getEffectCastEntities() {
        return effectCastEntities;
    }
}
