/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.buffs.areas;

import java.util.Set;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersection.*;

/**
 *
 * @author Carl
 */
public class CheckAreaBuffsSystem implements EntitySystem{

    public CheckAreaBuffsSystem(IntersectionInformant intersectionInformant){
        this.intersectionInformant = intersectionInformant;
    }
    private IntersectionInformant intersectionInformant;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        IntersectionTracker<Pair<Integer>> tracker = intersectionInformant.getTracker(entityWorld, this);
        Set<Pair<Integer>> intersectionEntries = tracker.getEntries();
        Set<Pair<Integer>> intersectionLeavers = tracker.getLeavers();
        for(int buffAreaEntity : entityWorld.getEntitiesWithAny(AreaOriginComponent.class)){
            int buffEntity = entityWorld.getComponent(buffAreaEntity, AreaBuffComponent.class).getBuffEntity();
            for(Pair<Integer> pair : intersectionEntries){
                if(pair.getA() == buffAreaEntity){
                    addBuff(entityWorld, buffAreaEntity, pair.getB(), buffEntity);
                }
                else if(pair.getB() == buffAreaEntity){
                    addBuff(entityWorld, buffAreaEntity, pair.getA(), buffEntity);
                }
            }
            for(Pair<Integer> pair : intersectionLeavers){
                if(pair.getA() == buffAreaEntity){
                    removeBuff(entityWorld, pair.getB(), buffEntity);
                }
                else if(pair.getB() == buffAreaEntity){
                    removeBuff(entityWorld, pair.getA(), buffEntity);
                }
            }
        }
    }
    
    private void addBuff(EntityWorld entityWorld, int buffAreaEntity, int targetEntity, int buffEntity){
        boolean isValidTarget = true;
        AreaSourceComponent areaSourceComponent = entityWorld.getComponent(buffAreaEntity, AreaSourceComponent.class);
        AreaBuffTargetRulesComponent areaBuffTargetRulesComponent = entityWorld.getComponent(buffAreaEntity, AreaBuffTargetRulesComponent.class);
        if((areaSourceComponent != null) && (areaBuffTargetRulesComponent != null)){
            isValidTarget = TargetUtil.isValidTarget(entityWorld, areaSourceComponent.getSourceEntity(), targetEntity, areaBuffTargetRulesComponent.getTargetRulesEntity());
        }
        if(isValidTarget){
            int buffStatusEntity = entityWorld.createEntity();
            entityWorld.setComponent(buffStatusEntity, new ActiveBuffComponent(targetEntity, buffEntity));
            if(areaSourceComponent != null){
                entityWorld.setComponent(buffStatusEntity, new EffectCastSourceComponent(areaSourceComponent.getSourceEntity()));
            }
            entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        }
    }
    
    private void removeBuff(EntityWorld entityWorld, int targetEntity, int buffEntity){
        for(int buffStatus : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)){
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class);
            if((activeBuffComponent.getTargetEntity() == targetEntity) && (activeBuffComponent.getBuffEntity() == buffEntity)){
                entityWorld.setComponent(buffStatus, new RemoveFromTargetComponent());
                break;
            }
        }
    }
}
