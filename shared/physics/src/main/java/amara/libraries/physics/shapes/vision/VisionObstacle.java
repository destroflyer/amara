/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.vision;

import amara.libraries.physics.shapes.ConvexedOutline;

/**
 *
 * @author Carl
 */
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
