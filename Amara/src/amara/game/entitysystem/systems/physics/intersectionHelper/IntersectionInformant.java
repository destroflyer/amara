/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public interface IntersectionInformant
{
    public Set<Pair<Integer>> getEntries();
    
    public Set<Pair<Integer>> getRepeaters();
    
    public Set<Pair<Integer>> getLeavers();
}
