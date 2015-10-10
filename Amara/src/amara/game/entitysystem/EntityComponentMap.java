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
    public Object setComponent(Integer entity, Object component);
    public Object removeComponent(Integer entity, Class componentClass);
    public void clearComponents(Integer entity);
    public void clear();
}