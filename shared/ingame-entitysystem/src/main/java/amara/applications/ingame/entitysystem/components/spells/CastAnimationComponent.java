package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class CastAnimationComponent {

    public CastAnimationComponent() {

    }

    public CastAnimationComponent(int animationEntity, int additionalLoops) {
        this.animationEntity = animationEntity;
        this.additionalLoops = additionalLoops;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int animationEntity;
    private int additionalLoops;

    public int getAnimationEntity() {
        return animationEntity;
    }

    public int getAdditionalLoops() {
        return additionalLoops;
    }
}
