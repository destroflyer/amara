/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class CastLinearSkillshotSpellSystem implements EntitySystem{
    
    @Override
    public void update(final EntityWorld entityWorld, float deltaSeconds){
        for(final EntityWrapper caster : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(CastLinearSkillshotSpellComponent.class)))
        {
            CastLinearSkillshotSpellComponent castLinearSkillshotSpellComponent = caster.getComponent(CastLinearSkillshotSpellComponent.class);
            final Vector2f direction = castLinearSkillshotSpellComponent.getDirection();
            castSpawnSpell(entityWorld, caster.getId(), castLinearSkillshotSpellComponent.getSpellEntityID(), new SpawnedObjectModifier(){

                @Override
                public void modify(EntityWrapper spawnedObject, EntityWrapper spawnInformation){
                    Vector2f position = caster.getComponent(PositionComponent.class).getPosition().clone();
                    RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                    if(relativeSpawnPositionComponent != null){
                        position.addLocal(relativeSpawnPositionComponent.getPosition());
                    }
                    spawnedObject.setComponent(new PositionComponent(position));
                    spawnedObject.setComponent(new DirectionComponent(direction));
                    EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                    movement.setComponent(new MovementDirectionComponent(direction));
                    movement.setComponent(new MovementSpeedComponent(spawnInformation.getComponent(SpawnMovementSpeedComponent.class).getSpeed()));
                    spawnedObject.setComponent(new MovementComponent(movement.getId()));
                }
            });
            entityWorld.setComponent(caster.getId(), new DirectionComponent(direction));
            entityWorld.removeComponent(caster.getId(), CastLinearSkillshotSpellComponent.class);
        }
    }
    
    public static void castSpawnSpell(EntityWorld entityWorld, int casterEntityID, int spellEntityID, SpawnedObjectModifier spawnedObjectModifier){
        InstantSpawnsComponent instantSpawnsComponent = entityWorld.getComponent(spellEntityID, InstantSpawnsComponent.class);
        if(instantSpawnsComponent != null){
            int[] spawnInformationEntitiesIDs = instantSpawnsComponent.getSpawnInformationEntitiesIDs();
            for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                spawnedObject.setComponent(new CastSourceComponent(casterEntityID));
                spawnedObject.setComponent(new TeamComponent(entityWorld.getComponent(casterEntityID, TeamComponent.class).getTeamEntityID()));
                EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
                spawnedObjectModifier.modify(spawnedObject, spawnInformation);
            }
        }
    }
}
