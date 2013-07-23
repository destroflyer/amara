/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.EntityWrapper;
import amara.game.entitysystem.components.physics.HitboxComponent;
import intersections.HasShape;
import shapes.Shape;

/**
 *
 * @author Philipp
 */
public class Hitbox implements HasShape
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
}
