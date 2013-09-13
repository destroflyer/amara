/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.spells.*;

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
            EntityWrapper spellCast = entityWorld.getWrapped(entityWorld.createEntity());
            spellCast.setComponent(new ApplyCastedSpellComponent(entity, castSingleTargetSpellComponent.getSpellEntityID()));
            spellCast.setComponent(new AffectedTargetsComponent(new int[]{castSingleTargetSpellComponent.getTargetEntityID()}));
            entityWorld.getCurrent().removeComponent(entity, CastSingleTargetSpellComponent.class);
        }
    }
}
