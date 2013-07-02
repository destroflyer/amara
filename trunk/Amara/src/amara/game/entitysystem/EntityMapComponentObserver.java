/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public interface EntityMapComponentObserver
{
    public void onComponentAdded(int entity, Class componentClass);
    public void onComponentChanged(int entity, Class componentClass);
    public void onComponentRemoved(int entity, Class componentClass);
}
