/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class PayOutBountiesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, IsAliveComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAliveComponent.class)){
            BountyComponent bountyComponent = entityWorld.getComponent(entity, BountyComponent.class);
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(entity, DamageHistoryComponent.class);
            if((bountyComponent != null) && (damageHistoryComponent != null) && (damageHistoryComponent.getEntries().length > 0)){
                DamageHistoryComponent.DamageHistoryEntry lastDamageHistoryEntry = damageHistoryComponent.getEntries()[damageHistoryComponent.getEntries().length - 1];
                int currentGold = GoldUtil.getGold(entityWorld, lastDamageHistoryEntry.getSourceEntity());
                entityWorld.setComponent(lastDamageHistoryEntry.getSourceEntity(), new GoldComponent(currentGold + bountyComponent.getGold()));
            }
        }
        observer.reset();
    }
}
