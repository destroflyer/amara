/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.physics.intersectionHelper;

import amara.applications.ingame.entitysystem.components.physics.HitboxComponent;
import amara.libraries.entitysystem.EntityWrapper;
import amara.libraries.physics.intersection.BoundAabb;
import amara.libraries.physics.shapes.Shape;

/**
 *
 * @author Philipp
 */
public class Hitbox implements BoundAabb
{
    EntityWrapper entity;

    public Hitbox(EntityWrapper wrapper)
    {
        entity = wrapper;
    }

    public int getId()
    {
        return entity.getId();
    }

    public Shape getShape()
    {
        return entity.getComponent(HitboxComponent.class).getShape();
    }

    public double getMinX()
    {
        return getShape().getMinX();
    }

    public double getMinY()
    {
        return getShape().getMinY();
    }

    public double getMaxX()
    {
        return getShape().getMaxX();
    }

    public double getMaxY()
    {
        return getShape().getMaxY();
    }
}
