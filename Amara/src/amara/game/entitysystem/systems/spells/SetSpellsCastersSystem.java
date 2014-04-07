/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SetSpellsCastersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, SpellsComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(SpellsComponent.class)){
            updateCaster(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(SpellsComponent.class)){
            updateCaster(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(SpellsComponent.class)){
            for(int spellEntity : observer.getRemoved().getComponent(entity, SpellsComponent.class).getSpellsEntities()){
                entityWorld.removeComponent(spellEntity, CastSourceComponent.class);
            }
        }
        observer.reset();
    }
    
    private void updateCaster(EntityWorld entityWorld, int entity){
        for(int spellEntity : entityWorld.getComponent(entity, SpellsComponent.class).getSpellsEntities()){
            entityWorld.setComponent(spellEntity, new CastSourceComponent(entity));
        }
    }
}
