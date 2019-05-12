/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupSpellsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellIndicatorComponent.class, SpellTargetRulesComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(SpellIndicatorComponent.class)){
            int indicatorEntity = observer.getRemoved().getComponent(entity, SpellIndicatorComponent.class).getIndicatorEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, indicatorEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(SpellTargetRulesComponent.class)){
            int targetRulesEntity = observer.getRemoved().getComponent(entity, SpellTargetRulesComponent.class).getTargetRulesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, targetRulesEntity);
        }
    }
}
