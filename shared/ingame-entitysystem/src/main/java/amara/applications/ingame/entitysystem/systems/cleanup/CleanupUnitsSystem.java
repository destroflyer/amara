package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.items.InventoryComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

public class CleanupUnitsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            IdleAnimationComponent.class,
            WalkAnimationComponent.class,
            AutoAttackAnimationComponent.class,
            DeathAnimationComponent.class,
            AnimationComponent.class,
            BaseAttributesComponent.class,
            AttributesPerLevelComponent.class,
            AutoAttackComponent.class,
            SpellsComponent.class,
            MapSpellsComponent.class,
            InventoryComponent.class,
            ScoreComponent.class
        );
        for (int entity : observer.getRemoved().getEntitiesWithAny(IdleAnimationComponent.class)) {
            int animationEntity = observer.getRemoved().getComponent(entity, IdleAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(WalkAnimationComponent.class)) {
            int animationEntity = observer.getRemoved().getComponent(entity, WalkAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackAnimationComponent.class)) {
            int animationEntity = observer.getRemoved().getComponent(entity, AutoAttackAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(DeathAnimationComponent.class)){
            int animationEntity = observer.getRemoved().getComponent(entity, DeathAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AnimationComponent.class)) {
            if (!entityWorld.hasEntity(entity)){
                int animationEntity = observer.getRemoved().getComponent(entity, AnimationComponent.class).getAnimationEntity();
                CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
            }
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(BaseAttributesComponent.class)) {
            int bonusAttributesEntity = observer.getRemoved().getComponent(entity, BaseAttributesComponent.class).getBonusAttributesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, bonusAttributesEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AttributesPerLevelComponent.class)) {
            int bonusAttributesEntity = observer.getRemoved().getComponent(entity, AttributesPerLevelComponent.class).getBonusAttributesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, bonusAttributesEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackComponent.class)) {
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, autoAttackEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(SpellsComponent.class)) {
            int[] spellEntities = observer.getRemoved().getComponent(entity, SpellsComponent.class).getSpellsEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, spellEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(MapSpellsComponent.class)) {
            int[] spellEntities = observer.getRemoved().getComponent(entity, MapSpellsComponent.class).getSpellsEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, spellEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(InventoryComponent.class)) {
            int[] itemEntities = observer.getRemoved().getComponent(entity, InventoryComponent.class).getItemEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, itemEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(ScoreComponent.class)) {
            int scoreEntity = observer.getRemoved().getComponent(entity, ScoreComponent.class).getScoreEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, scoreEntity);
        }
    }
}
