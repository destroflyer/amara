/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.bounties.*;
import amara.game.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;

/**
 *
 * @author Carl
 */
public class PayOutBountiesSystem implements EntitySystem{
    
    private final float experienceRange = 20;
    private LinkedList<Integer> tmpRewardedEntities = new LinkedList<Integer>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAliveComponent.class)){
            BountyComponent bountyComponent = entityWorld.getComponent(entity, BountyComponent.class);
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(entity, DamageHistoryComponent.class);
            if((bountyComponent != null) && (damageHistoryComponent != null) && (damageHistoryComponent.getEntries().length > 0)){
                DamageHistoryComponent.DamageHistoryEntry lastDamageHistoryEntry = damageHistoryComponent.getEntries()[damageHistoryComponent.getEntries().length - 1];
                int killerEntity = lastDamageHistoryEntry.getSourceEntity();
                //Gold
                BountyGoldComponent bountyGoldComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyGoldComponent.class);
                if(bountyGoldComponent != null){
                    GoldComponent goldComponent = entityWorld.getComponent(killerEntity, GoldComponent.class);
                    if(goldComponent != null){
                        entityWorld.setComponent(killerEntity, new GoldComponent(goldComponent.getGold() + bountyGoldComponent.getGold()));
                    }
                }
                //Experience
                BountyExperienceComponent bountyExperienceComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyExperienceComponent.class);
                if(bountyExperienceComponent != null){
                    tmpRewardedEntities.clear();
                    int killerTeamEntity = entityWorld.getComponent(killerEntity, TeamComponent.class).getTeamEntity();
                    Vector2f deathPosition = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    for(int rewardedEntity : entityWorld.getEntitiesWithAll(TeamComponent.class, PositionComponent.class, ExperienceComponent.class)){
                        if(rewardedEntity != killerEntity){
                            int teamEntity = entityWorld.getComponent(rewardedEntity, TeamComponent.class).getTeamEntity();
                            Vector2f position = entityWorld.getComponent(rewardedEntity, PositionComponent.class).getPosition();
                            if((teamEntity == killerTeamEntity) && (position.distanceSquared(deathPosition) <= (experienceRange * experienceRange))){
                                tmpRewardedEntities.add(rewardedEntity);
                            }
                        }
                    }
                    if((!tmpRewardedEntities.contains(killerEntity)) && entityWorld.hasComponent(killerEntity, ExperienceComponent.class)){
                        tmpRewardedEntities.add(killerEntity);
                    }
                    if(tmpRewardedEntities.size() > 0){
                        int experienceShare = (bountyExperienceComponent.getExperience() / tmpRewardedEntities.size());
                        for(int rewardedEntity : tmpRewardedEntities){
                            int experience = entityWorld.getComponent(rewardedEntity, ExperienceComponent.class).getExperience();
                            entityWorld.setComponent(rewardedEntity, new ExperienceComponent(experience + experienceShare));
                        }
                    }
                }
                //Buff
                BountyBuffComponent bountyBuffComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyBuffComponent.class);
                if(bountyBuffComponent != null){
                    ApplyAddBuffsSystem.addBuff(entityWorld, killerEntity, bountyBuffComponent.getBuffEntity(), bountyBuffComponent.getDuration());
                }
            }
        }
    }
}
