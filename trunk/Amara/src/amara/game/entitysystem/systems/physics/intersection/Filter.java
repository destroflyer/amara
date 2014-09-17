/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

/**
 *
 * @author Philipp
 */
public class Filter<T>
{
    public boolean pass(T a, T b)
    {
        return true;
    }
}
