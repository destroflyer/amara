package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;

public class ApplyAddSpellSpellEffectsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddSpellSpellEffectsComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            AddSpellSpellEffectsComponent addSpellSpellEffectsComponent =  entityWorld.getComponent(effectImpactEntity, AddSpellSpellEffectsComponent.class);
            for (int spellEffectEntity : addSpellSpellEffectsComponent.getSpellEffectEntities()){
                // Clone the spell effect so it can be reused for multiple spells
                int newSpellEffectEntity = entityWorld.createEntity();
                entityWorld.setComponent(newSpellEffectEntity, new SpellEffectParentComponent(spellEffectEntity));
                entityWorld.setComponent(newSpellEffectEntity, new CastedSpellComponent(targetEntity));
                int[] castedEffectTriggers = entityWorld.getComponent(spellEffectEntity, CastedEffectTriggersComponent.class).getEffectTriggerEntities();
                int[] newCastedEffectTriggers = new int[castedEffectTriggers.length];
                for (int i = 0; i < newCastedEffectTriggers.length; i++) {
                    int newEffectTrigger = entityWorld.createEntity();
                    for (Object component : entityWorld.getComponents(castedEffectTriggers[i])) {
                        entityWorld.setComponent(newEffectTrigger, component);
                    }
                    if (addSpellSpellEffectsComponent.isSetSourcesToSpells()) {
                        entityWorld.setComponent(newEffectTrigger, new TriggerSourceComponent(targetEntity));
                    }
                    newCastedEffectTriggers[i] = newEffectTrigger;
                }
                entityWorld.setComponent(newSpellEffectEntity, new CastedEffectTriggersComponent(newCastedEffectTriggers));
            }
        }
    }
}
