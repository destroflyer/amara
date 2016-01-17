/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.EntityWorld;
import amara.libraries.physics.intersection.*;

/**
 *
 * @author Philipp
 */
public interface IntersectionInformant
{
    public void updateHitboxes(EntityWorld entityWorld);
    public IntersectionTracker<Pair<Integer>> getTracker(EntityWorld entityWorld, Object key);
}
