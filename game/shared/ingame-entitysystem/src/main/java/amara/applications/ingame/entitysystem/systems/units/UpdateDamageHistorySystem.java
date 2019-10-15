/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.game.UpdateGameTimeSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateDamageHistorySystem implements EntitySystem{
    
    private static final int RESET_TIME = 10;
    private Set<Integer> entitiesWithInactiveDamageHistory;
    private HashMap<Integer, LinkedList<DamageHistoryComponent.DamageHistoryEntry>> damageEntriesMap = new HashMap<Integer, LinkedList<DamageHistoryComponent.DamageHistoryEntry>>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        entitiesWithInactiveDamageHistory = entityWorld.getEntitiesWithAny(DamageHistoryComponent.class);
        damageEntriesMap.clear();
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)){
            onDamageTaken(entityWorld, effectImpactEntity, DamageHistoryComponent.DamageType.PHYSICAL);
        }
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)){
            onDamageTaken(entityWorld, effectImpactEntity, DamageHistoryComponent.DamageType.MAGIC);
        }
        for(int entity : damageEntriesMap.keySet()){
            updateDamageHistory(entityWorld, entity);
        }
        for(int entity : entitiesWithInactiveDamageHistory){
            if(entityWorld.hasComponent(entity, IsAliveComponent.class)){
                DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(entity, DamageHistoryComponent.class);
                float timeSinceLastDamage = (UpdateGameTimeSystem.getGameTime(entityWorld) - damageHistoryComponent.getLastDamageTime());
                if(timeSinceLastDamage >= RESET_TIME){
                    entityWorld.removeComponent(entity, DamageHistoryComponent.class);
                }
            }
        }
    }
    
    private void onDamageTaken(EntityWorld entityWorld, int effectImpactEntity, DamageHistoryComponent.DamageType damageType){
        int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
        float damage = -1;
        switch(damageType){
            case PHYSICAL:
                damage = entityWorld.getComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class).getValue();
                break;
            
            case MAGIC:
                damage = entityWorld.getComponent(effectImpactEntity, ResultingMagicDamageComponent.class).getValue();
                break;
        }
        int sourceEntity = -1;
        EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
        if(effectSourceComponent != null){
            sourceEntity = effectSourceComponent.getSourceEntity();
        }
        int sourceSpellEntity = -1;
        EffectSourceSpellComponent effectSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceSpellComponent.class);
        if(effectSourceSpellComponent != null){
            sourceSpellEntity = effectSourceSpellComponent.getSpellEntity();
        }
        LinkedList<DamageHistoryComponent.DamageHistoryEntry> damageEntries = damageEntriesMap.computeIfAbsent(targetEntity, t -> new LinkedList<>());
        Iterator<DamageHistoryComponent.DamageHistoryEntry> iterator = damageEntries.iterator();
        int index = 0;
        while(iterator.hasNext()){
            DamageHistoryComponent.DamageHistoryEntry entry = iterator.next();
            if(damage > entry.getDamage()){
                break;
            }
            index++;
        }
        damageEntries.add(index, new DamageHistoryComponent.DamageHistoryEntry(damageType, damage, sourceEntity, sourceSpellEntity));
    }
    
    private void updateDamageHistory(EntityWorld entityWorld, int targetEntity){
        DamageHistoryComponent.DamageHistoryEntry[] oldEntries = new DamageHistoryComponent.DamageHistoryEntry[0];
        float gameTime = UpdateGameTimeSystem.getGameTime(entityWorld);
        float firstDamageTime = gameTime;
        DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(targetEntity, DamageHistoryComponent.class);
        if(damageHistoryComponent != null){
            oldEntries = damageHistoryComponent.getEntries();
            firstDamageTime = damageHistoryComponent.getFirstDamageTime();
        }
        LinkedList<DamageHistoryComponent.DamageHistoryEntry> damageEntries = damageEntriesMap.get(targetEntity);
        LinkedList<DamageHistoryComponent.DamageHistoryEntry> appliedEntries = damageEntries;
        float health = entityWorld.getComponent(targetEntity, HealthComponent.class).getValue();
        if(health < 1){
            appliedEntries = new LinkedList<>();
            while(health < 1){
                DamageHistoryComponent.DamageHistoryEntry entry = damageEntries.pop();
                health += entry.getDamage();
                appliedEntries.add(entry);
            }
        }
        DamageHistoryComponent.DamageHistoryEntry[] newEntries = new DamageHistoryComponent.DamageHistoryEntry[oldEntries.length + appliedEntries.size()];
        for(int i=0;i<oldEntries.length;i++){
            newEntries[i] = oldEntries[i];
        }
        for(int i=0;i<appliedEntries.size();i++){
            newEntries[oldEntries.length + i] = appliedEntries.get(i);
        }
        entityWorld.setComponent(targetEntity, new DamageHistoryComponent(newEntries, firstDamageTime, gameTime));
        entitiesWithInactiveDamageHistory.remove(targetEntity);
    }
}
