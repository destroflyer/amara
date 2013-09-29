/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effects.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.components.visuals.*;
import shapes.Circle;

/**
 *
 * @author Carl
 */
public class CastLinearSkillshotSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(CastLinearSkillshotSpellComponent.class)))
        {
            CastLinearSkillshotSpellComponent castLinearSkillshotSpellComponent = entityWrapper.getComponent(CastLinearSkillshotSpellComponent.class);
            Vector2f direction = castLinearSkillshotSpellComponent.getDirection();
            int[] spawnInformationEntitiesIDs = entityWorld.getCurrent().getComponent(castLinearSkillshotSpellComponent.getSpellEntityID(), InstantSpawnsComponent.class).getSpawnInformationEntitiesIDs();
            for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                String templateName = spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateName();
                
                //TODO: Load template
                spawnedObject.setComponent(new ModelComponent("Models/fireball/skin.xml"));
                spawnedObject.setComponent(new HitboxComponent(new Circle(1)));
                spawnedObject.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
                EntityWrapper intersectionRules = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                intersectionRules.setComponent(new AcceptEnemiesComponent());
                spawnedObject.setComponent(new IntersectionRulesComponent(intersectionRules.getId()));
                EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                effect.setComponent(new FlatMagicDamageComponent(200));
                effect.setComponent(new StunComponent(0.5f));
                spawnedObject.setComponent(new CollisionTriggerEffectComponent(effect.getId()));
                
                RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                if(relativeSpawnPositionComponent != null){
                    Vector2f position = entityWrapper.getComponent(PositionComponent.class).getPosition().add(relativeSpawnPositionComponent.getPosition());
                    spawnedObject.setComponent(new PositionComponent(position));
                }
                spawnedObject.setComponent(new DirectionComponent(direction));
                float spawnMovementSpeed = spawnInformation.getComponent(SpawnMovementSpeedComponent.class).getSpeed();
                spawnedObject.setComponent(new MovementSpeedComponent(direction.normalize().multLocal(spawnMovementSpeed)));
                spawnedObject.setComponent(new TeamComponent(entityWrapper.getComponent(TeamComponent.class).getTeamEntityID()));
            }
            entityWorld.getCurrent().removeComponent(entityWrapper.getId(), CastLinearSkillshotSpellComponent.class);
        }
    }
}
