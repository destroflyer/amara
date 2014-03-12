package amara.engine.applications.ingame.client.systems.debug;

import amara.game.entitysystem.systems.physics.shapes.*;

public class ShapeMesh extends ConnectedPointsMesh{

    public ShapeMesh(SimpleConvex simpleConvex){
        super((float) simpleConvex.getX(), (float) simpleConvex.getY(), simpleConvex.getBase());
    }
}