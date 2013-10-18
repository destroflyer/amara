/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.movement.*;

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
    
    public static void castSingleTargetSpell(EntityWorld entityWorld, int casterEntityID, int spellEntityID, int targetEntityID){
        InstantTargetEffectComponent instantTargetEffectComponent = entityWorld.getComponent(spellEntityID, InstantTargetEffectComponent.class);
        if(instantTargetEffectComponent != null){
            EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
            effectCast.setComponent(new PrepareEffectComponent(instantTargetEffectComponent.getEffectEntityID()));
            effectCast.setComponent(new EffectSourceComponent(casterEntityID));
            effectCast.setComponent(new AffectedTargetsComponent(new int[]{targetEntityID}));
        }
        InstantTargetBuffComponent instantTargetBuffComponent = entityWorld.getComponent(spellEntityID, InstantTargetBuffComponent.class);
        if(instantTargetBuffComponent != null){
            EntityWrapper buffStatus = entityWorld.getWrapped(entityWorld.createEntity());
            buffStatus.setComponent(new ActiveBuffComponent(targetEntityID, instantTargetBuffComponent.getBuffEntityID()));
            buffStatus.setComponent(new CastSourceComponent(casterEntityID));
            buffStatus.setComponent(new RemainingBuffDurationComponent(instantTargetBuffComponent.getDuration()));
            entityWorld.setComponent(targetEntityID, new RequestUpdateAttributesComponent());
        }
        InstantSpawnsComponent instantSpawnsComponent = entityWorld.getComponent(spellEntityID, InstantSpawnsComponent.class);
        if(instantSpawnsComponent != null){
            int[] spawnInformationEntitiesIDs = instantSpawnsComponent.getSpawnInformationEntitiesIDs();
            for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                spawnedObject.setComponent(new CastSourceComponent(casterEntityID));
                Vector2f position = entityWorld.getComponent(casterEntityID, PositionComponent.class).getPosition().clone();
                RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                if(relativeSpawnPositionComponent != null){
                    position.addLocal(relativeSpawnPositionComponent.getPosition());
                }
                spawnedObject.setComponent(new PositionComponent(position));
                float spawnMovementSpeed = spawnInformation.getComponent(SpawnMovementSpeedComponent.class).getSpeed();
                spawnedObject.setComponent(new TargetedMovementComponent(targetEntityID, spawnMovementSpeed));
                EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
            }
        }
    }
}
