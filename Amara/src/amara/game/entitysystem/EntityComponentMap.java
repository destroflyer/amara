/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public interface EntityComponentMap extends EntityComponentMapReadonly
{
    public Object setComponent(int entity, Object component);
    public Object removeComponent(int entity, Class componentClass);
    public void clearComponents(int entity);
    public void clear();
}