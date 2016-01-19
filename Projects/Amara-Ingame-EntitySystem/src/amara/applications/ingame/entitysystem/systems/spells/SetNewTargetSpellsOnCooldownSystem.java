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
public class SetNewTargetSpellsOnCooldownSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AggroTargetComponent.class)){
            checkSetSpellOnCooldown(entityWorld, entity);
        }
    }
    
    private void checkSetSpellOnCooldown(EntityWorld entityWorld, int entity){
        SetNewTargetSpellsOnCooldownComponent setNewTargetSpellsOnCooldownComponent = entityWorld.getComponent(entity, SetNewTargetSpellsOnCooldownComponent.class);
        if(setNewTargetSpellsOnCooldownComponent != null){
            int[] spellEntities = entityWorld.getComponent(entity, SpellsComponent.class).getSpellsEntities();
            for(int i=0;i<setNewTargetSpellsOnCooldownComponent.getSpellIndices().length;i++){
                int spellIndex = setNewTargetSpellsOnCooldownComponent.getSpellIndices()[i];
                float cooldown = setNewTargetSpellsOnCooldownComponent.getCooldowns()[i];
                if(cooldown == -1){
                    cooldown = entityWorld.getComponent(spellEntities[spellIndex], CooldownComponent.class).getDuration();
                }
                entityWorld.setComponent(spellEntities[spellIndex], new RemainingCooldownComponent(cooldown));
            }
        }
    }
}
