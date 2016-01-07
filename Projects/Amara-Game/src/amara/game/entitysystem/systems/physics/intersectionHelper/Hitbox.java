/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.EntityWrapper;
import amara.game.entitysystem.components.physics.HitboxComponent;
import amara.game.entitysystem.systems.physics.intersection.BoundAabb;
import amara.game.entitysystem.systems.physics.shapes.Shape;

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
