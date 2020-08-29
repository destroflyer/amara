package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.entitysystem.systems.cleanup.CleanupUtil;
import amara.core.Util;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;

public class ApplyReplaceSpellsWithNewSpellsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReplaceSpellWithNewSpellComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = entityWorld.getComponent(effectImpactEntity, ReplaceSpellWithNewSpellComponent.class);
            int[] spellsEntities = entityWorld.getComponent(targetEntity, SpellsComponent.class).getSpellsEntities();
            int oldSpellEntity = spellsEntities[replaceSpellWithNewSpellComponent.getSpellIndex()];
            CleanupUtil.tryCleanupEntity(entityWorld, oldSpellEntity);
            int[] newSpellsEntities = Util.cloneArray(spellsEntities);
            int newSpellEntity = EntityTemplate.createFromTemplate(entityWorld, replaceSpellWithNewSpellComponent.getNewSpellTemplate()).getId();
            newSpellsEntities[replaceSpellWithNewSpellComponent.getSpellIndex()] = newSpellEntity;
            entityWorld.setComponent(targetEntity, new SpellsComponent(newSpellsEntities));
        }
    }
}
