package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.entitysystem.systems.cleanup.CleanupUtil;
import amara.core.Util;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyReplaceSpellsWithExistingSpellsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReplaceSpellWithExistingSpellComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            ReplaceSpellWithExistingSpellComponent replaceSpellWithExistingSpellComponent = entityWorld.getComponent(effectImpactEntity, ReplaceSpellWithExistingSpellComponent.class);
            int[] spellsEntities = entityWorld.getComponent(targetEntity, SpellsComponent.class).getSpellsEntities();
            int oldSpellEntity = spellsEntities[replaceSpellWithExistingSpellComponent.getSpellIndex()];
            CleanupUtil.tryCleanupEntity(entityWorld, oldSpellEntity);
            int[] newSpellsEntities = Util.cloneArray(spellsEntities);
            newSpellsEntities[replaceSpellWithExistingSpellComponent.getSpellIndex()] = replaceSpellWithExistingSpellComponent.getSpellEntity();
            entityWorld.setComponent(targetEntity, new SpellsComponent(newSpellsEntities));
        }
    }
}
