/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import amara.libraries.physics.shapes.ConvexedOutline;

/**
 *
 * @author Carl
 */
public class MapObstacle {

    private ConvexedOutline convexedOutline;

    public void setConvexedOutline(ConvexedOutline convexedOutline) {
        this.convexedOutline = convexedOutline;
    }

    public ConvexedOutline getConvexedOutline() {
        return convexedOutline;
    }
}
