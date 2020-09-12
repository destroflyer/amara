package amara.applications.ingame.entitysystem.systems.spells;


import amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class SetAutoAttacksCastAnimationsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAttackComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAny(AutoAttackComponent.class)) {
            updateAnimation(entityWorld, entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(AutoAttackComponent.class)) {
            updateAnimation(entityWorld, entity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackComponent.class)) {
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.removeComponent(autoAttackEntity, CastAnimationComponent.class);
        }
    }

    private void updateAnimation(EntityWorld entityWorld, int entity) {
        AutoAttackAnimationComponent autoAttackAnimationComponent = entityWorld.getComponent(entity, AutoAttackAnimationComponent.class);
        if (autoAttackAnimationComponent != null) {
            int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.setComponent(autoAttackEntity, new CastAnimationComponent(autoAttackAnimationComponent.getAnimationEntity(), 0));
        }
    }
}
