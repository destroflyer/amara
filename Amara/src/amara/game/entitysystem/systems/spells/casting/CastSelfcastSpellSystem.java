/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;

/**
 *
 * @author Carl
 */
public class CastSelfcastSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper caster : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(CastSelfcastSpellComponent.class)))
        {
            CastSelfcastSpellComponent castSelfcastSpellComponent = caster.getComponent(CastSelfcastSpellComponent.class);
            CastSingleTargetSpellSystem.castSingleTargetSpell(entityWorld, caster.getId(), castSelfcastSpellComponent.getSpellEntityID(), caster.getId());
            entityWorld.removeComponent(caster.getId(), CastSelfcastSpellComponent.class);
        }
    }
}
