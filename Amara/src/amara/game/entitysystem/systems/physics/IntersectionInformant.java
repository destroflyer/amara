/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics;

import intersections.Pair;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface IntersectionInformant
{
    public Set<Pair<Hitbox>> getEntries();
    
    public Set<Pair<Hitbox>> getRepeaters();
    
    public Set<Pair<Hitbox>> getLeavers();
}
