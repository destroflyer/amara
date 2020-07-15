package amara.applications.ingame.entitysystem.systems.buffs.areas;

import java.util.Set;
import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyRemoveBuffsSystem;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersection.*;

public class CheckAreaBuffsSystem implements EntitySystem {

    public CheckAreaBuffsSystem(IntersectionInformant intersectionInformant) {
        this.intersectionInformant = intersectionInformant;
    }
    private IntersectionInformant intersectionInformant;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        IntersectionTracker<Pair<Integer>> tracker = intersectionInformant.getTracker(entityWorld, this);
        Set<Pair<Integer>> intersectionEntries = tracker.getEntries();
        Set<Pair<Integer>> intersectionLeavers = tracker.getLeavers();
        for (int buffAreaEntity : entityWorld.getEntitiesWithAny(AreaBuffComponent.class)) {
            int buffEntity = entityWorld.getComponent(buffAreaEntity, AreaBuffComponent.class).getBuffEntity();
            for (Pair<Integer> pair : intersectionEntries) {
                if (pair.getA() == buffAreaEntity) {
                    addBuff(entityWorld, buffAreaEntity, pair.getB(), buffEntity);
                } else if(pair.getB() == buffAreaEntity) {
                    addBuff(entityWorld, buffAreaEntity, pair.getA(), buffEntity);
                }
            }
            for (Pair<Integer> pair : intersectionLeavers) {
                if (pair.getA() == buffAreaEntity) {
                    ApplyRemoveBuffsSystem.removeBuff(entityWorld, pair.getB(), buffEntity);
                } else if (pair.getB() == buffAreaEntity) {
                    ApplyRemoveBuffsSystem.removeBuff(entityWorld, pair.getA(), buffEntity);
                }
            }
        }
    }

    private void addBuff(EntityWorld entityWorld, int buffAreaEntity, int targetEntity, int buffEntity) {
        boolean isValidTarget = true;
        AreaSourceComponent areaSourceComponent = entityWorld.getComponent(buffAreaEntity, AreaSourceComponent.class);
        AreaBuffTargetRulesComponent areaBuffTargetRulesComponent = entityWorld.getComponent(buffAreaEntity, AreaBuffTargetRulesComponent.class);
        if ((areaSourceComponent != null) && (areaBuffTargetRulesComponent != null)) {
            isValidTarget = TargetUtil.isValidTarget(entityWorld, areaSourceComponent.getSourceEntity(), targetEntity, areaBuffTargetRulesComponent.getTargetRulesEntity());
        }
        if (isValidTarget) {
            int buffStatusEntity = ApplyAddBuffsSystem.addBuff(entityWorld, targetEntity, buffEntity);
            if (areaSourceComponent != null) {
                entityWorld.setComponent(buffStatusEntity, new EffectSourceComponent(areaSourceComponent.getSourceEntity()));
            }
        }
    }
}
