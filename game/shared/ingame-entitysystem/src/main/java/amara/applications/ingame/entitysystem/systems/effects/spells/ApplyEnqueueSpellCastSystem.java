package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.spells.EnqueueSpellCastComponent;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellQueueSystem;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyEnqueueSpellCastSystem implements EntitySystem {

    public ApplyEnqueueSpellCastSystem(CastSpellQueueSystem castSpellQueueSystem) {
        this.castSpellQueueSystem = castSpellQueueSystem;
    }
    private CastSpellQueueSystem castSpellQueueSystem;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, EnqueueSpellCastComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            EnqueueSpellCastComponent enqueueSpellCastComponent = entityWorld.getComponent(effectImpactEntity, EnqueueSpellCastComponent.class);
            castSpellQueueSystem.enqueueSpellCast(targetEntity, enqueueSpellCastComponent.getSpellEntity(), enqueueSpellCastComponent.getTargetEntity());
        }
    }
}
