package amara.game.entitysystem;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DefaultComponentEqualityDefinition implements ComponentEqualityDefinition {
    public static final DefaultComponentEqualityDefinition SINGLETON = new DefaultComponentEqualityDefinition();
    
    public boolean areComponentsEqual(Object componentA, Object componentB) {
        if (componentA.equals(componentB)) {
            return true;
        }
        Class componentClass = componentA.getClass();
        if (componentB.getClass() != componentClass) {
            return false;
        }
        for (Field field : componentClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(!equals(field.get(componentA), field.get(componentB))) {
                    return false;
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IllegalAccessException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        if (componentClass.getSuperclass() != Object.class) {
            System.err.println("components with superclasses not supported by ComponentMapObserver.componentsEqual");
        }
        return true;
    }
    
    private boolean equals(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass().isArray() && b.getClass().isArray()) {
            int length = Array.getLength(a);
            if (length > 0 && !a.getClass().getComponentType().equals(b.getClass().getComponentType())) {
                return false;
            }
            if (Array.getLength(b) != length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (!equals(Array.get(a, i), Array.get(b, i))) {
                    return false;
                }
            }
            return true;
        }
        return a.equals(b);
    }

}
