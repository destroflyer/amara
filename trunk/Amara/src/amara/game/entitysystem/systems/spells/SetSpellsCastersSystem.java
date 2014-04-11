/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SetSpellsCastersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AutoAttackComponent.class, SpellsComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AutoAttackComponent.class)){
            updateCaster(entityWorld, entity, observer.getNew().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AutoAttackComponent.class)){
            updateCaster(entityWorld, entity, observer.getChanged().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AutoAttackComponent.class)){
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.removeComponent(autoAttackEntity, EffectCastSourceComponent.class);
        }
        for(int entity : observer.getNew().getEntitiesWithAll(SpellsComponent.class)){
            updateCaster(entityWorld, entity, observer.getNew().getComponent(entity, SpellsComponent.class).getSpellsEntities());
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(SpellsComponent.class)){
            updateCaster(entityWorld, entity, observer.getChanged().getComponent(entity, SpellsComponent.class).getSpellsEntities());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(SpellsComponent.class)){
            for(int spellEntity : observer.getRemoved().getComponent(entity, SpellsComponent.class).getSpellsEntities()){
                entityWorld.removeComponent(spellEntity, EffectCastSourceComponent.class);
            }
        }
        observer.reset();
    }
    
    private void updateCaster(EntityWorld entityWorld, int casterEntity, int[] spellEntities){
        for(int spellEntity : spellEntities){
            updateCaster(entityWorld, casterEntity, spellEntity);
        }
    }
    
    private void updateCaster(EntityWorld entityWorld, int casterEntity, int spellEntity){
        entityWorld.setComponent(spellEntity, new EffectCastSourceComponent(casterEntity));
    }
}
