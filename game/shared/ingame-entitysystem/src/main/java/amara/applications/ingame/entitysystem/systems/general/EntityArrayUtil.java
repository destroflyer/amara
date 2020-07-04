package amara.applications.ingame.entitysystem.systems.general;

import java.util.Arrays;

public class EntityArrayUtil {

    public static int[] add(int[] entities, int entity) {
        int[] newEntities = new int[entities.length + 1];
        System.arraycopy(entities, 0, newEntities, 0, entities.length);
        newEntities[entities.length] = entity;
        return newEntities;
    }

    public static int[] remove(int[] entities, int entity, boolean collapse) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] == entity) {
                int[] newEntities;
                if (collapse) {
                    newEntities = new int[entities.length - 1];
                    int currentIndex = 0;
                    for (int r = 0; r < entities.length; r++) {
                        if (r != i) {
                            newEntities[currentIndex] = entities[r];
                            currentIndex++;
                        }
                    }
                } else {
                    newEntities = Arrays.copyOf(entities, entities.length);
                    newEntities[i] = -1;
                }
                return newEntities;
            }
        }
        return entities;
    }

    public static int[] set(int[] entities, int index, int entity) {
        int[] newEntities;
        if (index < entities.length) {
            newEntities = Arrays.copyOf(entities, entities.length);
        } else {
            newEntities = new int[index + 1];
            for (int i = 0; i < index; i++) {
                newEntities[i] = ((i < entities.length) ? entities[i] : -1);
            }
        }
        newEntities[index] = entity;
        return newEntities;
    }
}
