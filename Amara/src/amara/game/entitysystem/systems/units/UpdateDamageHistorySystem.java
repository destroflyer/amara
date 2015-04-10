/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import java.util.Set;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.game.UpdateGameTimeSystem;

/**
 *
 * @author Carl
 */
public class UpdateDamageHistorySystem implements EntitySystem{
    
    private static final int RESET_TIME = 10;
    private Set<Integer> entitiesWithInactiveDamageHistory;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        entitiesWithInactiveDamageHistory = entityWorld.getEntitiesWithAll(DamageHistoryComponent.class);
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PhysicalDamageComponent.class)){
            onDamageTaken(entityWorld, effectImpactEntity, DamageHistoryComponent.DamageType.PHYSICAL);
        }
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, MagicDamageComponent.class)){
            onDamageTaken(entityWorld, effectImpactEntity, DamageHistoryComponent.DamageType.MAGIC);
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
        int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetID();
        float damage = -1;
        switch(damageType){
            case PHYSICAL:
                damage = entityWorld.getComponent(effectImpactEntity, PhysicalDamageComponent.class).getValue();
                break;
            
            case MAGIC:
                damage = entityWorld.getComponent(effectImpactEntity, MagicDamageComponent.class).getValue();
                break;
        }
        int sourceEntity = -1;
        EffectCastSourceComponent effectCastSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceComponent.class);
        if(effectCastSourceComponent != null){
            sourceEntity = effectCastSourceComponent.getSourceEntity();
        }
        int sourceSpellEntity = -1;
        EffectCastSourceSpellComponent effectCastSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceSpellComponent.class);
        if(effectCastSourceSpellComponent != null){
            sourceSpellEntity = effectCastSourceSpellComponent.getSpellEntity();
        }
        DamageHistoryComponent.DamageHistoryEntry[] oldEntries = new DamageHistoryComponent.DamageHistoryEntry[0];
        float gameTime = UpdateGameTimeSystem.getGameTime(entityWorld);
        float firstDamageTime = gameTime;
        DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(targetEntity, DamageHistoryComponent.class);
        if(damageHistoryComponent != null){
            oldEntries = damageHistoryComponent.getEntries();
            firstDamageTime = damageHistoryComponent.getFirstDamageTime();
        }
        DamageHistoryComponent.DamageHistoryEntry[] newEntries = new DamageHistoryComponent.DamageHistoryEntry[oldEntries.length + 1];
        for(int i=0;i<oldEntries.length;i++){
            newEntries[i] = oldEntries[i];
        }
        newEntries[oldEntries.length] = new DamageHistoryComponent.DamageHistoryEntry(damageType, damage, sourceEntity, sourceSpellEntity);
        entityWorld.setComponent(targetEntity, new DamageHistoryComponent(newEntries, firstDamageTime, gameTime));
        entitiesWithInactiveDamageHistory.remove(targetEntity);
    }
}
