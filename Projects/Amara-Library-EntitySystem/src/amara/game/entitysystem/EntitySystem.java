/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public interface EntitySystem
{
    public void update(EntityWorld entityWorld, float deltaSeconds);
}
