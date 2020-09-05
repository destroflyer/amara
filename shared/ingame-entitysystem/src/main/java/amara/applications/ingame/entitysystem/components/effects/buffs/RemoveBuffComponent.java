package amara.applications.ingame.entitysystem.components.effects.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class RemoveBuffComponent {

    public RemoveBuffComponent() {

    }

    public RemoveBuffComponent(int... buffEntities) {
        this.buffEntities = buffEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] buffEntities;

    public int[] getBuffEntities(){
        return buffEntities;
    }
}
