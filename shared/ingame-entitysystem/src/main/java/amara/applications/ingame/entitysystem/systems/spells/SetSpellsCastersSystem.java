package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

public class SetSpellsCastersSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAttackComponent.class, PassivesComponent.class, SpellsComponent.class, InventoryComponent.class, MapSpellsComponent.class);
        // AutoAttack
        for (int entity : observer.getNew().getEntitiesWithAny(AutoAttackComponent.class)) {
            updateCaster(entityWorld, entity, observer.getNew().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity());
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(AutoAttackComponent.class)) {
            updateCaster(entityWorld, entity, observer.getChanged().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity());
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackComponent.class)) {
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.removeComponent(autoAttackEntity, EffectSourceComponent.class);
        }
        //Passives
        for (int entity : observer.getNew().getEntitiesWithAny(PassivesComponent.class)) {
            updateCaster(entityWorld, entity, observer.getNew().getComponent(entity, PassivesComponent.class).getPassiveEntities());
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(PassivesComponent.class)) {
            updateCaster(entityWorld, entity, observer.getChanged().getComponent(entity, PassivesComponent.class).getPassiveEntities());
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(PassivesComponent.class)) {
            for (int passiveEntity : observer.getRemoved().getComponent(entity, PassivesComponent.class).getPassiveEntities()) {
                entityWorld.removeComponent(passiveEntity, EffectSourceComponent.class);
            }
        }
        //Spells
        for (int entity : observer.getNew().getEntitiesWithAny(SpellsComponent.class)) {
            updateCaster_Spells(entityWorld, entity, observer.getNew().getComponent(entity, SpellsComponent.class).getSpellsEntities());
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(SpellsComponent.class)) {
            updateCaster_Spells(entityWorld, entity, observer.getChanged().getComponent(entity, SpellsComponent.class).getSpellsEntities());
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(SpellsComponent.class)) {
            for (int spellEntity : observer.getRemoved().getComponent(entity, SpellsComponent.class).getSpellsEntities()) {
                entityWorld.removeComponent(spellEntity, EffectSourceComponent.class);
            }
        }
        // Items
        for (int entity : observer.getNew().getEntitiesWithAny(InventoryComponent.class)) {
            updateCaster_Inventory(entityWorld, entity, observer.getNew().getComponent(entity, InventoryComponent.class).getItemEntities());
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(InventoryComponent.class)) {
            updateCaster_Inventory(entityWorld, entity, observer.getChanged().getComponent(entity, InventoryComponent.class).getItemEntities());
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(InventoryComponent.class)) {
            for (int itemEntity : observer.getRemoved().getComponent(entity, InventoryComponent.class).getItemEntities()) {
                ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
                if (itemActiveComponent != null) {
                    entityWorld.removeComponent(itemActiveComponent.getSpellEntity(), EffectSourceComponent.class);
                }
                ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
                if (itemPassivesComponent != null) {
                    for (int itemPassiveEntity : itemPassivesComponent.getPassiveEntities()) {
                        entityWorld.removeComponent(itemPassiveEntity, EffectSourceComponent.class);
                    }
                }
            }
        }
        // Map Spells
        for (int entity : observer.getNew().getEntitiesWithAny(MapSpellsComponent.class)) {
            updateCaster_Spells(entityWorld, entity, observer.getNew().getComponent(entity, MapSpellsComponent.class).getSpellsEntities());
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(MapSpellsComponent.class)) {
            updateCaster_Spells(entityWorld, entity, observer.getChanged().getComponent(entity, MapSpellsComponent.class).getSpellsEntities());
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(MapSpellsComponent.class) ) {
            for (int spellEntity : observer.getRemoved().getComponent(entity, MapSpellsComponent.class).getSpellsEntities()) {
                entityWorld.removeComponent(spellEntity, EffectSourceComponent.class);
            }
        }
    }
    
    private void updateCaster_Spells(EntityWorld entityWorld, int casterEntity, int[] spellEntities) {
        for (int spellEntity : spellEntities) {
            if (spellEntity != -1) {
                updateCaster(entityWorld, casterEntity, spellEntity);
                SpellPassivesComponent spellPassivesComponent = entityWorld.getComponent(spellEntity, SpellPassivesComponent.class);
                if (spellPassivesComponent != null) {
                    updateCaster(entityWorld, casterEntity, spellPassivesComponent.getPassiveEntities());
                }
            }
        }
    }

    private void updateCaster_Inventory(EntityWorld entityWorld, int casterEntity, int[] itemsEntities) {
        for (int itemEntity : itemsEntities) {
            ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
            if (itemActiveComponent != null) {
                updateCaster(entityWorld, casterEntity, itemActiveComponent.getSpellEntity());
            }
            ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
            if (itemPassivesComponent != null) {
                updateCaster(entityWorld, casterEntity, itemPassivesComponent.getPassiveEntities());
            }
        }
    }

    private void updateCaster(EntityWorld entityWorld, int casterEntity, int[] spellOrPassiveEntities) {
        for (int targetEntity : spellOrPassiveEntities) {
            updateCaster(entityWorld, casterEntity, targetEntity);
        }
    }

    private void updateCaster(EntityWorld entityWorld, int casterEntity, int spellOrPassiveEntity) {
        entityWorld.setComponent(casterEntity, new EffectSourceComponent(casterEntity));
        entityWorld.setComponent(spellOrPassiveEntity, new EffectSourceSpellComponent(spellOrPassiveEntity));

        EffectSourceComponent oldSpellOrPassiveEffectSourceComponent = entityWorld.getComponent(spellOrPassiveEntity, EffectSourceComponent.class);
        if ((oldSpellOrPassiveEffectSourceComponent == null) || (oldSpellOrPassiveEffectSourceComponent.getSourceEntity() != casterEntity)) {
            entityWorld.setComponent(spellOrPassiveEntity, new EffectSourceComponent(casterEntity));
            LearnEffectTriggersComponent learnEffectTriggersComponent = entityWorld.getComponent(spellOrPassiveEntity, LearnEffectTriggersComponent.class);
            if (learnEffectTriggersComponent != null) {
                EffectTriggerUtil.triggerEffects(entityWorld, learnEffectTriggersComponent.getEffectTriggerEntities(), -1);
            }
        }
    }
}
