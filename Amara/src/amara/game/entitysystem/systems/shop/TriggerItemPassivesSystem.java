/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.shop;

import java.util.HashMap;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.items.passives.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;

/**
 *
 * @author Carl
 */
public class TriggerItemPassivesSystem implements EntitySystem{
    
    private HashMap<Integer, int[]> cachedItems = new HashMap<Integer, int[]>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(InventoryComponent.class)){
            checkInventory(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(InventoryComponent.class)){
            checkInventory(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(InventoryComponent.class)){
            for(int itemEntity : observer.getRemoved().getComponent(entity, InventoryComponent.class).getItemEntities()){
                trigger(entityWorld, itemEntity, false);
            }
        }
    }
    
    private void checkInventory(EntityWorld entityWorld, int entity){
        int[] oldItemEntities = cachedItems.get(entity);
        int[] newItemEntities = entityWorld.getComponent(entity, InventoryComponent.class).getItemEntities();
        boolean wasItemAdded;
        for(int newItemEntity : newItemEntities){
            wasItemAdded = true;
            if(oldItemEntities != null){
                for(int oldItemEntity : oldItemEntities){
                    if(newItemEntity == oldItemEntity){
                        wasItemAdded = false;
                        break;
                    }
                }
            }
            if(wasItemAdded){
                trigger(entityWorld, newItemEntity, true);
            }
        }
        if(oldItemEntities != null){
            boolean wasItemRemoved;
            for(int oldItemEntity : oldItemEntities){
                wasItemRemoved = true;
                for(int newItemEntity : newItemEntities){
                    if(newItemEntity == oldItemEntity){
                        wasItemRemoved = false;
                        break;
                    }
                }
                if(wasItemRemoved){
                    trigger(entityWorld, oldItemEntity, false);
                }
            }
        }
        cachedItems.put(entity, newItemEntities);
    }
    
    private void trigger(EntityWorld entityWorld, int itemEntity, boolean addedOrRemoved){
        ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
        if(itemPassivesComponent != null){
            int[] itemPassivesEntities = itemPassivesComponent.getPassiveEntities();
            for(int itemPassiveEntity : itemPassivesEntities){
                int[] effectTriggersEntities;
                if(addedOrRemoved){
                    effectTriggersEntities = entityWorld.getComponent(itemPassiveEntity, ItemAddedEffectTriggersComponent.class).getEffectTriggerEntities();
                }
                else{
                    effectTriggersEntities = entityWorld.getComponent(itemPassiveEntity, ItemRemovedEffectTriggersComponent.class).getEffectTriggerEntities();
                }
                for(int effectTriggerEntity : effectTriggersEntities){
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }
}
