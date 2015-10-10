/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SetSpellsCastersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAttackComponent.class, SpellsComponent.class, InventoryComponent.class);
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
            }
        }
    }
    
    private void updateCaster_Spells(EntityWorld entityWorld, int casterEntity, int[] spellEntities){
        for(int spellEntity : spellEntities){
            updateCaster(entityWorld, casterEntity, spellEntity);
        }
    }
    
    private void updateCaster_Inventory(EntityWorld entityWorld, int casterEntity, int[] itemsEntities){
        for(int itemEntity : itemsEntities){
            ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
            if(itemActiveComponent != null){
                updateCaster(entityWorld, casterEntity, itemActiveComponent.getSpellEntity());
            }
        }
    }
    
    private void updateCaster(EntityWorld entityWorld, int casterEntity, int spellEntity){
        entityWorld.setComponent(casterEntity, new EffectCastSourceComponent(casterEntity));
        entityWorld.setComponent(spellEntity, new EffectCastSourceComponent(casterEntity));
        entityWorld.setComponent(spellEntity, new EffectCastSourceSpellComponent(spellEntity));
    }
}
