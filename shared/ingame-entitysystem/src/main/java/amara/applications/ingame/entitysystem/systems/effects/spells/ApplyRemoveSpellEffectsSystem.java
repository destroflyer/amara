package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.spells.RemoveSpellEffectsComponent;
import amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent;
import amara.applications.ingame.entitysystem.components.spells.triggers.SpellEffectParentComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyRemoveSpellEffectsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveSpellEffectsComponent.class)) {
            for (int spellEffectEntity : entityWorld.getComponent(effectImpactEntity, RemoveSpellEffectsComponent.class).getSpellEffectEntities()) {
                entityWorld.removeComponent(spellEffectEntity, CastedSpellComponent.class);
                for (int childSpellEffectEntity : entityWorld.getEntitiesWithAny(SpellEffectParentComponent.class)) {
                    int parentSpellEffectEntity = entityWorld.getComponent(childSpellEffectEntity, SpellEffectParentComponent.class).getSpellEffectEntity();
                    if (parentSpellEffectEntity == spellEffectEntity) {
                        for (int castedEffectTriggerEntity : entityWorld.getComponent(childSpellEffectEntity, CastedEffectTriggersComponent.class).getEffectTriggerEntities()) {
                            entityWorld.removeEntity(castedEffectTriggerEntity);
                        }
                        entityWorld.removeEntity(childSpellEffectEntity);
                    }
                }
            }
        }
    }
}
