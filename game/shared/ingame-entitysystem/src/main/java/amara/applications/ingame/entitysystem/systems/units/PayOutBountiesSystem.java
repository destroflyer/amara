/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.bounties.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PayOutBountiesSystem implements EntitySystem{

    private final float experienceRange = 28;
    private int defaultBountyRulesEntity = -1;
    private LinkedList<Integer> tmpRewardedEntities = new LinkedList<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(defaultBountyRulesEntity == -1){
            defaultBountyRulesEntity = entityWorld.createEntity();
            entityWorld.setComponent(defaultBountyRulesEntity, new AcceptEnemiesComponent());
        }
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAliveComponent.class)){
            BountyComponent bountyComponent = entityWorld.getComponent(entity, BountyComponent.class);
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(entity, DamageHistoryComponent.class);
            if((bountyComponent != null) && (damageHistoryComponent != null) && (damageHistoryComponent.getEntries().length > 0)){
                int killerEntity = -1;
                int bountyRulesEntity = defaultBountyRulesEntity;
                BountyRulesComponent bountyRulesComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyRulesComponent.class);
                if(bountyRulesComponent != null){
                    bountyRulesEntity = bountyRulesComponent.getTargetRulesEntity();
                }
                for(int i=(damageHistoryComponent.getEntries().length - 1);i>=0;i--){
                    DamageHistoryComponent.DamageHistoryEntry damageHistoryEntry = damageHistoryComponent.getEntries()[i];
                    if(TargetUtil.isValidTarget(entityWorld, entity, damageHistoryEntry.getSourceEntity(), bountyRulesEntity)){
                        killerEntity = damageHistoryEntry.getSourceEntity();
                        break;
                    }
                }
                if (killerEntity != -1) {
                    int killReceiverEntity = getBountyReceiverEntity(entityWorld, killerEntity);
                    //CharacterKill
                    BountyCharacterKillComponent bountyCharacterKillScoreComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyCharacterKillComponent.class);
                    if(bountyCharacterKillScoreComponent != null){
                        //Kill
                        ScoreComponent killerScoreComponent = entityWorld.getComponent(killReceiverEntity, ScoreComponent.class);
                        if(killerScoreComponent != null){
                            int scoreEntity = killerScoreComponent.getScoreEntity();
                            int kills = 1;
                            CharacterKillsComponent characterKillsComponent = entityWorld.getComponent(scoreEntity, CharacterKillsComponent.class);
                            if(characterKillsComponent != null){
                                kills += characterKillsComponent.getKills();
                            }
                            entityWorld.setComponent(scoreEntity, new CharacterKillsComponent(kills));
                        }
                        tmpRewardedEntities.clear();
                        tmpRewardedEntities.add(killReceiverEntity);
                        //Assists
                        for(int i=0;i<(damageHistoryComponent.getEntries().length - 1);i++){
                            int assistingEntity = damageHistoryComponent.getEntries()[i].getSourceEntity();
                            int assistReceiverEntity = getBountyReceiverEntity(entityWorld, assistingEntity);
                            if(!tmpRewardedEntities.contains(assistReceiverEntity)){
                                ScoreComponent scoreComponent = entityWorld.getComponent(assistReceiverEntity, ScoreComponent.class);
                                if(scoreComponent != null){
                                    int scoreEntity = scoreComponent.getScoreEntity();
                                    int assists = 1;
                                    CharacterAssistsComponent characterAssistsComponent = entityWorld.getComponent(scoreEntity, CharacterAssistsComponent.class);
                                    if(characterAssistsComponent != null){
                                        assists += characterAssistsComponent.getAssists();
                                    }
                                    entityWorld.setComponent(scoreEntity, new CharacterAssistsComponent(assists));
                                }
                                tmpRewardedEntities.add(assistReceiverEntity);
                            }
                        }
                    }
                    //CreepScore
                    BountyCreepScoreComponent bountyCreepScoreComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyCreepScoreComponent.class);
                    if(bountyCreepScoreComponent != null){
                        ScoreComponent scoreComponent = entityWorld.getComponent(killReceiverEntity, ScoreComponent.class);
                        if(scoreComponent != null){
                            int scoreEntity = scoreComponent.getScoreEntity();
                            int kills = 1;
                            CreepScoreComponent creepScoreComponent = entityWorld.getComponent(scoreEntity, CreepScoreComponent.class);
                            if(creepScoreComponent != null){
                                kills += creepScoreComponent.getKills();
                            }
                            entityWorld.setComponent(scoreEntity, new CreepScoreComponent(kills));
                        }
                    }
                    //Gold
                    BountyGoldComponent bountyGoldComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyGoldComponent.class);
                    if(bountyGoldComponent != null){
                        GoldComponent goldComponent = entityWorld.getComponent(killReceiverEntity, GoldComponent.class);
                        if(goldComponent != null){
                            entityWorld.setComponent(killReceiverEntity, new GoldComponent(goldComponent.getGold() + bountyGoldComponent.getGold()));
                        }
                    }
                    //Experience
                    BountyExperienceComponent bountyExperienceComponent = entityWorld.getComponent(bountyComponent.getBountyEntity(), BountyExperienceComponent.class);
                    if(bountyExperienceComponent != null){
                        tmpRewardedEntities.clear();
                        int killerTeamEntity = entityWorld.getComponent(killReceiverEntity, TeamComponent.class).getTeamEntity();
                        Vector2f deathPosition = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                        for(int rewardedEntity : entityWorld.getEntitiesWithAll(TeamComponent.class, PositionComponent.class, ExperienceComponent.class)){
                            if(rewardedEntity != killReceiverEntity){
                                int teamEntity = entityWorld.getComponent(rewardedEntity, TeamComponent.class).getTeamEntity();
                                Vector2f position = entityWorld.getComponent(rewardedEntity, PositionComponent.class).getPosition();
                                if((teamEntity == killerTeamEntity) && (position.distanceSquared(deathPosition) <= (experienceRange * experienceRange))){
                                    tmpRewardedEntities.add(rewardedEntity);
                                }
                            }
                        }
                        if((!tmpRewardedEntities.contains(killReceiverEntity)) && entityWorld.hasComponent(killReceiverEntity, ExperienceComponent.class)){
                            tmpRewardedEntities.add(killReceiverEntity);
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
                        ApplyAddBuffsSystem.addBuff(entityWorld, killReceiverEntity, bountyBuffComponent.getBuffEntity(), bountyBuffComponent.getDuration());
                    }
                }
            }
        }
    }

    private int getBountyReceiverEntity(EntityWorld entityWorld, int receiverEntity) {
        RedirectReceivedBountiesComponent receivedBountiesRedirectComponent = entityWorld.getComponent(receiverEntity, RedirectReceivedBountiesComponent.class);
        if (receivedBountiesRedirectComponent != null) {
            int newReceiverEntity = receivedBountiesRedirectComponent.getReceivedEntity();
            return getBountyReceiverEntity(entityWorld, newReceiverEntity);
        }
        return receiverEntity;
    }
}
