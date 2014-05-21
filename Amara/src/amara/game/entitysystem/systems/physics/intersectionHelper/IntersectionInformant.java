/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.systems.physics.intersection.Pair;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface IntersectionInformant
{
    public Set<Pair<Integer>> getEntries(EntityWorld entityWorld);
    
    public Set<Pair<Integer>> getRepeaters(EntityWorld entityWorld);
    
    public Set<Pair<Integer>> getLeavers(EntityWorld entityWorld);
}