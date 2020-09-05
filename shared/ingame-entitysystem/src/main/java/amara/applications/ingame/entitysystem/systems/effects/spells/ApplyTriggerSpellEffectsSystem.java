package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

public class ApplyTriggerSpellEffectsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, TriggerSpellEffectsComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int spellEntity = entityWorld.getComponent(effectImpactEntity, TriggerSpellEffectsComponent.class).getSpellEntity();
            int effectActionIndex = entityWorld.getComponent(effectImpactEntity, EffectSourceActionIndexComponent.class).getIndex();
            for (int spellEffectEntity : entityWorld.getEntitiesWithAll(CastedSpellComponent.class, CastedEffectTriggersComponent.class)) {
                if (entityWorld.getComponent(spellEffectEntity, CastedSpellComponent.class).getSpellEntity() == spellEntity) {
                    int[] effectTriggerEntities = entityWorld.getComponent(spellEffectEntity, CastedEffectTriggersComponent.class).getEffectTriggerEntities();
                    EffectTriggerUtil.triggerEffects(entityWorld, effectTriggerEntities, targetEntity, effectActionIndex);
                }
            }
        }
    }
}
