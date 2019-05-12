/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells.casting;

import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetCooldownOnCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellComponent.class)){
            int spellEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getSpellEntity();
            setOnCooldown_Active(entityWorld, casterEntity, spellEntity);
        }
    }
    
    public static void setOnCooldown_Active(EntityWorld entityWorld, int casterEntity, int spellEntity){
        setOnCooldown(entityWorld, spellEntity);
        //Unique Actives
        if(entityWorld.hasComponent(spellEntity, UniqueComponent.class)){
            NameComponent nameComponent = entityWorld.getComponent(spellEntity, NameComponent.class);
            if(nameComponent != null){
                InventoryComponent inventoryComponent = entityWorld.getComponent(casterEntity, InventoryComponent.class);
                if(inventoryComponent != null){
                    for(int itemEntity : inventoryComponent.getItemEntities()){
                        ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
                        if(itemActiveComponent != null){
                            int tmpSpellEntity = itemActiveComponent.getSpellEntity();
                            if((tmpSpellEntity != spellEntity) && entityWorld.hasComponent(tmpSpellEntity, UniqueComponent.class)){
                                NameComponent tmpNameComponent = entityWorld.getComponent(tmpSpellEntity, NameComponent.class);
                                if((tmpNameComponent != null) && (tmpNameComponent.getName().equals(nameComponent.getName()))){
                                    setOnCooldown(entityWorld, tmpSpellEntity);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void setOnCooldown(EntityWorld entityWorld, int spellEntity){
        CooldownComponent cooldownComponent = entityWorld.getComponent(spellEntity, CooldownComponent.class);
        if(cooldownComponent != null){
            entityWorld.setComponent(spellEntity, new RemainingCooldownComponent(cooldownComponent.getDuration()));
        }
    }
}
