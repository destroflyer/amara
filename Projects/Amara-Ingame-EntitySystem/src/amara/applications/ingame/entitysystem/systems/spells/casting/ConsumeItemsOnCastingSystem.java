/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells.casting;

import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ConsumeItemsOnCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellComponent.class, InventoryComponent.class)){
            int spellEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getSpellEntity();
            int[] itemEntities = entityWorld.getComponent(casterEntity, InventoryComponent.class).getItemEntities();
            for(int i=0;i<itemEntities.length;i++){
                ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntities[i], ItemActiveComponent.class);
                if((itemActiveComponent != null) && (itemActiveComponent.getSpellEntity() == spellEntity) && itemActiveComponent.isConsumable()){
                    int[] newItemEntities = new int[itemEntities.length];
                    for(int r=0;r<itemEntities.length;r++){
                        newItemEntities[r] = ((r != i)?itemEntities[r]:-1);
                    }
                    entityWorld.setComponent(casterEntity, new InventoryComponent(newItemEntities));
                    break;
                }
            }
        }
    }
}
