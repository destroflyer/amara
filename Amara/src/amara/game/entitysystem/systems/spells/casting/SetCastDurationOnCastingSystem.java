/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SetCastDurationOnCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellComponent.class)){
            int spellEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getSpellEntity();
            CastDurationComponent castDurationComponent = entityWorld.getComponent(spellEntity, CastDurationComponent.class);
            if(castDurationComponent != null){
                boolean isCancelable = entityWorld.hasComponent(spellEntity, CastCancelableComponent.class);
                entityWorld.setComponent(casterEntity, new IsCastingComponent(castDurationComponent.getDuration(), isCancelable));
            }
        }
    }
}
