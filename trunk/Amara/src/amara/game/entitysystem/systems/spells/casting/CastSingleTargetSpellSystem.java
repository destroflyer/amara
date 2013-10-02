/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.effects.*;
import amara.game.entitysystem.components.units.movement.*;
import amara.game.entitysystem.components.visuals.*;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class CastSingleTargetSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(CastSingleTargetSpellComponent.class))
        {
            CastSingleTargetSpellComponent castSingleTargetSpellComponent = entityWorld.getCurrent().getComponent(entity, CastSingleTargetSpellComponent.class);
            castSingleTargetSpell(entityWorld, entity, castSingleTargetSpellComponent.getSpellEntityID(), castSingleTargetSpellComponent.getTargetEntityID());
            entityWorld.getCurrent().removeComponent(entity, CastSingleTargetSpellComponent.class);
        }
    }
    
    public static void castSingleTargetSpell(EntityWorld entityWorld, int casterEntityID, int spellEntityID, int targetEntityID){
        InstantTargetEffectComponent instantTargetEffectComponent = entityWorld.getCurrent().getComponent(spellEntityID, InstantTargetEffectComponent.class);
        if(instantTargetEffectComponent != null){
            EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
            effectCast.setComponent(new PrepareEffectComponent(instantTargetEffectComponent.getEffectEntityID()));
            effectCast.setComponent(new EffectSourceComponent(casterEntityID));
            effectCast.setComponent(new AffectedTargetsComponent(new int[]{targetEntityID}));
        }
        InstantSpawnsComponent instantSpawnsComponent = entityWorld.getCurrent().getComponent(spellEntityID, InstantSpawnsComponent.class);
        if(instantSpawnsComponent != null){
            int[] spawnInformationEntitiesIDs = instantSpawnsComponent.getSpawnInformationEntitiesIDs();
            for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                String templateName = spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateName();

                //TODO: Load template
                spawnedObject.setComponent(new ModelComponent("Models/cloud/skin.xml"));
                EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                effect.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
                spawnedObject.setComponent(new TargetReachedTriggerEffectComponent(effect.getId()));

                spawnedObject.setComponent(new CastSourceComponent(casterEntityID));
                Vector2f position = entityWorld.getCurrent().getComponent(casterEntityID, PositionComponent.class).getPosition().clone();
                RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                if(relativeSpawnPositionComponent != null){
                    position.addLocal(relativeSpawnPositionComponent.getPosition());
                }
                spawnedObject.setComponent(new PositionComponent(position));
                float spawnMovementSpeed = spawnInformation.getComponent(SpawnMovementSpeedComponent.class).getSpeed();
                spawnedObject.setComponent(new TargetedMovementComponent(targetEntityID, spawnMovementSpeed));
            }
        }
    }
}
