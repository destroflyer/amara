/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.spells.SpellPassivesComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.entitysystem.systems.units.PassiveUtil;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class TriggerSpellsPassivesSystem implements EntitySystem{
    
    private HashMap<Integer, int[]> cachedSpells = new HashMap<>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(SpellsComponent.class)){
            checkSpells(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(SpellsComponent.class)){
            checkSpells(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(SpellsComponent.class)){
            for(int spellEntity : observer.getRemoved().getComponent(entity, SpellsComponent.class).getSpellsEntities()){
                onSpellRemoved(entityWorld, entity, spellEntity);
            }
        }
    }
    
    private void checkSpells(EntityWorld entityWorld, int entity){
        int[] oldSpellEntities = cachedSpells.get(entity);
        int[] newSpellEntities = entityWorld.getComponent(entity, SpellsComponent.class).getSpellsEntities();
        boolean wasSpellAdded;
        for(int newSpellEntity : newSpellEntities){
            wasSpellAdded = true;
            if(oldSpellEntities != null){
                for(int oldSpellEntity : oldSpellEntities){
                    if(newSpellEntity == oldSpellEntity){
                        wasSpellAdded = false;
                        break;
                    }
                }
            }
            if(wasSpellAdded){
                onSpellAdded(entityWorld, entity, newSpellEntity);
            }
        }
        if(oldSpellEntities != null){
            boolean wasSpellRemoved;
            for(int oldSpellEntity : oldSpellEntities){
                wasSpellRemoved = true;
                for(int newSpellEntity : newSpellEntities){
                    if(newSpellEntity == oldSpellEntity){
                        wasSpellRemoved = false;
                        break;
                    }
                }
                if(wasSpellRemoved){
                    onSpellRemoved(entityWorld, entity, oldSpellEntity);
                }
            }
        }
        cachedSpells.put(entity, newSpellEntities);
    }
    
    private void onSpellAdded(EntityWorld entityWorld, int targetEntity, int spellEntity){
        SpellPassivesComponent spellPassivesComponent = entityWorld.getComponent(spellEntity, SpellPassivesComponent.class);
        if(spellPassivesComponent != null){
            PassiveUtil.addPassives(entityWorld, targetEntity, spellPassivesComponent.getPassiveEntities());
        }
    }
    
    private void onSpellRemoved(EntityWorld entityWorld, int targetEntity, int spellEntity){
        SpellPassivesComponent spellPassivesComponent = entityWorld.getComponent(spellEntity, SpellPassivesComponent.class);
        if(spellPassivesComponent != null){
            PassiveUtil.removePassives(entityWorld, targetEntity, spellPassivesComponent.getPassiveEntities());
        }
    }
}
