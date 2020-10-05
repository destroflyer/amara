package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntityWorld;

public class StackUtil {

    public static void addStacks(EntityWorld entityWorld, int entity, int buffEntity, int stacks) {
        setStacks(entityWorld, entity, buffEntity, stacks, true);
    }

    public static void setStacks(EntityWorld entityWorld, int entity, int buffEntity, int stacks) {
        setStacks(entityWorld, entity, buffEntity, stacks, false);
    }

    private static void setStacks(EntityWorld entityWorld, int entity, int buffEntity, int stacks, boolean add) {
        int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, entity, buffEntity);
        if (buffStatusEntity != -1) {
            int newStacks = stacks;
            if (add) {
                StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
                if (stacksComponent != null) {
                    newStacks += stacksComponent.getStacks();
                }
            }
            entityWorld.setComponent(buffStatusEntity, new StacksComponent(newStacks));
            int targetEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        }
    }
}
