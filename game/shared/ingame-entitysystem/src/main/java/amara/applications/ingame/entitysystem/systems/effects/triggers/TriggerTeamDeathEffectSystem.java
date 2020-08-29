package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TeamDeathTriggerComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

import java.util.Arrays;

public class TriggerTeamDeathEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TeamDeathTriggerComponent.class)){
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                int teamEntity = entityWorld.getComponent(sourceEntity, TeamComponent.class).getTeamEntity();
                int[] excludedEntities = entityWorld.getComponent(effectTriggerEntity, TeamDeathTriggerComponent.class).getExcludedEntities();
                if (isTeamDead(entityWorld, teamEntity, excludedEntities)) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }

    private boolean isTeamDead(EntityWorld entityWorld, int teamEntity, int[] excludedEntities) {
        return entityWorld.getEntitiesWithAny(TeamComponent.class).stream()
                .filter(entityInATeam -> entityWorld.getComponent(entityInATeam, TeamComponent.class).getTeamEntity() == teamEntity)
                .filter(entityInTeam -> Arrays.stream(excludedEntities).noneMatch(entity -> entity == entityInTeam))
                .noneMatch(alliedEntity -> entityWorld.hasComponent(alliedEntity, IsAliveComponent.class));
    }
}
