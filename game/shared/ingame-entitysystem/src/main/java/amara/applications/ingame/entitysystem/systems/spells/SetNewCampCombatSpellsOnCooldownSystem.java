/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetNewCampCombatSpellsOnCooldownSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CampInCombatComponent.class);
        for(int campEntity : observer.getNew().getEntitiesWithAny(CampInCombatComponent.class)){
            for(int entity : entityWorld.getEntitiesWithAny(CampComponent.class)){
                CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
                if(campComponent.getCampEntity() == campEntity){
                    checkSetSpellOnCooldown(entityWorld, entity);
                }
            }
        }
    }
    
    private void checkSetSpellOnCooldown(EntityWorld entityWorld, int entity){
        SetNewCampCombatSpellsOnCooldownComponent setNewCombatSpellsOnCooldownComponent = entityWorld.getComponent(entity, SetNewCampCombatSpellsOnCooldownComponent.class);
        if(setNewCombatSpellsOnCooldownComponent != null){
            int[] spellEntities = entityWorld.getComponent(entity, SpellsComponent.class).getSpellsEntities();
            for(int i=0;i<setNewCombatSpellsOnCooldownComponent.getSpellIndices().length;i++){
                int spellIndex = setNewCombatSpellsOnCooldownComponent.getSpellIndices()[i];
                float cooldown = setNewCombatSpellsOnCooldownComponent.getCooldowns()[i];
                if(cooldown == -1){
                    cooldown = entityWorld.getComponent(spellEntities[spellIndex], CooldownComponent.class).getDuration();
                }
                entityWorld.setComponent(spellEntities[spellIndex], new RemainingCooldownComponent(cooldown));
            }
        }
    }
}
