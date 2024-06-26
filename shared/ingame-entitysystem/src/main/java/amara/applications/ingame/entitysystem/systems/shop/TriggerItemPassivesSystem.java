/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.shop;

import java.util.HashMap;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.systems.units.PassiveUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class TriggerItemPassivesSystem implements EntitySystem{
    
    private HashMap<Integer, int[]> cachedItems = new HashMap<>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InventoryComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(InventoryComponent.class)){
            checkInventory(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(InventoryComponent.class)){
            checkInventory(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(InventoryComponent.class)){
            for(int itemEntity : observer.getRemoved().getComponent(entity, InventoryComponent.class).getItemEntities()){
                onItemRemoved(entityWorld, entity, itemEntity);
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
                onItemAdded(entityWorld, entity, newItemEntity);
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
                    onItemRemoved(entityWorld, entity, oldItemEntity);
                }
            }
        }
        cachedItems.put(entity, newItemEntities);
    }
    
    private void onItemAdded(EntityWorld entityWorld, int targetEntity, int itemEntity){
        ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
        if(itemPassivesComponent != null){
            PassiveUtil.addPassives(entityWorld, targetEntity, itemPassivesComponent.getPassiveEntities());
        }
    }
    
    private void onItemRemoved(EntityWorld entityWorld, int targetEntity, int itemEntity){
        ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
        if(itemPassivesComponent != null){
            PassiveUtil.removePassives(entityWorld, targetEntity, itemPassivesComponent.getPassiveEntities());
        }
    }
}
