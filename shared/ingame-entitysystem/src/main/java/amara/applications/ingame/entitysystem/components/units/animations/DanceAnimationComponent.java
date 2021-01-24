package amara.applications.ingame.entitysystem.components.units.animations;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class DanceAnimationComponent {

    public DanceAnimationComponent() {

    }

    public DanceAnimationComponent(int animationEntity) {
        this.animationEntity = animationEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int animationEntity;

    public int getAnimationEntity() {
        return animationEntity;
    }
}
