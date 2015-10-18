/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Carl
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentField{
    
    public enum Type{
        ENTITY,
        TIMER,
        DISTANCE,
        ATTRIBUTE,
        TEMPLATE,
        FILEPATH
    }
    Type type();
}
