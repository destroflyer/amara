/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetNewCombatSpellsOnCooldownSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InCombatComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(InCombatComponent.class)){
            checkSetSpellOnCooldown(entityWorld, entity);
        }
    }
    
    private void checkSetSpellOnCooldown(EntityWorld entityWorld, int entity){
        SetNewCombatSpellsOnCooldownComponent setNewCombatSpellsOnCooldownComponent = entityWorld.getComponent(entity, SetNewCombatSpellsOnCooldownComponent.class);
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
