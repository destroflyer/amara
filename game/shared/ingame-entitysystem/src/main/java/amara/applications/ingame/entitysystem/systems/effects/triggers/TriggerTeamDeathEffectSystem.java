package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TeamDeathTriggerComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class TriggerTeamDeathEffectSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TeamDeathTriggerComponent.class)){
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                int teamEntity = entityWorld.getComponent(sourceEntity, TeamComponent.class).getTeamEntity();
                boolean didEntityOfTeamDie = false;
                for (int deadEntity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)) {
                    TeamComponent teamComponent = entityWorld.getComponent(deadEntity, TeamComponent.class);
                    if ((teamComponent != null) && (teamComponent.getTeamEntity() == teamEntity)) {
                        didEntityOfTeamDie = true;
                        break;
                    }
                }
                if (didEntityOfTeamDie && isTeamDead(entityWorld, teamEntity)) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }

    private boolean isTeamDead(EntityWorld entityWorld, int teamEntity) {
        return entityWorld.getEntitiesWithAny(TeamComponent.class).stream()
                .filter(entityInATeam -> entityWorld.getComponent(entityInATeam, TeamComponent.class).getTeamEntity() == teamEntity)
                .noneMatch(alliedEntity -> entityWorld.hasComponent(alliedEntity, IsAliveComponent.class));
    }
}
