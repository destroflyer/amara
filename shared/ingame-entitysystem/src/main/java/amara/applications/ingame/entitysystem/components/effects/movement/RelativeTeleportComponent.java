package amara.applications.ingame.entitysystem.components.effects.movement;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

@Serializable
public class RelativeTeleportComponent {

    public RelativeTeleportComponent() {

    }

    public RelativeTeleportComponent(Vector2f sourceParentPosition, Vector2f targetParentPosition) {
        this.sourceParentPosition = sourceParentPosition;
        this.targetParentPosition = targetParentPosition;
    }
    private Vector2f sourceParentPosition;
    private Vector2f targetParentPosition;

    public Vector2f getSourceParentPosition() {
        return sourceParentPosition;
    }

    public Vector2f getTargetParentPosition() {
        return targetParentPosition;
    }
}
