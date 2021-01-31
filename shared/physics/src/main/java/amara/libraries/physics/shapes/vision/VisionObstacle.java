package amara.libraries.physics.shapes.vision;

import amara.libraries.physics.shapes.ConvexedOutline;

public class VisionObstacle {

    public VisionObstacle(ConvexedOutline convexedOutline, boolean isBlockingInside) {
        this.convexedOutline = convexedOutline;
        this.isBlockingInside = isBlockingInside;
    }
    private ConvexedOutline convexedOutline;
    private boolean isBlockingInside;

    public ConvexedOutline getConvexedOutline() {
        return convexedOutline;
    }

    public boolean isBlockingInside() {
        return isBlockingInside;
    }
}
