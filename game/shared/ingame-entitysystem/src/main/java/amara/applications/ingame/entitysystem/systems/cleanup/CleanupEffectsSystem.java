/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.effects.audio.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.effects.visuals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PlayAudioComponent.class, AddBuffComponent.class, AddBuffAreaComponent.class, AddKnockupComponent.class, SpawnComponent.class, AddAutoAttackSpellEffectsComponent.class, AddSpellsSpellEffectsComponent.class, PlayAnimationComponent.class);
        //audio
        for(int entity : observer.getRemoved().getEntitiesWithAll(PlayAudioComponent.class)){
            int[] audioEntities = observer.getRemoved().getComponent(entity, PlayAudioComponent.class).getAudioEntities();
            for(int audioEntity : audioEntities){
                CleanupUtil.tryCleanupEntity(entityWorld, audioEntity);
            }
        }
        //buffs
        for(int entity : observer.getRemoved().getEntitiesWithAll(AddBuffComponent.class)){
            int buffEntity = observer.getRemoved().getComponent(entity, AddBuffComponent.class).getBuffEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, buffEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AddBuffAreaComponent.class)){
            int buffAreaEntity = observer.getRemoved().getComponent(entity, AddBuffAreaComponent.class).getBuffAreaEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, buffAreaEntity);
        }
        //crowdcontrol
        for(int entity : observer.getRemoved().getEntitiesWithAll(AddKnockupComponent.class)){
            int knockupEntity = observer.getRemoved().getComponent(entity, AddKnockupComponent.class).getKnockupEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, knockupEntity);
        }
        //spawns
        for(int entity : observer.getRemoved().getEntitiesWithAll(SpawnComponent.class)){
            int[] spawnInformationEntities = observer.getRemoved().getComponent(entity, SpawnComponent.class).getSpawnInformationEntites();
            for(int spawnInformationEntity : spawnInformationEntities){
                CleanupUtil.tryCleanupEntity(entityWorld, spawnInformationEntity);
            }
        }
        //spells
        for(int entity : observer.getRemoved().getEntitiesWithAll(AddAutoAttackSpellEffectsComponent.class)){
            int[] spellEffectEntities = observer.getRemoved().getComponent(entity, AddAutoAttackSpellEffectsComponent.class).getSpellEffectEntities();
            for(int spellEffectEntity : spellEffectEntities){
                CleanupUtil.tryCleanupEntity(entityWorld, spellEffectEntity);
            }
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AddSpellsSpellEffectsComponent.class)){
            int[] spellEffectEntities = observer.getRemoved().getComponent(entity, AddSpellsSpellEffectsComponent.class).getSpellEffectEntities();
            for(int spellEffectEntity : spellEffectEntities){
                CleanupUtil.tryCleanupEntity(entityWorld, spellEffectEntity);
            }
        }
        //visuals
        for(int entity : observer.getRemoved().getEntitiesWithAll(PlayAnimationComponent.class)){
            int animationEntity = observer.getRemoved().getComponent(entity, PlayAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
    }
}
