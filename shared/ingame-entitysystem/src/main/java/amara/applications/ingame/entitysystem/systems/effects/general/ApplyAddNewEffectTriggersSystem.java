package amara.applications.ingame.entitysystem.systems.effects.general;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.general.AddNewEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;

public class ApplyAddNewEffectTriggersSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddNewEffectTriggersComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            String[] effectTriggerTemplates = entityWorld.getComponent(effectImpactEntity, AddNewEffectTriggersComponent.class).getEffectTriggerTemplates();
            int[] effectTriggerEntities = new int[effectTriggerTemplates.length];
            for (int i = 0; i < effectTriggerEntities.length; i++) {
                effectTriggerEntities[i] = entityWorld.createEntity();
            }
            for (int i = 0; i < effectTriggerEntities.length; i++) {
                String template = effectTriggerTemplates[i] + "(";
                for (int r = 0; r < effectTriggerEntities.length; r++) {
                    if (r != 0) {
                        template += ",";
                    }
                    template += ",newEffectTrigger" + r + "=" + effectTriggerEntities[r];
                }
                template += ")";
                EntityTemplate.loadTemplate(entityWorld, effectTriggerEntities[i], template);
                entityWorld.setComponent(effectTriggerEntities[i], new TriggerSourceComponent(targetEntity));
            }
        }
    }
}
