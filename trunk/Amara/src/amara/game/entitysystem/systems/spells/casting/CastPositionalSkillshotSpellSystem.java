/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spells.specials.*;

/**
 *
 * @author Carl
 */
public class CastPositionalSkillshotSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper caster : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(CastPositionalSkillshotSpellComponent.class)))
        {
            CastPositionalSkillshotSpellComponent castPositionalSkillshotSpellComponent = caster.getComponent(CastPositionalSkillshotSpellComponent.class);
            int spellEntityID = castPositionalSkillshotSpellComponent.getSpellEntityID();
            final Vector2f position = castPositionalSkillshotSpellComponent.getPosition();
            CastLinearSkillshotSpellSystem.castSpawnSpell(entityWorld, caster.getId(), spellEntityID, new SpawnedObjectModifier(){

                @Override
                public void modify(EntityWrapper spawnedObject, EntityWrapper spawnInformation){
                    spawnedObject.setComponent(new PositionComponent(position.clone()));
                }
            });
            entityWorld.removeComponent(caster.getId(), CastPositionalSkillshotSpellComponent.class);
            if(entityWorld.hasComponent(spellEntityID, TeleportCasterToTargetPositionComponent.class)){
                caster.setComponent(new PositionComponent(position.clone()));
                caster.removeComponent(MovementTargetComponent.class);
                caster.removeComponent(MovementSpeedComponent.class);
            }
        }
    }
}
