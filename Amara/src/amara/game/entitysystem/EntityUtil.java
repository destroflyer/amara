/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

/**
 *
 * @author Carl
 */
public class EntityUtil{
    
    public static void transferComponents(EntityWorld entityWorld, Integer sourceEntity, Integer targetEntity, Class[] componentClasses){
        transferComponents(entityWorld.getWrapped(sourceEntity), entityWorld.getWrapped(targetEntity), componentClasses);
    }
    
    public static void transferComponents(EntityWrapper source, EntityWrapper targetEntity, Class[] componentClasses){
        for(Class componentClass : componentClasses){
            Object component = source.getComponent(componentClass);
            if(component != null){
                targetEntity.setComponent(component);
            }
        }
    }
}
