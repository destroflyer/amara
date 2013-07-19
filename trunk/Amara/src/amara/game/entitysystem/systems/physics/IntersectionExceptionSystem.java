/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.IntersectionExceptionComponent;
import intersections.Pair;

/**
 *
 * @author Philipp
 */
public class IntersectionExceptionSystem implements EntitySystem
{
    private IntersectionInformant info;

    public IntersectionExceptionSystem(IntersectionInformant info)
    {
        this.info = info;
    }
    
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(Pair<Hitbox> pair: info.getEntries())
        {
            if(entityWorld.getCurrent().hasAnyComponent(pair.getA().getId(), IntersectionExceptionComponent.class))
            {
                if(entityWorld.getCurrent().hasAnyComponent(pair.getB().getId(), IntersectionExceptionComponent.class))
                {
                    throw new UnsupportedOperationException("KÄSEKUCHEN!");
                }
            }
        }
    }
    
}
