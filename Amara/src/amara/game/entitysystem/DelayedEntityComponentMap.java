/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public interface DelayedEntityComponentMap extends EntityComponentMapReadonly
{
    public void setComponent(int entity, Object component);
    public void removeComponent(int entity, Class componentClass);
    public void clearComponents(int entity);
    public void applyChanges();
}
