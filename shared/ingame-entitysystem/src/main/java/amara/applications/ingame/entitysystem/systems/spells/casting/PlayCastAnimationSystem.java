package amara.applications.ingame.entitysystem.systems.spells.casting;

import amara.applications.ingame.entitysystem.components.input.CastSpellComponent;
import amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent;
import amara.applications.ingame.entitysystem.components.visuals.AnimationComponent;
import amara.applications.ingame.entitysystem.components.visuals.animations.PassedLoopTimeComponent;
import amara.applications.ingame.entitysystem.components.visuals.animations.RemainingLoopsComponent;
import amara.applications.ingame.entitysystem.components.visuals.animations.RestartClientAnimationComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class PlayCastAnimationSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int casterEntity : entityWorld.getEntitiesWithAny(CastSpellComponent.class)){
            int spellEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getSpellEntity();
            CastAnimationComponent castAnimationComponent = entityWorld.getComponent(spellEntity, CastAnimationComponent.class);
            if (castAnimationComponent != null) {
                entityWorld.setComponent(castAnimationComponent.getAnimationEntity(), new RemainingLoopsComponent(1 + castAnimationComponent.getAdditionalLoops()));
                entityWorld.setComponent(castAnimationComponent.getAnimationEntity(), new PassedLoopTimeComponent(0));
                entityWorld.setComponent(castAnimationComponent.getAnimationEntity(), new RestartClientAnimationComponent());
                entityWorld.setComponent(casterEntity, new AnimationComponent(castAnimationComponent.getAnimationEntity()));
            }
        }
    }
}
