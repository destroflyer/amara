/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;

/**
 *
 * @author Carl
 */
public class CastSingleTargetSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(CastSingleTargetSpellComponent.class))
        {
            CastSingleTargetSpellComponent castSingleTargetSpellComponent = entityWorld.getComponent(entity, CastSingleTargetSpellComponent.class);
            castSingleTargetSpell(entityWorld, entity, castSingleTargetSpellComponent.getSpellEntityID(), castSingleTargetSpellComponent.getTargetEntityID());
            entityWorld.removeComponent(entity, CastSingleTargetSpellComponent.class);
        }
    }
    
    public static void castSingleTargetSpell(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity){
        InstantEffectTriggersComponent instantEffectTriggersComponent = entityWorld.getComponent(spellEntity, InstantEffectTriggersComponent.class);
        if(instantEffectTriggersComponent != null){
            EffectTriggerUtil.triggerEffects(entityWorld, instantEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
        }
        InstantTargetBuffComponent instantTargetBuffComponent = entityWorld.getComponent(spellEntity, InstantTargetBuffComponent.class);
        if(instantTargetBuffComponent != null){
            EntityWrapper buffStatus = entityWorld.getWrapped(entityWorld.createEntity());
            buffStatus.setComponent(new ActiveBuffComponent(targetEntity, instantTargetBuffComponent.getBuffEntityID()));
            buffStatus.setComponent(new CastSourceComponent(casterEntity));
            buffStatus.setComponent(new RemainingBuffDurationComponent(instantTargetBuffComponent.getDuration()));
            entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        }
        InstantSpawnsComponent instantSpawnsComponent = entityWorld.getComponent(spellEntity, InstantSpawnsComponent.class);
        if(instantSpawnsComponent != null){
            int[] spawnInformationEntitiesIDs = instantSpawnsComponent.getSpawnInformationEntitiesIDs();
            for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                spawnedObject.setComponent(new CastSourceComponent(casterEntity));
                Vector2f position = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition().clone();
                RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                if(relativeSpawnPositionComponent != null){
                    position.addLocal(relativeSpawnPositionComponent.getPosition());
                }
                spawnedObject.setComponent(new PositionComponent(position));
                EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                movement.setComponent(new MovementTargetComponent(targetEntity));
                movement.setComponent(new MovementSpeedComponent(spawnInformation.getComponent(SpawnMovementSpeedComponent.class).getSpeed()));
                spawnedObject.setComponent(new MovementComponent(movement.getId()));
                EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
            }
        }
    }
}
