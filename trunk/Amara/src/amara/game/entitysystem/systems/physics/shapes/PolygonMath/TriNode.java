/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.util.HashSet;

/**
 *
 * @author Philipp
 */
public class TriNode
{
    private HashSet<TriNode> neighbors = new HashSet<TriNode>();
    
    public HashSet<TriNode> getNeighbors()
    {
        return neighbors;
    }
}
