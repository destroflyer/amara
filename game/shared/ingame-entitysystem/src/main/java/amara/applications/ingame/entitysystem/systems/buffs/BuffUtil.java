package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.libraries.entitysystem.EntityWorld;

public class BuffUtil {

    public static boolean hasBuffs(EntityWorld entityWorld, int entity, int[] buffEntities) {
        for (int buffEntity : buffEntities) {
            if (!hasBuff(entityWorld, entity, buffEntity)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasBuff(EntityWorld entityWorld, int entity, int buffEntity) {
        BuffsComponent buffsComponent = entityWorld.getComponent(entity, BuffsComponent.class);
        if (buffsComponent != null) {
            return (getBuffStatusEntity(entityWorld, entity, buffEntity) != -1);
        }
        return false;
    }

    public static int getBuffStatusEntity(EntityWorld entityWorld, int entity, int buffEntity) {
        BuffsComponent buffsComponent = entityWorld.getComponent(entity, BuffsComponent.class);
        if (buffsComponent != null) {
            for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                int currentBuffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                if (currentBuffEntity == buffEntity) {
                    return buffStatusEntity;
                }
            }
        }
        return -1;
    }
}
