/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class CleanupSpellsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            CastTypeComponent.class,
            SpellIndicatorComponent.class,
            SpellPassivesComponent.class,
            InstantEffectTriggersComponent.class,
            CastAnimationComponent.class,
            SpellTargetRulesComponent.class
        );
        for (int entity : observer.getRemoved().getEntitiesWithAny(CastTypeComponent.class)) {
            for (int spellEffectEntity : entityWorld.getEntitiesWithAny(CastedSpellComponent.class)) {
                int spellEntity = entityWorld.getComponent(spellEffectEntity, CastedSpellComponent.class).getSpellEntity();
                if (spellEntity == entity) {
                    CleanupUtil.tryCleanupEntity(entityWorld, spellEffectEntity);
                }
            }
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(SpellIndicatorComponent.class)) {
            int indicatorEntity = observer.getRemoved().getComponent(entity, SpellIndicatorComponent.class).getIndicatorEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, indicatorEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(SpellPassivesComponent.class)) {
            int[] passiveEntities = observer.getRemoved().getComponent(entity, SpellPassivesComponent.class).getPassiveEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, passiveEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(InstantEffectTriggersComponent.class)) {
            int[] effectTriggerEntities = observer.getRemoved().getComponent(entity, InstantEffectTriggersComponent.class).getEffectTriggerEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, effectTriggerEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(CastAnimationComponent.class)) {
            int animationEntity = observer.getRemoved().getComponent(entity, CastAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(SpellTargetRulesComponent.class)) {
            int targetRulesEntity = observer.getRemoved().getComponent(entity, SpellTargetRulesComponent.class).getTargetRulesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, targetRulesEntity);
        }
    }
}
