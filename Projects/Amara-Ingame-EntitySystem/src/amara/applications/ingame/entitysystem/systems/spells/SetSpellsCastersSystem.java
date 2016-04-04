/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetSpellsCastersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAttackComponent.class, PassivesComponent.class, SpellsComponent.class, InventoryComponent.class, MapSpellsComponent.class);
        //AutoAttack
        for(int entity : observer.getNew().getEntitiesWithAll(AutoAttackComponent.class)){
            updateCaster(entityWorld, entity, observer.getNew().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AutoAttackComponent.class)){
            updateCaster(entityWorld, entity, observer.getChanged().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AutoAttackComponent.class)){
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.removeComponent(autoAttackEntity, EffectCastSourceComponent.class);
        }
        //Passives
        for(int entity : observer.getNew().getEntitiesWithAll(PassivesComponent.class)){
            updateCaster_Passives(entityWorld, entity, observer.getNew().getComponent(entity, PassivesComponent.class).getPassiveEntities());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(PassivesComponent.class)){
            updateCaster_Passives(entityWorld, entity, observer.getChanged().getComponent(entity, PassivesComponent.class).getPassiveEntities());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(PassivesComponent.class)){
            for(int passiveEntity : observer.getRemoved().getComponent(entity, PassivesComponent.class).getPassiveEntities()){
                entityWorld.removeComponent(passiveEntity, EffectCastSourceComponent.class);
            }
        }
        //Spells
        for(int entity : observer.getNew().getEntitiesWithAll(SpellsComponent.class)){
            updateCaster_Spells(entityWorld, entity, observer.getNew().getComponent(entity, SpellsComponent.class).getSpellsEntities());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(SpellsComponent.class)){
            updateCaster_Spells(entityWorld, entity, observer.getChanged().getComponent(entity, SpellsComponent.class).getSpellsEntities());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(SpellsComponent.class)){
            for(int spellEntity : observer.getRemoved().getComponent(entity, SpellsComponent.class).getSpellsEntities()){
                entityWorld.removeComponent(spellEntity, EffectCastSourceComponent.class);
            }
        }
        //Items
        for(int entity : observer.getNew().getEntitiesWithAll(InventoryComponent.class)){
            updateCaster_Inventory(entityWorld, entity, observer.getNew().getComponent(entity, InventoryComponent.class).getItemEntities());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(InventoryComponent.class)){
            updateCaster_Inventory(entityWorld, entity, observer.getChanged().getComponent(entity, InventoryComponent.class).getItemEntities());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(InventoryComponent.class)){
            for(int itemEntity : observer.getRemoved().getComponent(entity, InventoryComponent.class).getItemEntities()){
                ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
                if(itemActiveComponent != null){
                    entityWorld.removeComponent(itemActiveComponent.getSpellEntity(), EffectCastSourceComponent.class);
                }
                ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
                if(itemPassivesComponent != null){
                    for(int itemPassiveEntity : itemPassivesComponent.getPassiveEntities()){
                        entityWorld.removeComponent(itemPassiveEntity, EffectCastSourceComponent.class);
                    }
                }
            }
        }
        //Map Spells
        for(int entity : observer.getNew().getEntitiesWithAll(MapSpellsComponent.class)){
            updateCaster_Spells(entityWorld, entity, observer.getNew().getComponent(entity, MapSpellsComponent.class).getSpellsEntities());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(MapSpellsComponent.class)){
            updateCaster_Spells(entityWorld, entity, observer.getChanged().getComponent(entity, MapSpellsComponent.class).getSpellsEntities());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(MapSpellsComponent.class)){
            for(int spellEntity : observer.getRemoved().getComponent(entity, MapSpellsComponent.class).getSpellsEntities()){
                entityWorld.removeComponent(spellEntity, EffectCastSourceComponent.class);
            }
        }
    }
    
    private void updateCaster_Passives(EntityWorld entityWorld, int casterEntity, int[] passivesEntities){
        for(int passiveEntity : passivesEntities){
            updateCaster(entityWorld, casterEntity, passiveEntity);
        }
    }
    
    private void updateCaster_Spells(EntityWorld entityWorld, int casterEntity, int[] spellEntities){
        for(int spellEntity : spellEntities){
            if(spellEntity != -1){
                updateCaster(entityWorld, casterEntity, spellEntity);
            }
        }
    }
    
    private void updateCaster_Inventory(EntityWorld entityWorld, int casterEntity, int[] itemsEntities){
        for(int itemEntity : itemsEntities){
            ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
            if(itemActiveComponent != null){
                updateCaster(entityWorld, casterEntity, itemActiveComponent.getSpellEntity());
            }
            ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
            if(itemPassivesComponent != null){
                updateCaster_Passives(entityWorld, casterEntity, itemPassivesComponent.getPassiveEntities());
            }
        }
    }
    
    private void updateCaster(EntityWorld entityWorld, int casterEntity, int targetEntity){
        entityWorld.setComponent(casterEntity, new EffectCastSourceComponent(casterEntity));
        entityWorld.setComponent(targetEntity, new EffectCastSourceComponent(casterEntity));
        entityWorld.setComponent(targetEntity, new EffectCastSourceSpellComponent(targetEntity));
    }
}
